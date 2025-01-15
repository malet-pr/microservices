package org.acme.rules.drools;

import org.acme.rules.drools.internal.WoData;

public interface RulesService {
    WoData runRuleTest(WoData woData);
}
