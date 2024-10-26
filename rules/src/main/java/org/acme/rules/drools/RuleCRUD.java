package org.acme.rules.drools;

import java.util.List;

public interface RuleCRUD {
    //void create(RuleDTO rule);
    //void update(UpdateRuleDTO updRule);
    void delete(String name);
    void recover(String name);
    //List<RuleDTO> findAll();
    //RuleDTO findByName(String name);
    //List<RuleDTO> findByGrouping(String group);
}
