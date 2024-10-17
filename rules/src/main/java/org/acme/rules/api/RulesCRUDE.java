package org.acme.rules.api;

import org.acme.rules.drools.dto.RuleDTO;
import org.acme.rules.drools.dto.UpdateRuleDTO;
import org.acme.rules.drools.service.RuleCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RulesCRUDE {

    @Autowired
    private RuleCRUD ruleCRUD;

    @PostMapping("/rule")
    public void createRule(@RequestBody RuleDTO rule) {
        ruleCRUD.create(rule);
    }

    @PatchMapping("/rule")
    public void updateRule(@RequestBody UpdateRuleDTO rule) {
        ruleCRUD.update(rule);
    }

    @DeleteMapping("/rule/{name}")
    public void deleteRule(@PathVariable String name) {
        ruleCRUD.delete(name);
    }

    @PatchMapping("/rule/{name}")
    public void recover(@PathVariable String name) {
        ruleCRUD.recover(name);
    }

    @GetMapping("/all-rules")
    public List<RuleDTO> getAllRules() {
        return ruleCRUD.findAll();
    }

    @GetMapping("/rule-by-name")
    public RuleDTO getRule(@RequestParam("name") String name) {
        return ruleCRUD.findByName(name);
    }

    @GetMapping("/rules-by-group")
    List<RuleDTO> findByGrouping(@RequestParam("group") String group) {
        return ruleCRUD.findByGrouping(group);
    }

}

