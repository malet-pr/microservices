package org.acme.rules.api;

import org.acme.rules.drools.RulesService;
import org.acme.rules.drools.WoData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/run")
public class RunRules {

    Logger log = LoggerFactory.getLogger(RunRules.class);

    @Autowired
    private RulesService service;

    @PostMapping("/test")
    public WoData runRule(@RequestBody WoData woData) {
        return service.runRuleTest(woData);
    }

}

