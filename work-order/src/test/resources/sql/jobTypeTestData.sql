truncate table work_order, wo_job,job, job_type cascade;

ALTER SEQUENCE job_type_seq RESTART WITH 1;

insert into job_type (id,creation_date,active_status,client_type,code,name)
values
    (1,NOW(),'Y','consumer','type1','job type name 1'),
    (2,NOW(),'Y','corporate','type2','job type name 2'),
    (3,NOW(),'N','corporate','type3','job type name 3');

ALTER SEQUENCE job_type_seq RESTART WITH 10;