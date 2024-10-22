ALTER TABLE wo.work_order DROP COLUMN applied_rule;
ALTER TABLE wo.work_order ADD had_rules boolean DEFAULT false;
