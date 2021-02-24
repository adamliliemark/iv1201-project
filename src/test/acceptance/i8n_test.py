import asyncio
from pyppeteer import launch
from utils import *


async def main():
    browser = await launch(options=LAUNCH_OPTIONS_SE)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)
    await login_page(page)
    await browser.close()


async def login_page(page):
    print_test_case_desc("Checking if login page is translated")
    actual_legend = await page.Jeval("legend", "node => node.innerText")
    expected_legend = "Vänligen logga in"
    assert actual_legend == expected_legend, "Actual: {}\tExpected: {}".format(actual_legend, expected_legend)


asyncio.get_event_loop().run_until_complete(main())
