   create table authorities (
       id bigint not null auto_increment,
        authority varchar(255) not null,
        user_email varchar(255),
        primary key (id)
    );
    create table availability (
       id bigint not null auto_increment,
        from_date DATE not null,
        to_date DATE not null,
        user_email varchar(255),
        primary key (id)
    );
    create table competence (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    );
    create table competence_profile (
       id bigint not null auto_increment,
        years_of_experience double precision,
        competence_id bigint,
        user_email varchar(255) not null,
        primary key (id)
    );
    create table users (
       email varchar(255) not null,
        enabled bit not null,
        first_name varchar(255) not null,
        last_name varchar(255) not null,
        password varchar(255) not null,
        ssn bigint not null,
        primary key (email)
    );
    alter table authorities 
       add constraint FKkwejet1qepg6ldux6ns5ti8lg 
       foreign key (user_email) 
       references users (email);
    alter table availability 
       add constraint FKme040xvsxikuykxdhliy12k4h 
       foreign key (user_email) 
       references users (email);     
    alter table competence_profile 
       add constraint FKs2bcpk8humnrh416e4o7gyqg1 
       foreign key (competence_id) 
       references competence (id);
    alter table competence_profile 
       add constraint FK5u1u01dq9jcpywuvtyythvjbt 
       foreign key (user_email) 
       references users (email);