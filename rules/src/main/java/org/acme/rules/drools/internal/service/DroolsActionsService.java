package org.acme.rules.drools.internal.service;

import org.acme.rules.drools.internal.util.ActionAdapter;
import org.acme.rules.drools.internal.util.RuleTypeAdapter;
import org.acme.rules.grpc.woserviceconnect.Order;


public interface DroolsActionsService {
    Order recoverImpactRules(RuntimeException e, RuleTypeAdapter facts);
    Order impactRules(RuleTypeAdapter facts);
    void addJob(ActionAdapter accion, Order wo);
    void removeJobs(ActionAdapter accion, Order wo);
    void removeAllJobs(ActionAdapter accion, Order wo);
    void updateJob(ActionAdapter accion, Order wo);
    Order recover(RuntimeException e, RuleTypeAdapter facts);
}
