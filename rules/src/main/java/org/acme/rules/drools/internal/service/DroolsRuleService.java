package org.acme.rules.drools.internal.service;

import org.acme.rules.drools.internal.util.RuleAdapter;
import org.acme.rules.drools.internal.util.RuleTypeAdapter;
import org.acme.rules.drools.internal.model.Rule;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.grpc.woserviceconnect.Order;
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
    HashMap<RuleType, RuleAdapter> getAllRules();
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
    boolean addWoToRun(Order order, String grouping);
    boolean runRules(String grouping, List<Order> orderList);
    boolean recover(RuntimeException e, String grouping, List<Order> orderList);
    boolean recover(RuntimeException e, Order order);
    RuleAdapter getRuleAdapter(RuleType type, String threadName);
}
