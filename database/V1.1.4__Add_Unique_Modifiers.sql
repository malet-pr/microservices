ALTER TABLE wo.job ADD CONSTRAINT unique_code_job UNIQUE (code);
ALTER TABLE wo.job_type ADD CONSTRAINT unique_code_job_type UNIQUE (code);
ALTER TABLE wo.work_order ADD CONSTRAINT unique_wo_number UNIQUE (wo_number);