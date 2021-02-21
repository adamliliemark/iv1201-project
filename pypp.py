
import asyncio
import time
from pyppeteer import launch


def printTestCaseDesc(desc):
    print(" - {}".format(desc))

async def main():
    #time.sleep(30)
    browser = await launch(
        options=
        {
            'args': ['--no-sandbox --lang=en_US']
        })
    page = await browser.newPage()
    await page.goto('http://127.0.0.1:8080/')
    await login(page)
    await check_first_page(page)
    await page.click("#apply-link")
    await check_translation_table(page)
    await enter_and_check_competence_years(page)
    # raise Exception("this is a dummy exception that should kill the process")
    await browser.close()


async def login(page):
    usr = await page.J("#username")
    await usr.type("testuser@example.com")
    pw = await page.J("#password")
    await pw.type("pass")
    # await page.screenshot({'path': 'login.png'})
    await page.click("#loginbtn")


async def check_first_page(page):
    printTestCaseDesc("Checking that first page contains correct text.")
    await page.content()
    assert (await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")) == ["You are a simple user..."]


# this obviously needs to switch to Swedish and be tried again.


async def check_translation_table(page):
    printTestCaseDesc("Checking that the value in the competences list is translated to English")
    expectedCompetences = ["Carousel operation", "Grilling sausage"]
    await page.content()
    competences = await page.JJeval("#competence", "node => [...node['0'].children].map(e => e.value)")
    assert len(competences) == len(expectedCompetences), "Wrong length of competence selector"
    for competence in expectedCompetences:
        assert competence in competences, "Expected competence {} not in competence selector".format(competence)

async def enter_and_check_competence_years(page):
    printTestCaseDesc("Entering 2.0 to competence years and checks that it is updated on the page")
    await page.content()
    years = await page.J("#competenceYears")
    await years.type("2.0")
    # await page.screenshot({'path': 'competence_years.png'})


asyncio.get_event_loop().run_until_complete(main())
