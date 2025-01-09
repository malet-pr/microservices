package org.acme.rules.drools;

import lombok.Getter;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;
import java.util.HashSet;
import java.util.Set;

public class WoDataUnit implements RuleUnitData {

    private final DataStore<WoData> woDataList;
    private final Set<String> controlSet = new HashSet<>();

    public WoDataUnit() {
        this(DataSource.createStore());
    }

    public WoDataUnit(DataStore<WoData> woDataList) {
        this.woDataList = woDataList;
    }

    public DataStore<WoData> getWoDataList() {
        return woDataList;
    }

    public Set<String> getControlSet() {
        return controlSet;
    }

}

