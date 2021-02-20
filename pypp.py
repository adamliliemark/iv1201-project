
import asyncio
import time
from pyppeteer import launch


async def main():
    time.sleep(15)
    browser = await launch(options={'args': ['--no-sandbox']})
    page = await browser.newPage()
    await page.goto('http://127.0.0.1:8080/')
    await login(page)
    await page.screenshot({'path': 'example2.png'})
    await browser.close()


async def login(page):
    usr = await page.J("#username")
    await usr.type("testuser@example.com")
    pw = await page.J("#password")
    await pw.type("pass")
    await page.screenshot({'path': 'example1.png'})
    await page.click("#loginbtn")


asyncio.get_event_loop().run_until_complete(main())