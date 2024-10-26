package org.acme.rules.api;

import org.acme.rules.drools.AsyncService;
import org.acme.rules.drools.WorkOrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/run")
public class RunRules {


    @Autowired
    private AsyncService asyncService;

    @PostMapping("/one")
    public Boolean runRule(@RequestBody WorkOrderData wo, @RequestParam("group") String group) {
        return asyncService.runRule(wo,group);
    }

    @PostMapping()
    public Boolean runRule(@RequestBody WorkOrderData wo) {
        return asyncService.runRule(wo);
    }

}

