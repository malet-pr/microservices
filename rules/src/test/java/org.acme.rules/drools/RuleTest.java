package org.acme.rules.drools;

import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RuleTest {

    static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    @Test
    @DisplayName("Tests the rule for Orders without order number")
    public void noOrderNumberTest() {
        log.info("Creating RuleUnit");
        WoDataUnit woDataUnit = new WoDataUnit();
        RuleUnitInstance<WoDataUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(woDataUnit);
        try {
            log.info("Insert data");
            woDataUnit.getWoDataList().add(WoData.builder().woNumber("order1").jobTypeCode("code1").build());
            woDataUnit.getWoDataList().add(WoData.builder().woNumber("order2").jobTypeCode("code2").build());
            woDataUnit.getWoDataList().add(WoData.builder().jobTypeCode("codeNull").build());

            log.info("Run query. Rules are also fired");
            List<WoData> queryResult = instance.executeQuery("FindJobType").toList("$m");

            queryResult.stream().map(WoData::getJobTypeCode).forEach(System.out::println);

            assertEquals(1, queryResult.size());
            assertTrue(woDataUnit.getControlSet().contains("codeNull"), "contains codeNull");

        } finally {
            instance.close();
        }
    }

    @Test
    @DisplayName("Tests the rule for Orders with order number")
    public void orderNumberTest() {
        log.info("Creating RuleUnit");
        WoDataUnit woDataUnit = new WoDataUnit();
        RuleUnitInstance<WoDataUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(woDataUnit);
        try {
            log.info("Insert data");
            woDataUnit.getWoDataList().add(WoData.builder().woNumber("order1").jobTypeCode("code1").build());
            woDataUnit.getWoDataList().add(WoData.builder().woNumber("order2").jobTypeCode("code2").build());
            woDataUnit.getWoDataList().add(WoData.builder().jobTypeCode("codeNull").build());

            log.info("Run query. Rules are also fired");
            List<WoData> queryResult = instance.executeQuery("ListOrderNumber").toList("$m");

            queryResult.stream().map(WoData::getWoNumber).forEach(System.out::println);

            assertEquals(2, queryResult.size());
            assertTrue(woDataUnit.getControlSet().contains("order1"), "contains order1");
            assertTrue(woDataUnit.getControlSet().contains("order2"), "contains order2");

        } finally {
            instance.close();
        }
    }

}