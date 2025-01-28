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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class Rule4Test {

    static final Logger log = LoggerFactory.getLogger(Rule1Test.class);
    @Mock
    RuleDAO ruleDaoMock;
    @Mock
    RuleTypeDAO ruleTypeDaoMock;
    @InjectMocks
    RulesServiceImpl service;


    @Test
    @DisplayName("Tests the rule for adding a list of job - applies")
    void addJobsTest_yes() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job3").activeStatus("Y").build();
        List<WoJob> woJobs = new ArrayList<>();
        woJobs.add(wj1);
        WoData order1 = WoData.builder().woNumber("order1").jobTypeCode("code3").woJobs(woJobs).build();
        Rule rule = new Rule();
        rule.setName("add-list-of-job-for-jobtype");
        rule.setDrl(TestData.rule4);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order1,"A");
        // Assert
        boolean hasJob1 = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job1"));
        boolean hasJob2 = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job2"));
        assertTrue(hasJob1, "The order should have hasJob1 == true");
        assertTrue(hasJob2, "The order should have hasJob2 == true");
        assertTrue(order1.isHasRules(),"The order should have hasRules == true");
        if(hasJob1){
            assertEquals("add list of jobs for jobtype", order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job1")).findFirst()
                    .get().getAppliedRule());
            assertEquals(2, order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job1")).findFirst()
                    .get().getQuantity());
            assertEquals("Y", order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job1")).findFirst()
                    .get().getActiveStatus());
        }
        if(hasJob2){
            assertEquals("add list of jobs for jobtype", order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2")).findFirst()
                    .get().getAppliedRule());
            assertEquals(4, order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2")).findFirst()
                    .get().getQuantity());
            assertEquals("Y", order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2")).findFirst()
                    .get().getActiveStatus());
        }
    }

    @Test
    @DisplayName("Tests the rule for adding list of jobs - doesn't apply because of list")
    public void addJobTest_not() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        List<WoJob> woJobs = new ArrayList<>();
        woJobs.add(wj1);
        WoData order1 = WoData.builder().woNumber("order1").jobTypeCode("code3").woJobs(woJobs).build();
        Rule rule = new Rule();
        rule.setName("add-list-of-job-for-jobtype");
        rule.setDrl(TestData.rule4);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order1,"A");
        // Assert
        boolean hasJob1 = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job1"));
        boolean hasJob2 = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job2"));
        assertTrue(hasJob1, "The order should have hasJob1 == true");
        assertFalse(hasJob2, "The order should have hasJob2 == false");
        assertTrue(order1.isHasRules(),"The order should have hasRules == true");
    }

    @Test
    @DisplayName("Tests the rule for adding list of jobs - doesn't apply because of jobCodeType")
    public void addJobTest2_not() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job3").activeStatus("Y").build();
        List<WoJob> woJobs = new ArrayList<>();
        woJobs.add(wj1);
        WoData order1 = WoData.builder().woNumber("order1").jobTypeCode("code4").woJobs(woJobs).build();
        Rule rule = new Rule();
        rule.setName("add-list-of-job-for-jobtype");
        rule.setDrl(TestData.rule4);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order1,"A");
        // Assert
        boolean hasJob1 = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job1"));
        boolean hasJob2 = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job2"));
        boolean hasJob3 = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job3"));
        assertFalse(hasJob1, "The order should have hasJob1 == false");
        assertFalse(hasJob2, "The order should have hasJob2 == false");
        assertTrue(hasJob3, "The order should have hasJob3 == true");
        assertTrue(order1.isHasRules(),"The order should have hasRules == true");
    }

}
