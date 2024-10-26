package org.acme.rules.drools.internal.service;

import org.acme.rules.drools.WorkOrderData;
import org.acme.rules.drools.internal.util.ActionAdapter;
import org.acme.rules.drools.internal.util.RuleTypeAdapter;


public interface DroolsActionsService {
    WorkOrderData recoverImpactRules(RuntimeException e, RuleTypeAdapter facts);
    WorkOrderData impactRules(RuleTypeAdapter facts);
    void addJob(ActionAdapter accion, WorkOrderData wo);
    void removeJobs(ActionAdapter accion, WorkOrderData wo);
    void removeAllJobs(ActionAdapter accion, WorkOrderData wo);
    void updateJob(ActionAdapter accion, WorkOrderData wo);
    WorkOrderData recover(RuntimeException e, RuleTypeAdapter facts);
}
