package org.acme.rules.drools.internal.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.acme.rules.drools.WorkOrderData;
import org.acme.rules.drools.internal.model.RuleType;

@Data
public class RuleTypeAdapter {

    private RuleType ruleType;
    private WorkOrderData woInProcess;
    private List<ActionAdapter> actions;
    
    public void addJob(String job, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("addJob");
        action.setNewJob(job);
        action.setRule(ruleName);
        action.setAmount(1);
        action.setWoData(Arrays.asList(woInProcess));
        action.setCreationDate(LocalDateTime.now());
        actions.add(action);

    }

    public void addJobs(String job, int amount, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("addJobs");
        action.setNewJob(job);
        action.setRule(ruleName);
        action.setAmount(amount);
        action.setWoData(Arrays.asList(woInProcess));
        action.setCreationDate(LocalDateTime.now());
        actions.add(action);
    }

    public void removeJob(String job, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("removeJob");
        action.setOldJob(job);
        action.setRule(ruleName);
        action.setWoData(Arrays.asList(woInProcess));
        action.setCreationDate(LocalDateTime.now());
        actions.add(action);
    }

    public void removeAllJobs(String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("removeAllJobs");
        action.setRule(ruleName);
        action.setWoData(Arrays.asList(woInProcess));
        action.setCreationDate(LocalDateTime.now());
        actions.add(action);
    }

    public void updateJob(String oldJob, String newJob, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("replaceJob");
        action.setOldJob(oldJob);
        action.setNewJob(newJob);
        action.setRule(ruleName);
        action.setWoData(Arrays.asList(woInProcess));
        action.setCreationDate(LocalDateTime.now());
        actions.add(action);
    }

}
