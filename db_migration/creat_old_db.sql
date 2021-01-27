drop schema if exists recruitmentdb_old;
create schema recruitmentdb_old;
use recruitmentdb_old;
CREATE TABLE role (
role_id BIGINT PRIMARY KEY,
name VARCHAR(255)
);

CREATE TABLE person (
person_id BIGINT PRIMARY KEY,
name VARCHAR(255),
surname VARCHAR(255),
ssn VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255),
role_id BIGINT REFERENCES role,
username VARCHAR(255)
);

CREATE TABLE availability (
availability_id BIGINT PRIMARY KEY,
person_id BIGINT REFERENCES person,
from_date DATE,
to_date DATE
);

CREATE TABLE competence (
competence_id BIGINT PRIMARY KEY,
name VARCHAR(255)
);

CREATE TABLE competence_profile (
competence_profile_id BIGINT PRIMARY KEY,
person_id BIGINT REFERENCES person,
competence_id BIGINT REFERENCES competence,
years_of_experience NUMERIC(4,2)
);



INSERT INTO role (role_id, name) VALUES (1, 'recruit');
INSERT INTO role (role_id, name) VALUES (2, 'applicant');
INSERT INTO person (person_id, name, surname, username, password, role_id)
VALUES (1, 'Greta', 'Borg', 'borg', 'wl9nk23a', 1);
INSERT INTO person (person_id, name, surname, ssn, email, role_id)
VALUES (2, 'Per', 'Strand', '19671212-1211', 'per@strand.kth.se', 2);
INSERT INTO availability (availability_id, person_id, from_date, to_date)
VALUES (1, 2, '2014-02-23', '2014-05-25');
INSERT INTO availability (availability_id, person_id, from_date, to_date)
VALUES (2, 2, '2014-07-10', '2014-08-10');
INSERT INTO competence (competence_id, name)
VALUES (1, 'Korvgrillning');
INSERT INTO competence (competence_id, name)
VALUES (2, 'Karuselldrift');
INSERT INTO competence_profile (competence_profile_id, person_id,
competence_id, years_of_experience)
VALUES (1, 2, 1, 3.5);
INSERT INTO competence_profile (competence_profile_id, person_id,
competence_id, years_of_experience)
VALUES (2, 2, 2, 2.0);

create table users (
first_name VARCHAR(255),
last_name VARCHAR(255),
ssn VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255)
);

CREATE TABLE availability_ (
availability_id BIGINT PRIMARY KEY,
user_email varchar(255),
from_date DATE,
to_date DATE
primary key(availability_id)
);

CREATE TABLE competence_profile_ (
competence_profile_id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_email varchar(255),
competence_id BIGINT REFERENCES competence,
years_of_experience NUMERIC(4,2)
);

CREATE TABLE unmigrated_person (
person_id BIGINT PRIMARY KEY,
name VARCHAR(255),
surname VARCHAR(255),
ssn VARCHAR(255),
email VARCHAR(255),
password VARCHAR(255),
role_id BIGINT REFERENCES role,
username VARCHAR(255)
);

create table authorities (
    id bigint not null auto_increment,
    authority varchar(255) not null,
    user_email varchar(255),
    primary key (id)
);


insert into users
select name as first_name, surname as last_name, ssn, email, password from person where email is not null;

insert into unmigrated_person
select name as first_name, surname as last_name, ssn, email, password from person where email is null;

insert into authorities (user_email, authority)
select email, "ROLE_USER" from person where role_id = 2 and email is not null;

insert into authorities 
select email as user_email, "ROLE_ADMIN" from person where role_id = 2 and email is not null;

insert into availability_ (user_email, from_date, to_date)
select email, from_date, to_date from person, availability
where availability.person_id = person.person_id
and email is not null;


insert into competence_profile_ (user_email, competence_id, years_of_experience)
select email, competence_id, years_of_experience from person, competence_profile
where competence_profile.person_id = person.person_id
and email is not null;


drop table person;
drop table availability;
drop table competence_profile;
drop table role;
rename table competence_profile_ to competence_profile;
rename table availability_ to availability;