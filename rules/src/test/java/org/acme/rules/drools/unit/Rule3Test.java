package org.acme.rules.drools.unit;

import org.acme.rules.drools.TestData;
import org.acme.rules.drools.internal.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class Rule3Test {

    static final Logger log = LoggerFactory.getLogger(Rule1Test.class);
    @Mock
    RuleDAO ruleDaoMock;
    @Mock
    RuleTypeDAO ruleTypeDaoMock;
    @InjectMocks
    RulesServiceImpl service;

    @Test
    @DisplayName("Tests the rule for disabling a list of job - applies")
    void disableJobsTest_yes() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        WoJob wj2 = WoJob.builder().jobCode("job2").activeStatus("Y").build();
        WoData order1 = WoData.builder().woNumber("order1").jobTypeCode("code1c").woJobs(List.of(wj1, wj2)).build();
        Rule rule = new Rule();
        rule.setName("disable-list-of-job-for-jobtype");
        rule.setDrl(TestData.rule3);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order1,"A");
        // Assert
        String job1Active = order1.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job1"))
                .findFirst().get().getActiveStatus();
        String job2Active = order1.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job2"))
                .findFirst().get().getActiveStatus();
        assertTrue(order1.isHasRules());
        assertEquals("N", job1Active);
        assertEquals("N", job2Active);
        assertEquals("disable list of jobs for jobtype", order1.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job1")).findFirst()
                .get().getAppliedRule());
        assertEquals("disable list of jobs for jobtype", order1.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job2")).findFirst()
                .get().getAppliedRule());
    }

    @Test
    @DisplayName("Tests the rule for disabling list of jobs - doesn't apply because of list of jobs")
    public void disableJobsTest_not() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        WoJob wj2 = WoJob.builder().jobCode("job4").activeStatus("Y").build();
        WoData order2 = WoData.builder().woNumber("order2").jobTypeCode("code1c").woJobs(List.of(wj1, wj2)).build();
        Rule rule = new Rule();
        rule.setName("disable-list-of-jobs-for-jobtype");
        rule.setDrl(TestData.rule1);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order2,"A");
        // Assert
        String job1Active = order2.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job1"))
                .findFirst().get().getActiveStatus();
        String job4Active = order2.getWoJobs().stream()
                .filter(wj -> wj.getJobCode().equals("job4"))
                .findFirst().get().getActiveStatus();
        assertTrue(order2.isHasRules());
        assertEquals("Y", job1Active);
        assertEquals("Y", job4Active);
    }

    @Test
    @DisplayName("Tests the rule for disabling list of jobs - doesn't apply because of jobTypeCode")
    public void disableJobsTest2_not() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        WoJob wj2 = WoJob.builder().jobCode("job2").activeStatus("Y").build();
        WoData order2 = WoData.builder().woNumber("order2").jobTypeCode("code3").woJobs(List.of(wj1, wj2)).build();
        Rule rule = new Rule();
        rule.setName("disable-list-of-jobs-for-jobtype");
        rule.setDrl(TestData.rule1);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order2,"A");
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
