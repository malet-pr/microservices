truncate table wo.wo_order, wo.wo_job,wo.job, wo.job_type cascade;

ALTER SEQUENCE wo.job_seq RESTART WITH 1;

insert into wo.job (id,creation_date,active_status,code,name)
values
    (1,NOW(),'Y','JobCode1','job name 1'),
    (2,NOW(),'Y','JobCode2','job name 2'),
    (3,NOW(),'N','JobCode3','job name 3'),
    (4,NOW(),'Y','JobCode4','job name 4');

ALTER SEQUENCE wo.job_seq RESTART WITH 10;
