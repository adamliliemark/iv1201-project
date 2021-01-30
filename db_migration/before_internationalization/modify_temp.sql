use recruitmentdb_old_temp;

create table users (
first_name VARCHAR(255),
last_name VARCHAR(255),
ssn VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255)
);

CREATE TABLE availability_ (
user_email varchar(255),
from_date DATE,
to_date DATE
);

CREATE TABLE competence_profile_ (
user_email varchar(255),
competence_id BIGINT REFERENCES competence,
years_of_experience NUMERIC(4,2)
);

CREATE TABLE competence_ (
name VARCHAR(255)
);


CREATE TABLE unmigrated_person (
person_id BIGINT PRIMARY KEY,
name VARCHAR(255),
surname VARCHAR(255),
ssn VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255),
role_id BIGINT,
username VARCHAR(255)
);

create table authorities (
    authority varchar(255) not null,
    user_email varchar(255)
);


insert into users
select name as first_name, surname as last_name, ssn, email, password from person where email is not null;

insert into unmigrated_person
select * from person where email is null;

insert into authorities (user_email, authority)
select email, "ROLE_USER" from person where role_id = 1 and email is not null;

insert into authorities (user_email, authority) 
select email as user_email, "ROLE_ADMIN" from person where role_id = 2 and email is not null;

insert into availability_ (user_email, from_date, to_date)
select email, from_date, to_date from person, availability
where availability.person_id = person.person_id
and email is not null;


insert into competence_profile_ (user_email, competence_id, years_of_experience)
select email, competence_id, years_of_experience from person, competence_profile
where competence_profile.person_id = person.person_id
and email is not null;

insert into competence_
select name from competence;

drop table person;
drop table availability;
drop table competence_profile;
drop table role;
drop table competence;
rename table competence_ to competence;
rename table competence_profile_ to competence_profile;
rename table availability_ to availability;