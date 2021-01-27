To verify the migration:
Create and populate a legacy DB using creat_old.sql and populate_old.sql
then
>chmod +x migrate_db.sh
>./migrate_sb.sh -u <mysqlusername> -p <musqlpassword> -s <legacy_db_scheme>

Should output
migrated_database_pop.sql

running this file in MySQL will populate the new database with the migrated data.
unmigrateable users (users that have missing essential info) are stored in unmigrated_person table.