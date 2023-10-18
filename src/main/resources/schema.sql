alter table job drop foreign key FK5q04favsasq8y70bsei7wv8fc;

drop table if exists apply cascade;
drop table if exists company cascade;
drop table if exists job cascade;
drop table if exists users cascade;

create table apply (
    id bigint not null auto_increment,
    job_id bigint not null,
    user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table company (
    id bigint not null auto_increment,
    country varchar(255),
    name varchar(255),
    region varchar(255),
    primary key (id)
) engine=InnoDB;

create table job (
    compensation integer not null,
    company_id bigint,
    id bigint not null auto_increment,
    description varchar(1000),
    position varchar(255),
    tech_stack varchar(255),
    primary key (id)
) engine=InnoDB;

create table users (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
) engine=InnoDB;

alter table job add constraint FK5q04favsasq8y70bsei7wv8fc
   foreign key (company_id) references company (id);
