package org.acme.rules.drools.internal;

import org.acme.rules.drools.RulesService;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RulesServiceImpl implements RulesService {

    private static final Logger log = LoggerFactory.getLogger(RulesServiceImpl.class);
    KieServices kieServices = KieServices.Factory.get();
    KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    KieRepository kieRepository = kieServices.getRepository();

    @Autowired
    RuleDAO ruleDAO;

    @Autowired
    RuleTypeDAO ruleTypeDAO;

    public KieSession getKieSession(String grouping) {
        List<Rule> activeRules = ruleDAO.findByActiveStatus('Y');
        RuleType ruleType = ruleTypeDAO.findByGrouping(grouping);
        String imports = ruleType.getHeader();
        String rulesPath = "src/main/resources/rules/";
        for (Rule rule : activeRules) {
            StringBuilder sb1 = new StringBuilder(rulesPath).append(rule.getName()).append(".drl");
            StringBuilder sb2 = new StringBuilder(imports).append("\n").append(rule.getDrl());
            kieFileSystem.write(sb1.toString(), sb2.toString());
        }
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)
                || kieBuilder.getResults().hasMessages(Message.Level.WARNING)) {
            log.error("Error building KieSession: {}", kieBuilder.getResults());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        return kieContainer.newKieSession();
    }

    private void getKieRepository() {
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
    }

    @Override
    public WoData runRuleTest(WoData woData, String grouping) {
        KieSession kieSession = this.getKieSession(grouping);
        //KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(kieSession, "rules/logs/"+getLogFileName());
        try{
            kieSession.insert(woData);
            kieSession.fireAllRules();
            //logger.close();
            woData.setHasRules(true);
            log.info("All rules type {} have been fired", grouping);
        } catch(Exception e) {
            log.error(e.getMessage());
        } finally {
            kieSession.dispose();
        }
        return woData;
    }

    private String getLogFileName(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        return "logfile_" + timestamp;
    }

}




