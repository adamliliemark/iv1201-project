from pyppeteer import launch
from utils import *


async def main():
    props = load_properties_file("")
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await page.setJavaScriptEnabled(enabled=True)

    await login(page, ADMIN, PASS)
    await nap()
    await page.click("#list-link")
    await nap()
    await only_from_date(page, props)
    await nap()
    await page.click("#list-link")
    await nap()
    await only_to_date(page, props)
    await nap()
    await page.click("#list-link")
    await nap()
    await wrong_order_list_dates(page, props)
    await nap()
    await browser.close()


async def only_from_date(page, props):
    print_test_case_desc("Checking entering only from date results in error")
    from_input = await page.J("#from")
    await from_input.type("01-01-2222")
    await page.click("#listbutton")
    await nap()
    actual_errors = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(n => n.innerText)")
    expected_error = props['error.oneDate']
    assert expected_error in actual_errors, "Actual {}\tExpected: {}".format(actual_errors, expected_error)
    await page.click("body")
    print_success()


async def only_to_date(page, props):
    print_test_case_desc("Checking entering only to date results in error")
    to_input = await page.J("#to")
    await to_input.type("01-01-2222")
    await page.click("#listbutton")
    await nap()
    actual_errors = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(n => n.innerText)")
    expected_error = props['error.oneDate']
    assert expected_error in actual_errors, "Actual {}\tExpected: {}".format(actual_errors, expected_error)
    await page.click("body")
    print_success()


async def wrong_order_list_dates(page, props):
    print_test_case_desc("Checking that dates in wrong order results in error")
    from_input = await page.J("#from")
    to_input = await page.J("#to")
    await from_input.type("03-03-2222")
    await to_input.type("02-02-2222")
    await page.click("#listbutton")
    await nap()
    actual_error = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(c => c.innerText)")
    expected_error = props['error.form.list.wrongOrder']
    assert expected_error in actual_error
    await page.click("body")
    print_success()

asyncio.get_event_loop().run_until_complete(main())
