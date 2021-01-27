#!/bin/sh
helpFunction()
{
   echo ""
   echo "Usage: $0 -a USERNAME -b PASSWORD -c SCHEMA"
   echo  "\t-u Username for the database"
   echo  "\t-p Password for the database"
   echo  "\t-s Schema name for legact db"
   exit 1
}

while getopts "u:p:s:" opt
do
   case "$opt" in
      u ) user="$OPTARG" ;;
      p ) pass="$OPTARG" ;;
      s ) schema="$OPTARG" ;;
      ? ) helpFunction ;;
   esac
done

if [ -z "$user" ] || [ -z "$pass" ] || [ -z "$schema" ]
then
   echo "Some or all of the parameters are empty";
   helpFunction
fi

echo "drop schema if exists recruitmentdb_old_temp; create schema recruitmentdb_old_temp;" | mysql -u$user -p$pass
mysqldump -u$user -p$pass $schema | mysql -u$user -p$pass recruitmentdb_old_temp
cat modify_temp.sql | mysql -u$user -p$pass
mysqldump -u$user -p$pass --no-create-info --complete-insert --skip-triggers recruitmentdb_old_temp > migrated_database_pop.sql

