from pyppeteer import launch
from utils import *


async def main():
    props = load_properties_locally("")
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await page.setJavaScriptEnabled(enabled=True)

    await login(page, ADMIN, PASS)
    await nap()
    await page.click("#list-link")
    await nap()
    await only_one_date(page, props)
    await nap()
    await page.screenshot({"path": "after.png"})
    await browser.close()


async def only_one_date(page, props):
    print_test_case_desc("Checking entering only one date results in error")
    #await page.evaluate("document.forms['listForm'].from.valueAsDate = (new Date())")
    from_input = await page.J("#from")
    await from_input.type("01-01-2222")
    await page.click("#listbutton")
    actual_errors = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(n => n.innerText)")
    expected_error = props['error.oneDate']
    assert expected_error in actual_errors, "Actual {}\tExpected: {}".format(actual_errors, expected_error)

    print_success()


asyncio.get_event_loop().run_until_complete(main())
