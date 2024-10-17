package org.acme.rules.drools.service;

import org.acme.rules.drools.internal.model.RuleType;
import java.util.List;

public interface RuleTypeService {
    void save(RuleType ruleType);
    RuleType findById(Long id);
    RuleType findByCode(String code);
    List<RuleType> findByGroupingOrderByPriorityAsc(String grouping);
}
