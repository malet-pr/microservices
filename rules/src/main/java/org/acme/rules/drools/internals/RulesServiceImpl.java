package org.acme.rules.drools.internals;

import org.acme.rules.drools.RulesService;
import org.acme.rules.drools.WoData;
import org.acme.rules.drools.WoDataUnit;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RulesServiceImpl implements RulesService {

    Logger log = LoggerFactory.getLogger(RulesServiceImpl.class);

    @Override
    public WoData runRuleTest(WoData woData) {
        WoDataUnit woDataUnit = new WoDataUnit();
        try (RuleUnitInstance<WoDataUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(woDataUnit)) {
            woDataUnit.getOrders().add(woData);
            instance.fire();
            woData.setHasRules(true);
            return woData;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


}
