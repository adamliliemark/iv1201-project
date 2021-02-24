import asyncio
from pyppeteer import launch
from utils import *


async def main():
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await page.setExtraHTTPHeaders({'Content-Language': 'sv-SE', 'Accept-Language': 'sv-SE,sv;q=0.9'})
    await retry_connect(BASE_URL, 20, page)
    await login_page(page)
    await login(page, USER, PASS)
    await nap()
    await browser.close()


async def login_page(page):
    print_test_case_desc("Checking if login page is translated")
    await page.screenshot({"path": "i8n.png"})
    actual_legend = await page.Jeval("legend", "node => node.innerText")
    expected_legend = "Vänligen logga in."
    assert actual_legend == expected_legend, "Actual: {}\tExpected: {}".format(actual_legend, expected_legend)
    print_success()



asyncio.get_event_loop().run_until_complete(main())
