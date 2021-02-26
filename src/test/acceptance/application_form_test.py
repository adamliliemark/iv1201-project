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
    await null_years(page, props)
    await nap()
    await page.click("#competenceFormSubmit")
    await nap()
    await wrong_order_availability_dates(page, props)
    await nap()
    await page.click("#apply-link", WAIT_OPTS)
    await nap()
    await page.click("#competenceFormSubmit")
    await nap()
    await wrong_type_availability_dates(page, props)
    await nap()
    await page.click("body")
    await page.click("#apply-link", WAIT_OPTS)
    await nap()
    await page.click("#competenceFormSubmit")
    await nap()
    await null_availability_dates(page, props)
    await browser.close()


async def null_years(page, props):
    print_test_case_desc("Checking that en empty input in competence years results in error")
    years = await page.J("#competenceYears")
    await years.type("")
    await page.click("#competenceFormAdd")
    actual_error = await page.Jeval("#js-error-text", "node => node.innerText")
    expected_error = props['error.competenceYearsMissing']
    assert actual_error == expected_error, "Actual: {}\tExpected: {}".format(actual_error, expected_error)
    await page.click("body")
    print_success()


async def null_availability_dates(page, props):
    print_test_case_desc("Checking that an empty input in availability dates results in error")
    await page.evaluate("document.forms['availabilityForm'].from.removeAttribute('required')")
    await page.evaluate("document.forms['availabilityForm'].to.removeAttribute('required')")
    await page.click("#availabilityFormSubmit")
    await nap()
    expected_error = [props['error.form.availability.datesInWrongOrder'], props['error.form.from.null'],
                      props['error.form.to.null']]
    actual_errors = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(n => n.innerText)")
    assert expected_error.sort() == actual_errors.sort(), f"\nActual: {actual_errors}\nExpected: {expected_error}"
    assert len(actual_errors) == 3, f"\nActual: {len(actual_errors)}\nExpected: {3}"
    print_success()


async def wrong_type_availability_dates(page, props):
    print_test_case_desc("Checking that wrong input in availability dates results in error")
    await page.evaluate("document.forms['availabilityForm'].from.removeAttribute('type')")
    await page.evaluate("document.forms['availabilityForm'].to.removeAttribute('type')")
    await page.evaluate("document.forms['availabilityForm'].from.removeAttribute('pattern')")
    await page.evaluate("document.forms['availabilityForm'].to.removeAttribute('pattern')")
    from_input = await page.J("#from")
    to_input = await page.J("#to")
    await from_input.type("wrong")
    await to_input.type("wrong")
    await page.click("#availabilityFormSubmit")
    await nap()
    actual_errors = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(n => n.innerText)")
    expected_errors = [props['error.from.typeMismatch'], props['error.to.typeMismatch'],
                       props['error.form.availability.datesInWrongOrder']]
    assert expected_errors.sort() == actual_errors.sort(), f"\nActual: {actual_errors}\nExpected: {expected_errors}"
    assert len(actual_errors) == 3, "Incorrect amount of errors, expected {}, got {}".format(len(actual_errors), 3)
    print_success()


async def wrong_order_availability_dates(page, props):
    print_test_case_desc("Checking that dates in wrong order results in error")
    from_input = await page.J("#from")
    to_input = await page.J("#to")
    await from_input.type("03-03-2222")
    await to_input.type("02-02-2222")
    await page.click("#availabilityFormSubmit")
    await nap()
    actual_error = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(c => c.innerText)")
    expected_error = props['error.form.availability.datesInWrongOrder']
    assert expected_error in actual_error
    await page.click("body")
    print_success()


asyncio.get_event_loop().run_until_complete(main())
