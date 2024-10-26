package org.acme.rules.drools.internal.service;

import org.acme.rules.drools.RuleCRUD;
import org.acme.rules.drools.internal.model.Rule;
import org.acme.rules.drools.internal.repository.RuleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleCRUDImpl implements RuleCRUD {
    @Override
    public void delete(String name) {

    }

    @Override
    public void recover(String name) {

    }

    /*
    @Autowired
    private RuleDAO ruleDAO;
    private final RuleMapper ruleMapper = new RuleMapper();

    @Override
    public void create(RuleDTO rule) {
        Rule entity = ruleMapper.convertToEntity(rule);
        ruleDAO.save(entity);
    }

    @Override
    public void update(UpdateRuleDTO updRule) {
        ruleDAO.findByNameIgnoreCaseAndActive(updRule.getName())
                .ifPresent(e -> {
                    e.setDrl(updRule.getDrl());
                    ruleDAO.save(e);
                });
    }

    @Override
    public void delete(String name) {
        ruleDAO.findByNameIgnoreCaseAndActive(name)
                .ifPresent(e -> {
                    e.setActiveStatus('N');
                    ruleDAO.save(e);
                });
    }

    @Override
    public void recover(String name){
        ruleDAO.findByNameIgnoreCaseAndActive(name)
                .ifPresent(e -> {
                    e.setActiveStatus('Y');
                    ruleDAO.save(e);
                });
    }

    @Override
    public List<RuleDTO> findAll() {
        List<Rule> rules = ruleDAO.findAll();
        List<RuleDTO> ruleDTOs = new ArrayList<>();
        rules.forEach(rule -> ruleDTOs.add(ruleMapper.convertToDto(rule)));
        return ruleDTOs;
    }

    @Override
    public RuleDTO findByName(String name) {
        Optional<Rule> entity = ruleDAO.findByNameIgnoreCaseAndActive(name);
        if(entity.isPresent()) return ruleMapper.convertToDto(entity.get());
        return new RuleDTO();
    }

    @Override
    public List<RuleDTO> findByGrouping(String group) {
        List<Rule> entities = ruleDAO.findByGrouping(group);
        List<RuleDTO> ruleDTOs = new ArrayList<>();
        entities.forEach(entity -> ruleDTOs.add(ruleMapper.convertToDto(entity)));
        return ruleDTOs;
    }
     */

}

