package org.acme.rules.drools.internal.service;

import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.drools.internal.repository.RuleTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleTypeServiceImpl implements RuleTypeService {

    @Autowired
    RuleTypeDAO ruleTypeDAO;

    @Override
    public void save(RuleType ruleType) {
        ruleTypeDAO.save(ruleType);
    }

    @Override
    public RuleType findById(Long id) {
        return ruleTypeDAO.findById(id).orElse(null);
    }

    @Override
    public RuleType findByCode(String code) {
        return ruleTypeDAO.findByCode(code);
    }

    @Override
    public List<RuleType> findByGroupingOrderByPriorityAsc(String grouping) {
        return ruleTypeDAO.findByGroupingOrderByPriorityAsc(grouping);
    }

}
