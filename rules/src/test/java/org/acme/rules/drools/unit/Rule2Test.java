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
public class Rule2Test {

    static final Logger log = LoggerFactory.getLogger(Rule1Test.class);
    @Mock
    RuleDAO ruleDaoMock;
    @Mock
    RuleTypeDAO ruleTypeDaoMock;
    @InjectMocks
    RulesServiceImpl service;


    @Test
    @DisplayName("Tests the rule for adding a job - applies")
    void addJobTest_yes() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        List<WoJob> woJobs = new ArrayList<>();
        woJobs.add(wj1);
        WoData order1 = WoData.builder().woNumber("order1").jobTypeCode("code1b").woJobs(woJobs).build();
        Rule rule = new Rule();
        rule.setName("add-job-for-jobtype");
        rule.setDrl(TestData.rule2);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order1,"A");
        // Assert
        boolean hasJob = order1.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job2"));
        assertTrue(hasJob, "The order should have hasJob == true");
        assertTrue(order1.isHasRules(),"The order should have hasRules == true");
        if(hasJob){
            assertEquals("add job for jobtype", order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2")).findFirst()
                    .get().getAppliedRule());
            assertEquals(5, order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2")).findFirst()
                    .get().getQuantity());
            assertEquals("Y", order1.getWoJobs().stream()
                    .filter(wj -> wj.getJobCode().equals("job2")).findFirst()
                    .get().getActiveStatus());
        }
    }

    @Test
    @DisplayName("Tests the rule for adding jobs - doesn't apply")
    public void addJobTest_not() {
        // Arrange
        WoJob wj1 = WoJob.builder().jobCode("job1").activeStatus("Y").build();
        List<WoJob> woJobs = new ArrayList<>();
        woJobs.add(wj1);
        WoData order2 = WoData.builder().woNumber("order2").jobTypeCode("code2b").woJobs(woJobs).build();
        Rule rule = new Rule();
        rule.setName("add-job-for-jobtype");
        rule.setDrl(TestData.rule2);
        RuleType ruleType = new RuleType();
        ruleType.setHeader(TestData.imports);
        Mockito.when(ruleDaoMock.findByActiveStatus('Y')).thenReturn(List.of(rule));
        Mockito.when(ruleTypeDaoMock.findByGrouping("A")).thenReturn(ruleType);
        // Act
        service.runRuleTest(order2,"A");
        // Assert
        boolean hasJob = order2.getWoJobs().stream()
                .anyMatch(wj -> wj.getJobCode().equals("job2"));
        assertFalse(hasJob, "The order should have hasJob == false");
        assertTrue(order2.isHasRules(),"The order should have hasRules == true");
    }


}
