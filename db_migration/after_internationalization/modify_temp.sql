create table users (
first_name VARCHAR(255),
last_name VARCHAR(255),
ssn VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255)
);

CREATE TABLE availability_ (
user_email varchar(255) references users,
from_date DATE,
to_date DATE
);

CREATE TABLE competence_profile_ (
user_email varchar(255) references users,
competence_id BIGINT,
years_of_experience NUMERIC(4,2)
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
    user_email varchar(255) references users
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


CREATE TABLE unmigrated_availability (
availability_id BIGINT PRIMARY KEY,
person_id BIGINT,
from_date DATE,
to_date DATE
);

CREATE TABLE unmigrated_competence_profile (
competence_profile_id BIGINT PRIMARY KEY,
person_id BIGINT,
competence_id BIGINT,
years_of_experience NUMERIC(4,2)
);

create table language(
    language_code varchar(255) primary key,
    native_name  varchar(255)
);

insert into language(language_code, native_name) values("sv_SE", "svenska"), ("en_US", "english");

create table competence_translation(
    competence_id BIGINT references competence,
    language_language_code varchar(255) references language,
    text varchar(255)
);


insert into competence_translation(competence_id, language_language_code, text)
select competence_id, "sv_SE", name from competence;




insert into unmigrated_availability(availability_id, person_id, from_date, to_date)
select availability_id, availability.person_id, from_date, to_date from availability, unmigrated_person
where unmigrated_person.person_id = availability.person_id;

insert into unmigrated_competence_profile(competence_profile_id, person_id, competence_id, years_of_experience)
select competence_profile_id, competence_profile.person_id, competence_id, years_of_experience from competence_profile, unmigrated_person
where unmigrated_person.person_id = competence_profile.person_id;


insert into competence_profile_ (user_email, competence_id, years_of_experience)
select email, competence_id, years_of_experience from person, competence_profile
where competence_profile.person_id = person.person_id
and email is not null;

drop table person;
drop table availability;
drop table competence_profile;
drop table role;
alter table competence drop column name;
rename table competence_profile_ TO competence_profile;
rename table availability_ TO availability;
alter table competence change competence_id id BIGINT;
drop table competence;