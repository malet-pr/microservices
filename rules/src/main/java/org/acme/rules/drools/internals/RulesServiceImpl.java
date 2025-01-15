package org.acme.rules.drools.internals;

import org.acme.rules.DroolsConfig;
import org.acme.rules.drools.RulesService;
import org.acme.rules.drools.WoData;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RulesServiceImpl implements RulesService {

    Logger log = LoggerFactory.getLogger(RulesServiceImpl.class);

    @Override
    public WoData runRuleTest(WoData woData) {
        KieSession kieSession = new DroolsConfig().getKieSession();
        try{
            kieSession.insert(woData);
            kieSession.fireAllRules();
            woData.setHasRules(true);
            log.info("Rule executed");
        } catch(Exception e) {
            log.error(e.getMessage());
        } finally {
            kieSession.dispose();
        }
        return woData;
    }

    public boolean containsJobCode(WoData woData, String jobCode){
        return woData.getWoJobs().stream().anyMatch(j -> j.getJobCode().equals(jobCode));
    }
    public static void disableJob(WoData woData, String jobCode, String ruleName){
        woData.getWoJobs().forEach(wj -> {
            if (wj.getJobCode().equals(jobCode)) {
                wj.setActiveStatus("N");
                wj.setAppliedRule(ruleName);
            }
        });
    }

}


// CONDITIONS
// contains a list of jobCodes
// does not contain single jobCode
// does not contain a list of jobCodes
// contains repeated jobCodes
// check quantity

// ACTIONS
// add Job
// remove list of jobs
// add list of jobs
// deal with repeated jobCodes --> keep one with added quantities
// change quantity
