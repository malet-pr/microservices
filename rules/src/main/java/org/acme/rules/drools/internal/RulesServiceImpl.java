package org.acme.rules.drools.internal;

import org.acme.rules.drools.RulesService;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RulesServiceImpl implements RulesService {

    private static final Logger log = LoggerFactory.getLogger(RulesServiceImpl.class);
    KieServices kieServices = KieServices.Factory.get();
    KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    KieRepository kieRepository = kieServices.getRepository();

    @Autowired
    RuleDAO ruleDAO;

    public KieSession getKieSession() {
        List<Rule> activeRules = ruleDAO.findByActiveStatus('Y');
        for (Rule rule : activeRules) {
            String resourcePath = "src/main/resources/rules/" + rule.getName() + ".drl";
            kieFileSystem.write(resourcePath, rule.getDrl());
        }
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            log.error("Error building KieSession: {}", kieBuilder.getResults());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        return kieContainer.newKieSession();
    }

    private void getKieRepository() {
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
    }

    @Override
    public WoData runRuleTest(WoData woData) {
        KieSession kieSession = this.getKieSession();
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
