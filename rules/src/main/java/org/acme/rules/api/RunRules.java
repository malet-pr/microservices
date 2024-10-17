package org.acme.rules.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/run")
public class RunRules {

    /*
    @Autowired
    private AsyncService asyncService;


    @PostMapping("/many")
    public HashMap<String,List<String>> runRules(@RequestBody List<WorkOrderDTO> woList, @RequestParam("group") String group) {
        return asyncService.runRules(woList,group);
    }

    @PostMapping("/one")
    public Boolean runRule(@RequestBody WorkOrderDTO wo, @RequestParam("group") String group) {
        return asyncService.runRule(wo,group);
    }
    */

}
