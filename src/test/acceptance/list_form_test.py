from pyppeteer import launch
from utils import *


async def main():
    props = load_properties_locally("")
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await login(page, ADMIN, PASS)
    await nap()
    await page.click("#list-link", WAIT_OPTS)
    await nap()
    await only_one_date(page, props)
    await browser.close()


async def only_one_date(page, props):
    print_test_case_desc("Checking entering only one date results in error")
    await nap()
    from_input = await page.J("#from")
    await nap()
    await nap()
    await nap()
    await nap()
    await from_input.type("01-01-2222")
    await nap()
    await nap()
    await nap()
    await page.click("#listbutton")
    await nap()
    await nap()
    await nap()
    actual_error = await page.Jeval("#js-error-text", "node => node.innerText")
    print (actual_error)
    await nap()
    expected_error = props['error.oneDate']

    assert actual_error == expected_error, "Actual {}\tExpected: {}".format(actual_error, expected_error)

    print_success()


asyncio.get_event_loop().run_until_complete(main())
