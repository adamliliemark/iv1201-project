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
    await home_page(page)
    await nap()
    await page.click("#apply-link", WAIT_OPTS)
    await nap()
    await user_translation_table(page)
    await logout(page)
    await nap()
    await login(page, ADMIN, PASS)
    await nap()
    await admin_page(page)
    await page.click("#list-link", WAIT_OPTS)
    await nap()
    await list_translation_table(page)
    await browser.close()


async def login_page(page):
    print_test_case_desc("Checking if login page is translated")
    actual_legend = await page.Jeval("legend", "node => node.innerText")
    expected_legend = "Vänligen logga in."
    assert actual_legend == expected_legend, "Actual: {}\tExpected: {}".format(actual_legend, expected_legend)
    print_success()


async def home_page(page):
    print_test_case_desc("Checking if home page is translated")
    actual_text = await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")
    expected_text = "Du är endast en simpel användare..."
    assert expected_text in actual_text, "Actual: {}\tExpected: {}".format(actual_text, expected_text)
    print_success()


async def user_translation_table(page):
    print_test_case_desc("Checking that the values in the user competences list is translated")
    await page.waitForSelector("#competence")
    expected_competences = ["Karuselldrift", "Korvgrillning"]
    competences = await page.JJeval("#competence", "node => [...node['0'].children].map(e => e.value)")
    assert len(competences) == len(expected_competences), "Wrong length of competence selector"
    for competence in expected_competences:
        assert competence in competences, "Actual: {}\tExpected: {}".format(competences, expected_competences)
    print_success()


async def admin_page(page):
    print_test_case_desc("Checking that admin page is translated")
    expected_message = "Du är en administratör!"
    actual_message = await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")
    assert expected_message in actual_message, "Expected: {}\tActual: {}".format(expected_message, actual_message)
    print_success()


async def list_translation_table(page):
    print_test_case_desc("Checking that the values in the admin competences list is translated")
    expected_competences = ["Karuselldrift", "Korvgrillning"]
    actual_competences = await page.JJeval("#competence", "node => [...node['0'].children].map(e => e.value)")
    assert len(actual_competences) == len(expected_competences), "Actual: {}\tExpected: {}".format(actual_competences, expected_competences)
    for competence in expected_competences:
        assert competence in actual_competences, "Actual: {}\tExpected: {}".format(actual_competences, expected_competences)
    print_success()


asyncio.get_event_loop().run_until_complete(main())
