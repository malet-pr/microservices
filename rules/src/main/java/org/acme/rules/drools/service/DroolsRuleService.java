package org.acme.rules.drools.service;

import org.acme.rules.drools.internal.adapter.RuleAdapter;
import org.acme.rules.drools.internal.adapter.RuleTypeAdapter;
import org.acme.rules.drools.internal.model.Rule;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;
import org.kie.api.builder.KieBuilder;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DroolsRuleService {
    Rule getRule(String ruleName);
    HashMap<RuleType, RuleAdapter> getRulesByType(String grouping);
    String getFullRule(String header, List<Rule> rules);
    String readAllText(Resource resource);
    String getHeader(RuleType type);
    KieContainer getKieContainer(String key, String val);
    KieBuilder getKieBuilder(String key, String val);
    KieContainer getKieContainer(String key, String val, String threadName);
    KieBuilder getKieBuilder(String key, String val, String threadName);
    Map<String, String> getRuleMetaData(String metaData);
    List<RuleType> getRuleTypes(String grouping);
    RuleAdapter getRuleAdapter(RuleType type);
    void fireAllRules(RuleAdapter adapter, RuleTypeAdapter facts);
    void fireAllRules(RuleAdapter adapter, KieSession kieSession);
    boolean addWoToRun(WorkOrderDTO wo, String grouping);
    boolean runRules(String grouping, List<WorkOrderDTO> woList);
    boolean recover(RuntimeException e, String grouping, List<WorkOrderDTO> woList);
    boolean recover(RuntimeException e, WorkOrderDTO woList);
    void getWoJobs(WorkOrderDTO wo);
    RuleAdapter getRuleAdapter(RuleType type, String threadName);
}
