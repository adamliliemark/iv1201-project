import asyncio
from pyppeteer import launch
from utils import *

async def main():
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await login(page, USER, PASS)
    await nap()
    await page.click("#apply-link", WAIT_OPTS)
    await nap()
    await null_years_js(page)
    await nap()
    await page.click("#competenceFormSubmit")
    await nap()
    await wrong_order_availability_dates(page)
    await nap()
    await page.click("#apply-link", WAIT_OPTS)
    await nap()
    await page.click("#competenceFormSubmit")
    await nap()
    await wrong_type_availability_dates(page)
    await nap()
    await page.click("#apply-link", WAIT_OPTS)
    await nap()
    await page.click("#competenceFormSubmit")
    await nap()
    await null_availability_dates(page)
    await browser.close()


async def null_years_js(page):
    print_test_case_desc("Checking that en empty input in competence years results in error")
    years = await page.J("#competenceYears")
    await years.type("")
    await page.click("#competenceFormAdd")
    actual_error = await page.Jeval("#js-error-text", "node => node.innerText")
    expected_error = "Years of experience must be supplied."
    assert actual_error == expected_error, "Actual: {}\tExpected: {}".format(actual_error, expected_error)
    await page.click("body")
    print_success()


async def wrong_years_nonjs(page):
    csrf = await page.J("#_csrf")
    await page.setRequestInterception(True)
    page.on('request', "interceptedRequest => {"
                       "   var data = {"
                       "      'method': 'POST',"
                       "      'postData': 'name=Carousel+operation&years=NaN'"
                       "};"
                       "interceptedRequest.continue(data);"
                       "});")
    await page.click("#apply-link")
    await page.screenshot({"path": "int.png"})


async def null_availability_dates(page):
    print_test_case_desc("Checking that an empty input in availability dates results in error")
    await page.evaluate("document.forms['availabilityForm'].from.removeAttribute('required')")
    await page.evaluate("document.forms['availabilityForm'].to.removeAttribute('required')")
    await page.click("#availabilityFormSubmit")
    await nap()
    actual_error = await page.Jeval("#fallback-error", "node => node.innerText")
    expected_error = "There was an unexpected error when validation your data, for instance data of the wrong type. " \
                     "Please enter an appropriate value and try again."
    assert actual_error == expected_error, "\nActual: {}\nExpected: {}".format(actual_error, expected_error)
    print_success()


async def wrong_type_availability_dates(page):
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
    actual_error = await page.Jeval("#fallback-error", "node => node.innerText")
    expected_error = "There was an unexpected error when validation your data, for instance data of the wrong type. " \
                     "Please enter an appropriate value and try again."
    assert actual_error == expected_error, "\nActual: {}\nExpected: {}".format(actual_error, expected_error)
    print_success()


async def wrong_order_availability_dates(page):
    print_test_case_desc("Checking that dates in wrong order results in error")
    from_input = await page.J("#from")
    to_input = await page.J("#to")
    await from_input.type("03-03-2222")
    await to_input.type("02-02-2222")
    await page.click("#availabilityFormSubmit")
    await nap()
    actual_error = await page.JJeval("#th-error-label", "node => [...node['0'].children].map(c => c.innerText)")
    expected_error = "From date can not be after to date."
    assert expected_error in actual_error
    await page.click("body")
    print_success()


async def submit_entire_application(page):
    print_test_case_desc("Submitting the previously created application")
    await page.waitForSelector("#submitApplication")
    await page.click("#submitApplication")
    print_success()


asyncio.get_event_loop().run_until_complete(main())
