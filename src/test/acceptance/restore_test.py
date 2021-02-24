import asyncio
from pyppeteer import launch
from utils import *


async def main():
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)
    await page.click("#restore")
    await nap()
    await restore_password(page, RESTORE)
    await nap()
    await check_restore_result(page)
    await nap()
    await page.goto(BASE_URL + "/login")
    await nap()
    await login_with_new_credentials(page)
    await nap()
    await logout(page)
    await nap()
    await page.click("#restore")
    await nap()
    await restore_password_nonexisting_user(page)
    await nap()
    await check_restore_result(page)
    await nap()
    await page.goto(BASE_URL + "/restore")
    await nap()
    await restore_password_erroneous(page)
    await nap()
    await check_erroneous_restore(page)
    await nap()
    await page.goto(BASE_URL + "/restore")
    await nap()
    await restore_password_empty(page)
    await nap()
    await check_empty_restore(page)
    await browser.close()


async def fillout_restore_form(page, email):
    email_input = await page.J("#email")
    await email_input.type(email)
    await page.click("#restore-submit")


async def restore_password(page, email):
    print_test_case_desc("Filling out restore form with a valid email")
    await fillout_restore_form(page, RESTORE)
    print_success()


async def check_restore_result(page):
    print_test_case_desc("Checking that the correct message is shown")
    actual = await page.Jeval(".alert.alert-success", "node => node.innerText")
    expected = "An email has been sent, with login instructions, given that an account with that email existed"
    assert expected == actual, "Actual: {}\tExpected: {}".format(actual, expected)
    print_success()


async def login_with_new_credentials(page):
    print_test_case_desc("Checking that the restored account has acquired a new hard coded password")
    await login(page, RESTORE, RESTORE_PASS)
    print_success()


async def restore_password_nonexisting_user(page):
    print_test_case_desc("Filling out form with a valid email, but not present in database")
    await fillout_restore_form(page, "qwerty@example.com")
    print_success()


async def restore_password_erroneous(page):
    print_test_case_desc("Filling out form with an invalid email")
    await page.evaluate("document.forms['restoreForm'].email.removeAttribute('type')")
    await fillout_restore_form(page, "abcdefghjikl")
    await page.screenshot({"path": "erron.png"})
    print_success()


async def check_erroneous_restore(page):
    print_test_case_desc("Checking that erroneous input results in error")
    actual = await page.Jeval(".alert.alert-error", "node => node.innerText")
    expected = "The email entered is not a valid email"
    assert expected == actual, "Actual: {}\tExpected: {}".format(actual, expected)
    print_success()


async def restore_password_empty(page):
    print_test_case_desc("Filling out form with no input")
    await page.evaluate("document.forms['restoreForm'].email.removeAttribute('required')")
    await fillout_restore_form(page, "")
    await page.screenshot({"path": "erron.png"})
    print_success()


async def check_empty_restore(page):
    print_test_case_desc("Checking that empty input results in error")
    actual = await page.Jeval(".alert.alert-error", "node => node.innerText")
    expected = "Please enter an email"
    assert expected == actual
    print_success()


asyncio.get_event_loop().run_until_complete(main())
