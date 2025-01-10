package org.acme.rules.drools;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;
import java.util.HashSet;
import java.util.Set;

public class WoDataUnit implements RuleUnitData {

    private final DataStore<WoData> orders;
    private final Set<String> controlSet = new HashSet<>();
    private final Set<WoData> applySet = new HashSet<>();

    public WoDataUnit() {
        this(DataSource.createStore());
    }

    public WoDataUnit(DataStore<WoData> orders) {
        this.orders = orders;
    }

    public DataStore<WoData> getOrders() {
        return orders;
    }

    public Set<String> getControlSet() {
        return controlSet;
    }

    public Set<WoData> getApplySet() {return applySet; }

}
