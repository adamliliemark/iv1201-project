import asyncio
from pyppeteer import launch
from shared import *


async def main():
    browser = await launch(
        options={
            'args': ['--no-sandbox --lang=en_US']
        })
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await login(page)
    await check_first_page(page)
    await page.click("#list-link", WAIT_OPTS)
    await search_for_existing_application(page)
    await check_existing_application_search(page)
    await search_for_non_existing_application(page)
    await check_non_existing_application_search(page)
    # raise Exception("this is a dummy exception that should kill the process")
    await browser.close()


async def login(page):
    await page.waitForSelector("#username")
    print_test_case_desc("Navigating to login")
    usr = await page.J("#username")
    await usr.type("testadmin@example.com")
    pw = await page.J("#password")
    await pw.type("pass")
    # await page.screenshot({'path': 'login.png'})
    await page.click("#loginbtn")
    print_success()


async def check_first_page(page):
    print_test_case_desc("Checking that first page contains correct text.")
    await page.waitForSelector(".home-middle")
    assert (await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")) == ["You are an admin!"]
    print_success()


# hard coded global variables to be reused in multiple functions
first_name_string = "userFirstName"
last_name_string = "userLastName"


async def search_for_existing_application(page):
    print_test_case_desc("Searching for an application that was added in tempapplication_test.py")
    await page.content()

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
    await page.content()

    user_application = await page.JJeval(".userApplication", "node => node.map(n => n.innerText)")
    expected_user_application = "1: " + first_name_string + " " + last_name_string
    assert expected_user_application in user_application, "Wrong applicant found."
    print_success()


async def search_for_non_existing_application(page):
    print_test_case_desc("Searching for an application that does not exist")

    # click on list-link to clear form
    await page.click("#list-link", WAIT_OPTS)
    await page.content()

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


async def check_non_existing_application_search(page):
    print_test_case_desc("Checking that the no application showed up on the page")
    await page.content()

    found_application = await page.JJeval(".applications", "node => node.map(n => n.innerText)")
    expected_user_application = "No applications matched your search"
    assert expected_user_application in found_application, "Applicant found when no one was expected."
    print_success()

asyncio.get_event_loop().run_until_complete(main())
