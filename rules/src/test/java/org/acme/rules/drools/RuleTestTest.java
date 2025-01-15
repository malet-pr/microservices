package org.acme.rules.drools;

import org.acme.rules.drools.internals.RulesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RuleTestTest {

    static final Logger log = LoggerFactory.getLogger(RuleTestTest.class);
    private RulesServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new RulesServiceImpl();
    }

    @Test
    @DisplayName("Tests the rule for disabling a job - applies")
    void disableJobTest_yes() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        WoJob wj2 = WoJob.builder().jobCode("job2").activeStatus("Y").build();
        WoData order1 = WoData.builder().woNumber("order1").jobTypeCode("code1").woJobs(List.of(wj1, wj2)).build();
        // Act
        service.runRuleTest(order1);
        // Assert
        String job1Active = order1.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job1"))
                .findFirst().get().getActiveStatus();
        String job2Active = order1.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job2"))
                .findFirst().get().getActiveStatus();
        assertTrue(order1.isHasRules());
        assertEquals("N", job1Active);
        assertEquals("disable job for jobtype", order1.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job1")).findFirst()
                .get().getAppliedRule());
        assertEquals("Y", job2Active);
    }

    @Test
    @DisplayName("Tests the rule for disabling jobs - doesn't apply")
    public void disableJobsTest_not() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        WoJob wj2 = WoJob.builder().jobCode("job2").activeStatus("Y").build();
        WoData order2 = WoData.builder().woNumber("order2").jobTypeCode("code2").woJobs(List.of(wj1, wj2)).build();
        // Act
        service.runRuleTest(order2);
        // Assert
        String job1Active = order2.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job1"))
                .findFirst().get().getActiveStatus();
        String job2Active = order2.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job2"))
                .findFirst().get().getActiveStatus();
        assertTrue(order2.isHasRules());
        assertEquals("Y", job1Active);
        assertEquals("Y", job2Active);
    }

}


