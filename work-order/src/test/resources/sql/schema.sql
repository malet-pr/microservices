CREATE SEQUENCE IF NOT EXISTS job_seq;

CREATE TABLE IF NOT EXISTS job (
      id int8 DEFAULT nextval('job_seq'),
      creation_date timestamp(6) NULL,
      code varchar(255) NOT NULL UNIQUE ,
      "name" varchar(255) NULL,
      active_status bpchar(1) NULL,
      CONSTRAINT job_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS job_type_seq;

CREATE TABLE IF NOT EXISTS job_type (
       id int8 DEFAULT nextval('job_type_seq'),
       creation_date timestamp(6) NULL,
       code varchar(255) NOT NULL UNIQUE ,
       "name" varchar(255) NULL,
       client_type varchar(255) NULL,
       active_status bpchar(1) NULL,
       CONSTRAINT job_type_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS work_order_seq;

CREATE TABLE IF NOT EXISTS  work_order (
      id int8 DEFAULT nextval('work_order_seq'),
      creation_date timestamp(6) NULL,
      wo_number varchar(255) NOT NULL UNIQUE ,
      jobtype_id int8 NULL,
      client_id varchar(255) NULL,
      address varchar(255) NULL,
      city varchar(255) NULL,
      state varchar(255) NULL,
      wo_creation_date timestamp(6) NULL,
      wo_completion_date timestamp(6) NULL,
      applied_rule varchar(255) NULL,
      CONSTRAINT work_order_pkey PRIMARY KEY (id),
      CONSTRAINT fk9f2v1fgvk791qgg7gy6owq5by FOREIGN KEY (jobtype_id) REFERENCES job_type(id)
);

CREATE SEQUENCE IF NOT EXISTS work_order_job_seq;

CREATE TABLE IF NOT EXISTS wo_job (
     id int8 DEFAULT nextval('work_order_job_seq'),
     creation_date timestamp(6) NULL,
     work_order_id int8 NULL,
     job_id int8 NULL,
     quantity int4 NULL,
     active_status bpchar(1) NULL,
     applied_rule varchar(255) NULL,
     CONSTRAINT wo_job_pkey PRIMARY KEY (id),
     CONSTRAINT fk81bc8pni97yqwurkfgdyfqlw0 FOREIGN KEY (work_order_id) REFERENCES work_order(id)
);

alter sequence job_seq restart;
alter sequence job_type_seq restart;
alter sequence work_order_seq restart;
alter sequence work_order_job_seq restart;