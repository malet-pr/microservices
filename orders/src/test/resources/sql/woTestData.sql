truncate table wo.wo_order, wo.wo_job, wo.job, wo.job_type cascade;

ALTER SEQUENCE wo.order_job_seq RESTART WITH 1;
ALTER SEQUENCE wo.job_seq RESTART WITH 1;
ALTER SEQUENCE wo.job_type_seq RESTART WITH 1;
ALTER SEQUENCE wo.order_seq RESTART WITH 1;

insert into wo.job (id,creation_date,active_status,code,name)
values
    (1,NOW(),'Y','JobCode1','job name 1'),
    (2,NOW(),'Y','JobCode2','job name 2'),
    (3,NOW(),'N','JobCode3','job name 3'),
    (4,NOW(),'Y','JobCode4','job name 4');

insert into wo.job_type (id,creation_date,active_status,client_type,code,name)
values
    (1,NOW(),'Y','orders','type1','job type name 1'),
    (2,NOW(),'Y','corporate','type2','job type name 2'),
    (3,NOW(),'N','corporate','type3','job type name 3');

insert into wo.wo_order
(id,creation_date,jobtype_id,wo_completion_date,wo_creation_date,address,city,state,client_id,wo_number)
values
    (1,NOW(),1,CURRENT_DATE-1,CURRENT_DATE-3,'addres1','city1','state1','client1','ABC123'),
    (2,NOW(),2,CURRENT_DATE-2,CURRENT_DATE-4,'addres2','city2','state2','client2','ABC456'),
    (3,NOW(),2,CURRENT_DATE-1,CURRENT_DATE-4,'addres3','city3','state3','client3','ZZZ999');

insert into wo.wo_job (id,creation_date,active_status,quantity,job_id,order_id,applied_rule)
values
    (1,NOW(),'Y',5,1,1,''),
    (2,NOW(),'N',1,4,1,'A1'),
    (3,NOW(),'Y',2,2,2,''),
    (4,NOW(),'Y',3,1,2,'A2');

ALTER SEQUENCE wo.order_job_seq RESTART WITH 10;
ALTER SEQUENCE wo.job_seq RESTART WITH 10;
ALTER SEQUENCE wo.job_type_seq RESTART WITH 10;
ALTER SEQUENCE wo.order_seq RESTART WITH 10;

