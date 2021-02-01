#!/usr/bin/python3

#This script migrates from an existing legacy schema to an existing new schema.
#The new schema may or may not be empty
#The script outputs the data-import script into "migrated_database_pop.sql" and requires python mysql connector
#pip install mysql-connector-python
import mysql.connector
import subprocess
import os
import argparse, sys
import bcrypt

TEST = False
DEBUG = False
MANUALTRANSLATE = False

parser=argparse.ArgumentParser()
parser.add_argument('-u', help='mysql username', required=True)
parser.add_argument('-p', help='mysql password', required=True)
parser.add_argument('-oldschema', help='legacy schema name', required=True)
parser.add_argument('-test', action="store_true", help='tests migration script by first creating the old database')
parser.add_argument('-debug', action="store_true", help='outputs each statement before executing')

args=parser.parse_args()

#Connect to mysql server
user=args.u
password=args.p
schema=args.oldschema
tempSchema = schema + "_temp"
if args.test:
    TEST = True
if args.debug:
    DEBUG = True

print("This script will migrate the schema " + schema + " on localhost and output insertion statements into migrated_database_pop.sql")
if TEST:
    print("\n!WARNING! The script will first DROP and recreate the schema " + schema + " (localhost)\nas well as DROP and then create the schema " + 
    "recruitmentdb (that the script can be validated against).\nto disable this, omit the argument -test")   
cont = input("Continue y/n\n")
if cont[0].lower() != "y":
    print("aborted")
    exit()

tr = input("Do you want to manually provide competence translations for 'en_US'? (if no, no translations will be generated) y/n\n")
if tr[0].lower() == "y":
    MANUALTRANSLATE = True

conn = mysql.connector.connect(
  host="localhost",
  user=user,
  password=password
)
cursor = conn.cursor()

def executeSQLScript(filename):
    fd = open(filename, 'r')
    sqlFile = fd.read()
    fd.close()
    sqlCommands = sqlFile.split(';')
    for command in sqlCommands:
        if DEBUG:
            print ("COMMAND: " + command)
        if command.strip() != '':
            cursor.execute(command)
    conn.commit()

#For testing (validate output): Create the new database structure
if TEST: 
    executeSQLScript('creat_new_db_no_data.sql')

#For testing: remove and recreate example legacy schema
if TEST:
    executeSQLScript('creat_old_db.sql')

#Create temp schema and dump old schema into it
cursor.execute("drop schema if exists {}".format(tempSchema))
cursor.execute("create schema {}".format(tempSchema))
conn.commit()
dumpCommand = "mysqldump -u{} -p{} {} | mysql -u{} -p{} {}".format(user, password, schema, user, password, tempSchema)
os.system(dumpCommand)

#Modify the temp schema to fit the new schema
cursor.execute("use {}".format(tempSchema))
executeSQLScript('modify_temp.sql')

#Important: Hash the migrated passwords using BCRYPT 
cursor.execute("select password, email from users")
passwordEmail = cursor.fetchall()
for usr in passwordEmail:
    if usr[0] != None and False:
        salt = bcrypt.gensalt()
        hashedPass = bcrypt.hashpw(user[0].encode("utf-8"), salt) 
        cursor.execute("update users set password = {} where email = {}".format(hashedPass.decode('utf-8'), usr[1]))

#Also hash the passwords of incomplete users for obvious reasons 
cursor.execute("select password, person_id from unmigrated_person")
unmigratedPasswordEmail = cursor.fetchall()
for usr in unmigratedPasswordEmail:
    if user[0] != None and False:
        salt = bcrypt.gensalt()
        hashedPass = bcrypt.hashpw(usr[0].encode("utf-8"), salt) 
        cursor.execute("update unmigrated_person set password = '{}' where person_id = {}".format(hashedPass.decode('utf-8'), usr[1]))

#Manual translation process if selected
if MANUALTRANSLATE:
    cursor.execute("select text, competence_id from competence_translation")
    competenceNameIDLookup = cursor.fetchall()
    for lookup in competenceNameIDLookup:  
        enName = input("Please provide an english (en_US) translation for " + lookup[0] + ":\n")
        cursor.execute("insert into competence_translation(text, language_language_code, competence_id) values('{}','en_US', {})".format(enName, lookup[1]))
conn.commit()

#Dump the insertion statements into output file
os.system("mysqldump -u{} -p{} --no-create-info --complete-insert --skip-triggers {} > migrated_database_pop.sql".format(user, password, tempSchema))
#Finally drop the temp schema
cursor.execute("drop schema {}".format(tempSchema))
conn.commit()
if TEST:
    print("\t!!VALIDATING OUTPUT AGAINST NEW SCHEMA!!")
    cursor.execute("use recruitmentdb")
    executeSQLScript('migrated_database_pop.sql')
    print("\t!!TEST MIGRATION SUCCESSFULLY VALIDATED WITH NO ERRORS!!")
print("Done - output: migrated_database_pop.sql")