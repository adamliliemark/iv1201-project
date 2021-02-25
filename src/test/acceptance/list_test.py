from pyppeteer import launch
from utils import *


async def main():
    props = load_properties_file("")
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await login(page, ADMIN, PASS)
    await nap()
    await page.click("#list-link", WAIT_OPTS)
    await nap()
    await search_for_existing_application(page)
    await nap()
    await check_existing_application_search(page)
    await nap()
    await search_for_non_existing_application(page)
    await nap()
    await check_non_existing_application_search(page, props)
    await browser.close()


# hard coded global variables to be reused in multiple functions
first_name_string = "userFirstName"
last_name_string = "userLastName"


async def search_for_existing_application(page):
    print_test_case_desc("Searching for an application that was added in application_test.py")

    # fetch input fields
    from_input = await page.J("#from")
    to_input = await page.J("#to")
    competence_input = await page.J("#competence")
    first_name_input = await page.J("#firstname")
    last_name_input = await page.J("#lastname")

    # hard coded values
    from_string = "2222-02-02"
    to_string = "2222-03-03"
    competence_string = "Grilling sausage"

    # enter into search fields
    await from_input.type(from_string[::-1])
    await to_input.type(to_string[::-1])
    await competence_input.type(competence_string)
    await first_name_input.type(first_name_string)
    await last_name_input.type(last_name_string)

    # submit form
    await page.click("#listbutton")
    print_success()


async def check_existing_application_search(page):
    print_test_case_desc("Checking that the correct application showed up on the page")

    user_application = await page.JJeval(".userApplication", "node => node.map(n => n.innerText)")
    expected_user_application = "1: " + first_name_string + " " + last_name_string
    assert expected_user_application in user_application, "Expected: {}\t" \
                                                          "Actual: {}".format(expected_user_application, user_application)
    print_success()


async def search_for_non_existing_application(page):
    print_test_case_desc("Searching for an application that does not exist")

    # click on list-link to clear form
    await page.click("#list-link", WAIT_OPTS)
    # allow some time for reload
    await nap()

    # hard coded values not in database
    from_string = "3333-02-02"
    to_string = "3333-03-03"

    # entering values to fields
    from_input = await page.J("#from")
    to_input = await page.J("#to")
    await from_input.type(from_string[::-1])
    await to_input.type(to_string[::-1])

    # submit form
    await page.click("#listbutton")
    print_success()


async def check_non_existing_application_search(page, props):
    print_test_case_desc("Checking that the no application showed up on the page")
    found_application = await page.JJeval(".applications", "node => node.map(n => n.innerText)")
    expected_user_application = props['list.noApp']
    assert expected_user_application in found_application, "Applicant found when no" \
                                                           " one was expected in: {}".format(found_application)
    print_success()


asyncio.get_event_loop().run_until_complete(main())
