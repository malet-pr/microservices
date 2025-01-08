package org.acme.rules.api;

import org.acme.rules.drools.AsyncService;
import org.acme.rules.grpc.woserviceconnect.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/run")
public class RunRules {


    @Autowired
    private AsyncService asyncService;

    @PostMapping("/one")
    public Boolean runRule(@RequestBody Order order, @RequestParam("group") String group) {
        return asyncService.runRule(order,group);
    }

    @PostMapping()
    public Boolean runRule(@RequestBody Order order) {
        return asyncService.runRule(order);
    }

}

