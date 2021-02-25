import pyppeteer as pypp
import asyncio
from pyjavaproperties import Properties
import os

BASE_URL = "http://127.0.0.1:8080"
WAIT_OPTS = {"waitUntil": "networkidle0"}
SELECTOR_WAIT = {"timeout": "1000"}
LAUNCH_OPTIONS = {'args': ['--no-sandbox']}
USER = "testuser@example.com"
ADMIN = "testadmin@example.com"
RESTORE = "anonymous@kth.se"
PASS = "pass"
RESTORE_PASS = "new_password"


def load_properties_file(locale):
    props = Properties()
    props.load(open(os.path.abspath(os.path.join(os.path.abspath(os.path.join(os.getcwd(), os.pardir)), os.pardir)) +
                    '/iv1201-project/iv1201-project/src/main/resources/messages' + locale + '.properties', mode='r'))
    return props


def load_properties_locally(locale):
    props = Properties()
    props.load(open(os.path.abspath(os.path.join(os.path.abspath(os.path.join(os.getcwd(), os.pardir)), os.pardir)) +
                    locale + '.properties', mode='r'))
    return props


async def perform_action(action, args):
    for i in range(10):
        try:
            return await action(args)
        except pypp.errors.NetworkError:
            print("Something went wrong but I'm ignoring it.")


async def nap():
    await asyncio.sleep(0.5)


def print_test_case_desc(desc):
    print(" - {}".format(desc), end=" ")


def print_success():
    print("\t\t\N{check mark}")


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
