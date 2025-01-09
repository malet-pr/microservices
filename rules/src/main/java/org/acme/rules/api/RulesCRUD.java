package org.acme.rules.api;


import org.acme.rules.drools.RuleCRUD;
import org.acme.rules.drools.internal.dto.RuleDTO;
import org.acme.rules.drools.internal.dto.UpdateRuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rules")
public class RulesCRUD {
   /*
    @Autowired
    private RuleCRUD ruleCRUD;



    @GetMapping("/rule")
    public void getRuleByName(@RequestParam String name) {
        ruleCRUD.delete(name);
    }

    @GetMapping()
    public List<Rule> getAllRules() {
        return ruleCRUD.findAll();
    }

    @PostMapping()
    public void createRule(@RequestBody RuleDTO rule) {
        ruleCRUD.create(rule);
    }

    @PatchMapping()
    public void updateRule(@RequestBody UpdateRuleDTO rule) {
        ruleCRUD.update(rule);
    }

    @DeleteMapping("/rule")
    public void deleteRule(@RequestParam String name) {
        ruleCRUD.delete(name);
    }

*/

}

