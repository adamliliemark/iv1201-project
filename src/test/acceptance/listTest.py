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
    await page.click("#list-link", WAIT_OPTS)
    await search_for_existing_application(page)
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


async def check_first_page(page):
    print_test_case_desc("Checking that first page contains correct text.")
    await page.waitForSelector(".home-middle")
    assert (await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")) == ["You are an admin!"]


async def search_for_existing_application(page):
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
    first_name_string = "userFirstName"
    last_name_string = "userLastName"

    # enter into search fields
    await from_input.type(from_string[::-1])
    await to_input.type(to_string[::-1])
    await competence_input.type(competence_string)
    await first_name_input.type(first_name_string)
    await last_name_input.type(last_name_string)

    await page.screenshot({'path': 'login.png'})


asyncio.get_event_loop().run_until_complete(main())
