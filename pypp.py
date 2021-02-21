
import asyncio
from pyppeteer import launch
import pyppeteer as pypp

BASE_URL = "http://127.0.0.1:8080"
WAIT_OPTS = {"waitUntil": "networkidle0"}


def printTestCaseDesc(desc):
    print(" - {}".format(desc))


async def retryConnect(url, retries, page):
    if retries <= 0:
        print("Connection failed!")
        return
    else:
        try:
            await page.goto(url)
            print("Connected!")
        except pypp.errors.PageError:
            print("\nretrying start connection")
            await asyncio.sleep(4)
            await retryConnect(url, retries-1, page) 


async def main():
    browser = await launch(
        options={
            'args': ['--no-sandbox --lang=en_US']
        })
    page = await browser.newPage()
    await retryConnect(BASE_URL, 20, page)

    await login(page)
    await check_first_page(page)
    await page.click("#apply-link", WAIT_OPTS)
    await check_translation_table(page)
    await enter_and_check_competence_years(page)
    # raise Exception("this is a dummy exception that should kill the process")
    await browser.close()


async def login(page):
    await page.waitForSelector("#username")
    printTestCaseDesc("Navigating to login")
    usr = await page.J("#username")
    await usr.type("testuser@example.com")
    pw = await page.J("#password")
    await pw.type("pass")
    # await page.screenshot({'path': 'login.png'})
    await page.click("#loginbtn")


async def check_first_page(page):
    printTestCaseDesc("Checking that first page contains correct text.")
    await page.waitForSelector(".home-middle")
    assert (await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")) == ["You are a simple user..."]


async def check_translation_table(page):
    await page.waitForSelector("#competence")
    printTestCaseDesc("Checking that the value in the competences list is translated to English")
    expectedCompetences = ["Carousel operation", "Grilling sausage"]
    competences = await page.JJeval("#competence", "node => [...node['0'].children].map(e => e.value)")
    assert len(competences) == len(expectedCompetences), "Wrong length of competence selector"
    for competence in expectedCompetences:
        assert competence in competences, "Expected competence {} not in competence selector".format(competence)


async def enter_and_check_competence_years(page):
    printTestCaseDesc("Entering 2.0 to competence years and checks that it is updated on the page")
    await page.content()
    years = await page.J("#competenceYears")
    competence_to_choose = "Grilling sausage"
    await page.select("#competence", competence_to_choose)
    await years.type("2.0")
    await page.click("#competenceFormSubmit", WAIT_OPTS)
    user_competences = await page.JJeval("#userCompetences", "node => node.map(n => n.innerText)")
    print(user_competences)
    await page.screenshot({'path': 'competence_years.png', 'fullPage': True})



asyncio.get_event_loop().run_until_complete(main())
