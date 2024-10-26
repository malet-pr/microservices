SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

create sequence if not exists ru.rule_seq;

create table if not exists ru.rule(
    id int8 DEFAULT nextval('ru.rule_seq'),
    creation_date timestamp(6) null,
    "name" varchar(255) not null,
    "description" varchar(255) null,
    grouping varchar(255) not null,
    drl text not null,
    active_status bpchar(1) NULL,
    CONSTRAINT rule_pkey PRIMARY KEY (id),
	CONSTRAINT unique_name_rule UNIQUE ("name")
);

create sequence if not exists ru.rule_type_seq;

create table if not exists ru.rule_type(
    id int8 DEFAULT nextval('ru.rule_type_seq'),
    creation_date timestamp(6) null,
    "name" varchar(255) null,
    code varchar(255) not null,
    header varchar(255) null,
    grouping varchar(255) not null,
    priority int8 null,
    header_default varchar(255) null,
    CONSTRAINT rule_type_pkey PRIMARY KEY (id),
	CONSTRAINT unique_code_rule_type UNIQUE (code)
);



ALTER SEQUENCE ru.rule_seq RESTART WITH 1;
ALTER SEQUENCE ru.rule_type_seq RESTART WITH 1;