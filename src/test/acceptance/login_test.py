from pyppeteer import launch
from utils import *


async def main():
    props = load_properties_file("")
    browser = await launch(options=LAUNCH_OPTIONS)
    page = await browser.newPage()
    await retry_connect(BASE_URL, 20, page)

    await login_with_testuser_credentials(page)
    await nap()
    await check_testuser_login_redirect(page)
    await nap()
    await test_logout(page)
    await nap()
    await check_logout_redirect(page)
    await nap()
    await login_with_testadmin_credentials(page)
    await nap()
    await check_testadmin_login_redirect(page)
    await nap()
    await test_logout(page)
    await nap()
    await check_logout_redirect(page)
    await nap()
    await login_with_wrong_credentials(page)
    await nap()
    await check_wrong_login(page, props)
    await browser.close()


async def login_with_wrong_credentials(page):
    print_test_case_desc("Logging in with wrong credentials")
    await page.waitForSelector("#username")
    usr = await page.J("#username")
    await usr.type("wronguser@example.com")
    pw = await page.J("#password")
    await pw.type("wrongpass")
    await page.click("#loginbtn")
    print_success()


async def check_wrong_login(page, props):
    print_test_case_desc("Checking error message due to wrong credentials")
    error_message = await page.Jeval(".alert.alert-error", "node => node.innerText")
    expected_error_message = props['login.invalid']
    assert error_message == expected_error_message, "Unexpected error message: {}".format(error_message)
    print_success()


async def login_with_testuser_credentials(page):
    print_test_case_desc("Logging in with testuser credentials")
    await login(page, USER, PASS)
    print_success()


async def test_logout(page):
    print_test_case_desc("Logging out")
    await logout(page)
    print_success()


async def check_logout_redirect(page):
    print_test_case_desc("Checking that browser was redirected to /login")
    expected_url = "http://127.0.0.1:8080/login"
    assert expected_url == page.url, "Expected URL: {} Actual URL: {}".format(expected_url, page.url)
    print_success()


async def check_testuser_login_redirect(page):
    print_test_case_desc("Checking that testuser was redirected to /home")
    expected_url = "http://127.0.0.1:8080/home"
    assert expected_url == page.url, "Expected URL: {} Actual URL: {}".format(expected_url, page.url)
    print_success()


async def login_with_testadmin_credentials(page):
    print_test_case_desc("Logging in with testadmin credentials")
    await login(page, ADMIN, PASS)
    print_success()


async def check_testadmin_login_redirect(page):
    print_test_case_desc("Checking that testadmin was redirected to /admin")
    expected_url = "http://127.0.0.1:8080/admin"
    assert expected_url == page.url, "Expected URL: {} Actual URL: {}".format(expected_url, page.url)
    print_success()


asyncio.get_event_loop().run_until_complete(main())
