truncate table wo.work_order, wo.wo_job, wo.job, wo.job_type cascade;

ALTER SEQUENCE wo.job_type_seq RESTART WITH 1;

insert into wo.job_type (id,creation_date,active_status,client_type,code,name)
values
    (1,NOW(),'Y','orders','type1','job type name 1'),
    (2,NOW(),'Y','corporate','type2','job type name 2'),
    (3,NOW(),'N','corporate','type3','job type name 3');

ALTER SEQUENCE wo.job_type_seq RESTART WITH 10;