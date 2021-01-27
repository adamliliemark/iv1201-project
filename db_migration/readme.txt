This script creates sql queries that can be used to populate a database of the new format with the data from the legacy database
To verify the migration:
Create and populate a legacy DB using creat_old.sql and populate_old.sql
then
>chmod +x migrate_db.sh
>./migrate_db.sh -u <mysqlusername> -p <musqlpassword> -s <legacy_db_scheme>

Should output
migrated_database_pop.sql

Make sure that the new database exists (it is created automatically when launcing the spring application).
running the output sql file on the new database will populate it with the migrated data.
unmigrateable users (users that have missing essential info) are stored in unmigrated_person table.
