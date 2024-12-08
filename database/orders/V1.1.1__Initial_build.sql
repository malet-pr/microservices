SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;


CREATE SEQUENCE IF NOT EXISTS wo.job_seq;

CREATE TABLE IF NOT EXISTS wo.job (
                                      id int8 DEFAULT nextval('wo.job_seq'),
                                      creation_date timestamp(6) NULL,
                                      code varchar(255) NOT NULL,
                                      "name" varchar(255) NULL,
                                      active_status bpchar(1) NULL,
                                      CONSTRAINT job_pkey PRIMARY KEY (id),
                                      CONSTRAINT unique_code_job UNIQUE (code)
);

CREATE SEQUENCE IF NOT EXISTS wo.job_type_seq;

CREATE TABLE IF NOT EXISTS wo.job_type (
                                           id int8 DEFAULT nextval('wo.job_type_seq'),
                                           creation_date timestamp(6) NULL,
                                           code varchar(255) NOT NULL,
                                           "name" varchar(255) NULL,
                                           client_type varchar(255) NULL,
                                           active_status bpchar(1) NULL,
                                           CONSTRAINT job_type_pkey PRIMARY KEY (id),
                                           CONSTRAINT unique_code_job_type UNIQUE (code)
);

CREATE SEQUENCE IF NOT EXISTS wo.order_seq;

CREATE TABLE IF NOT EXISTS  wo.order (
                                              id int8 DEFAULT nextval('wo.order_seq'),
                                              creation_date timestamp(6) NULL,
                                              wo_number varchar(255) NOT NULL,
                                              jobtype_id int8 NULL,
                                              client_id varchar(255) NULL,
                                              address varchar(255) NULL,
                                              city varchar(255) NULL,
                                              state varchar(255) NULL,
                                              wo_creation_date timestamp(6) NULL,
                                              wo_completion_date timestamp(6) NULL,
                                              has_rules boolean DEFAULT false,
                                              CONSTRAINT order_pkey PRIMARY KEY (id),
                                              CONSTRAINT fk9f2v1fgvk791qgg7gy6owq5by FOREIGN KEY (jobtype_id) REFERENCES wo.job_type(id),
                                              CONSTRAINT unique_wo_number UNIQUE (wo_number)
);

CREATE SEQUENCE IF NOT EXISTS wo.order_job_seq;

CREATE TABLE IF NOT EXISTS wo.wo_job (
                                         id int8 DEFAULT nextval('wo.order_job_seq'),
                                         creation_date timestamp(6) NULL,
                                         order_id int8 NULL,
                                         job_id int8 NULL,
                                         quantity int4 NULL,
                                         active_status bpchar(1) NULL,
                                         applied_rule varchar(255) NULL,
                                         CONSTRAINT wo_job_pkey PRIMARY KEY (id),
                                         CONSTRAINT fk81bc8pni97yqwurkfgdyfqlw0 FOREIGN KEY (order_id) REFERENCES wo.order(id)
);

ALTER SEQUENCE wo.order_job_seq RESTART WITH 1;
ALTER SEQUENCE wo.job_seq RESTART WITH 1;
ALTER SEQUENCE wo.job_type_seq RESTART WITH 1;
ALTER SEQUENCE wo.order_seq RESTART WITH 1;