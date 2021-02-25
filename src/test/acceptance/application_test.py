from pyppeteer import launch
from utils import *


async def main():
    props = load_properties_file("")
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await login(page, USER, PASS)
    await nap()
    await page.click("#apply-link", WAIT_OPTS)
    await nap()
    await enter_and_check_competence_years(page)
    await nap()
    await add_availability(page)
    await nap()
    await check_availability_form(page, props)
    await submit_availability_form(page)
    await nap()
    await submit_entire_application(page)
    await browser.close()


async def enter_and_check_competence_years(page):
    print_test_case_desc("Entering 2.0 to competence years on competence Grilling sausage and checks that it is "
                         "updated on the page")
    await page.content()

    # fetch years input field
    years = await page.J("#competenceYears")

    # hard coded competence choice
    competence_to_choose = "Grilling sausage"
    competence_years_to_add = "2.0"

    # add the competence and years, click Add
    await page.select("#competence", competence_to_choose)
    await years.type(competence_years_to_add)
    await page.click("#competenceFormAdd", WAIT_OPTS)

    # wait for update on page
    await page.waitForSelector("#userCompetences")

    # fetch the user competences
    user_competences = await page.J("#userCompetences")

    # fetch values: length of list, competence name and years
    user_competences_divs = await user_competences.JJeval("div", "node => [...node['0'].children]")
    user_competence_name = await user_competences.JJeval(".userCompetenceName", "node => node.map(n => n.innerText)")
    user_competence_years = await user_competences.JJeval(".userCompetenceYears", "node => node.map(n => n.innerText)")

    # assert correct values
    assert competence_to_choose in user_competence_name, "Expected competence name to be " + competence_to_choose
    assert competence_years_to_add in user_competence_years, "Expected competence years to be " + competence_years_to_add
    assert len(user_competences_divs) == 2, "Wrong length of competence list"

    # submit the form
    await page.click("#competenceFormSubmit", WAIT_OPTS)
    print_success()


from_string = "2222-02-02"
to_string = "2222-03-03"


async def add_availability(page):
    print_test_case_desc("Entering 2222-02-02 to 2222-03-03 as availability and checks that it is updated on the page")

    # add availability dates
    await page.waitForSelector("#from", SELECTOR_WAIT)
    await page.waitForSelector("#to", SELECTOR_WAIT)
    from_input = await page.J("#from")
    to_input = await page.J("#to")

    await from_input.type(from_string[::-1])
    await to_input.type(to_string[::-1])

    # add the availability
    await page.click("#availabilityFormSubmit")
    print_success()


async def check_availability_form(page, props):
    print_test_case_desc("Checking that the availability has been added")
    await page.waitForSelector("#userAvailabilities", SELECTOR_WAIT)
    user_availabilities = await page.JJeval("#userAvailabilities", "node => [...node['0'].children].map(e => "
                                                                   "e.innerText)")
    expected_availability = from_string + " " + props['to'] + " " + to_string
    assert expected_availability in user_availabilities, "Expected availability not in availability list"
    print_success()


async def submit_availability_form(page):
    print_test_case_desc("Submitting availability form")
    await page.click("#applicationFormReviewBtn")
    print_success()


async def submit_entire_application(page):
    print_test_case_desc("Submitting the previously created application")
    await page.waitForSelector("#submitApplication")
    await page.click("#submitApplication")
    print_success()


asyncio.get_event_loop().run_until_complete(main())
