package org.acme.rules.drools;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleTest {

    static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    @Test
    @DisplayName("Tests the rule for disabling a job - applies")
    public void disableJobTest_yes() {
        log.info("Creating RuleUnit");
        WoDataUnit woDataUnit = new WoDataUnit();
        RuleUnitInstance<WoDataUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(woDataUnit);
        log.info("Preparing data");
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        WoJob wj2 = WoJob.builder().jobCode("job2").activeStatus("Y").build();
        WoData order1 = WoData.builder().woNumber("order1").jobTypeCode("code1").woJobs(List.of(wj1, wj2)).build();
        try {
            log.info("Insert data");
            woDataUnit.getOrders().add(order1);
            log.info("Fire rules");
            instance.fire();
            log.info("Check results");
            String job1Active = order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job1"))
                    .findFirst().get().getActiveStatus();
            String job2Active = order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2"))
                    .findFirst().get().getActiveStatus();
            assertEquals("N", job1Active);
            assertEquals("Y", job2Active);
        } finally {
            instance.close();
        }
    }

    @Test
    @DisplayName("Tests the rule for disabling jobs - doesn't apply")
    public void disableJobsTest_not() {
        log.info("Creating RuleUnit");
        WoDataUnit woDataUnit = new WoDataUnit();
        RuleUnitInstance<WoDataUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(woDataUnit);
        log.info("Preparing data");
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        WoJob wj2 = WoJob.builder().jobCode("job2").activeStatus("Y").build();
        WoData order2 = WoData.builder().woNumber("order2").jobTypeCode("code2").woJobs(List.of(wj1, wj2)).build();
        try {
            log.info("Insert data");
            woDataUnit.getOrders().add(order2);
            log.info("Fire rules");
            instance.fire();
            log.info("Check results");
            String job1Active = order2.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job1"))
                    .findFirst().get().getActiveStatus();
            String job2Active = order2.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2"))
                    .findFirst().get().getActiveStatus();
            assertEquals("Y", job1Active);
            assertEquals("Y", job2Active);
        } finally {
            instance.close();
        }
    }

}


