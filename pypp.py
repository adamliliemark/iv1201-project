
import asyncio
import time
from pyppeteer import launch


async def main():
    time.sleep(30)
    browser = await launch(
        options=
        {
            'args': ['--no-sandbox --lang=en_US']
        })
    page = await browser.newPage()
    await page.goto('http://127.0.0.1:8080/')
    page.on('domcontentloaded')
    await login(page)
    page.on('domcontentloaded')
    await check_first_page(page)
    page.on('domcontentloaded')
    await page.click("#apply-link")
    page.on('domcontentloaded')
    await check_translation_table(page)
    page.on('domcontentloaded')
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
    print("Checking that first page contains correct text.")
    assert (await page.JJeval(".home-middle", "node => node.map(n => n.innerText)")) == ["You are a simple user..."]


# this obviously needs to switch to Swedish and be tried again.


async def check_translation_table(page):
    print(await page.JJeval("#competence", "node => node.map(n => n.value)"))
    # await page.screenshot({'path': 'translation.png'})
    print("Checking that the value in the competences list is translated to English")
    assert (await page.JJeval("#competence", "node => node.map(n => n.value)")) == ["Carousel operation"]


async def enter_and_check_competence_years(page):
    print("Entering 2.0 to competence years and checks that it is updated on the page")
    years = await page.J("#competenceYears")
    await years.type("2.0")
    # await page.screenshot({'path': 'competence_years.png'})


asyncio.get_event_loop().run_until_complete(main())
