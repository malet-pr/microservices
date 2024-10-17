package org.acme.rules.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dummy")
@RestController
public class TestEndpoint {

    @GetMapping(value = "/test")
    public String testDrools() {
        return "modulith-rules-example is up and running";
    }


}
