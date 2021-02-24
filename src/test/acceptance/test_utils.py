import pyppeteer as pypp
import asyncio

BASE_URL = "http://127.0.0.1:8080"
WAIT_OPTS = {"waitUntil": "networkidle0"}
SELECTOR_WAIT = {"timeout": "1000"}
LAUNCH_OPTIONS_EN = {'args': ['--no-sandbox']}


async def nap():
    await asyncio.sleep(0.5)


def print_test_case_desc(desc):
    print(" - {}".format(desc), end=" ")


def print_success():
    print("   \N{check mark}")


async def retry_connect(url, retries, page):
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
            await retry_connect(url, retries - 1, page)


async def login(page, user, password):
    await page.waitForSelector("#username")
    usr = await page.J("#username")
    await usr.type(user)
    pw = await page.J("#password")
    await pw.type(password)
    await page.click("#loginbtn")


async def logout(page):
    await page.waitForSelector("#logoutBtn")
    await page.click("#logoutBtn")

