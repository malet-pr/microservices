package org.acme.rules.drools.internal.adapter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.acme.rules.drools.internal.model.RuleType;

// TODO: think if adapter is the right call
// TODO: if needed, use more appropriate name and move to utils folder
// TODO: check for possible circular reference with ActionAdapter
@Data
public class RuleTypeAdapter {

    private RuleType ruleType;
    private WoRuleAdapter woInProcess;
    private List<ActionAdapter> actions;
    
    public void addJob(String job, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("addJob");
        action.setNewJob(job);
        action.setRule(ruleName);
        action.setAmount(1);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);

    }
    public void addJobs(String job, int amount, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("addJobs");
        action.setNewJob(job);
        action.setRule(ruleName);
        action.setAmount(amount);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }
    public void removeJob(String job, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("removeJob");
        action.setOldJob(job);
        action.setRule(ruleName);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }

    public void removeAllJobs(String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("removeAllJobs");
        action.setRule(ruleName);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }
    public void removeAllJobsMinusOne(String job, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("removeAllJobsMinusOne");
        action.setOldJob(job);
        action.setRule(ruleName);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }
    public void removeJobsMinusOne(String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("removeJobsMinusOne");
        action.setRule(ruleName);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }
    public void removeJobMinusSpecific(String oldJob, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("removeJobMinusSpecific");
        action.setOldJob(oldJob);
        action.setRule(ruleName);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }
    public void replaceJob(String oldJob, String newJob, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("replaceJob");
        action.setOldJob(oldJob);
        action.setNewJob(newJob);
        action.setRule(ruleName);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }
    
    public void replaceJobsMinusSpecific(String oldJob, String newJob, String ruleName) {
        ActionAdapter action = new ActionAdapter();
        action.setAction("replaceJobsMinusSpecific");
        action.setOldJob(oldJob);
        action.setNewJob(newJob);
        action.setRule(ruleName);
        action.setWorkOrders(Arrays.asList(woInProcess));
        action.setCreationDate(new Date());
        actions.add(action);
    }

}
