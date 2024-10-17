package org.acme.rules.drools.service;

import org.acme.rules.drools.internal.adapter.ActionAdapter;
import org.acme.rules.drools.internal.adapter.RuleTypeAdapter;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;


public interface DroolsActionsService {
    WorkOrderDTO recoverImpactRules(RuntimeException e, RuleTypeAdapter facts);
    WorkOrderDTO impactRules(RuleTypeAdapter facts);
    void addJob(ActionAdapter accion, WorkOrderDTO wo);
    void removeJobs(ActionAdapter accion, WorkOrderDTO wo);
    void removeAllJobs(ActionAdapter accion, WorkOrderDTO wo);
    void removeAllJobsExceptOne(ActionAdapter accion, WorkOrderDTO wo);
    void replaceJob(ActionAdapter accion, WorkOrderDTO wo);
    WorkOrderDTO recover(RuntimeException e, RuleTypeAdapter facts);
}
