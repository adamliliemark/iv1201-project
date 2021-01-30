This script creates sql queries that can be used to populate a database of the new format with the data from the legacy database
You can chose to optionally translate the competence names as the script runs.

usage: migrate.py [-h] -u U -p P -oldschema OLDSCHEMA [-test] [-debug]

to test pass -test argument
The script will then validate the output against an example database structure

>chmod +x migrate_db.sh
>./migrate.py -u SOMEUSER -p SOMEPASS -oldschema SOMESCHEMA
Outputs into:
migrated_database_pop.sql
