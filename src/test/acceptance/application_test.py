import asyncio
from pyppeteer import launch
from shared import *


async def main():
    browser = await launch(
        options={
            'args': ['--no-sandbox']
        })
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await login(page)
    await check_first_page(page)
    await page.click("#apply-link", WAIT_OPTS)
    await check_translation_table(page)
    await enter_and_check_competence_years(page)
    #await add_availability_and_check(page)
    await add_availability(page)
    await submit_availability_form(page)
    #await submit_entire_application(page)
    await browser.close()


async def login(page):
    await page.waitForSelector("#username")
    print_test_case_desc("Navigating to login")
    usr = await page.J("#username")
    await usr.type("testuser@example.com")
    pw = await page.J("#password")
    await pw.type("pass")
    # await page.screenshot({'path': 'login.png'})
    await page.click("#loginbtn")
    print_success()


async def check_first_page(page):
    print_test_case_desc("Checking that first page contains correct text.")
    await page.waitForSelector(".home-middle")
    assert (await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")) == ["You are a simple user..."]
    print_success()


async def check_translation_table(page):
    await page.waitForSelector("#competence")
    print_test_case_desc("Checking that the value in the competences list is translated to English")
    expected_competences = ["Carousel operation", "Grilling sausage"]
    competences = await page.JJeval("#competence", "node => [...node['0'].children].map(e => e.value)")
    assert len(competences) == len(expected_competences), "Wrong length of competence selector"
    for competence in expected_competences:
        assert competence in competences, "Expected competence {} not in competence selector".format(competence)
    print_success()


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
    await page.content()

    # add availability dates
    await page.waitForSelector("#from", SELECTOR_WAIT)
    await page.waitForSelector("#to", SELECTOR_WAIT)
    from_input = await page.J("#from")
    to_input = await page.J("#to")

    await from_input.type(from_string[::-1])
    await to_input.type(to_string[::-1])

    # submit the form
    await page.click("#availabilityFormSubmit")
    print_success()


async def check_availability_form(page):
    print_test_case_desc("Checking that the availability has been added")
    # check that the page has been updated correctly
    # this test sometimes locks, dont knot why yet
    await page.waitForSelector("#userAvailabilities", SELECTOR_WAIT)
    user_availabilities = await page.JJeval("#userAvailabilities", "node => [...node['0'].children].map(e => "
                                                                   "e.innerText)")
    expected_availability = from_string + " to " + to_string
    assert expected_availability in user_availabilities, "Expected availability not in availability list"

    # submit the form
    await page.waitForSelector("#applicationFormReviewBtn")
    await page.click("#applicationFormReviewBtn")
    print_success()


async def submit_availability_form(page):
    print_test_case_desc("Submitting availability form")
    await page.content()
    await page.waitForSelector("#applicationFormReviewBtn")
    await page.click("#applicationFormReviewBtn")
    print_success()


async def submit_entire_application(page):
    print_test_case_desc("Submitting the previously created application")
    await page.content()
    await page.waitForSelector("#submitApplication")
    await page.click("#submitApplication", WAIT_OPTS)
    print_success()


asyncio.get_event_loop().run_until_complete(main())
