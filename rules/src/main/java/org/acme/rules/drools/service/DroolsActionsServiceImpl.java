package org.acme.rules.drools.service;

import java.util.*;

import org.acme.rules.drools.internal.adapter.ActionAdapter;
import org.acme.rules.drools.internal.adapter.RuleTypeAdapter;
import org.acme.rules.drools.internal.adapter.WoRuleAdapter;
import org.acme.rules.drools.internal.model.Rule;
import org.acme.rules.drools.internal.model.RuleLog;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.drools.internal.repository.RuleLogDAO;
import org.acme.rules.drools.internal.util.Constants;
import org.acme.rules.drools.internal.util.WorkOrderUtils;
import org.acme.rules.grpc.woserviceconnect.JobDTO;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;
import org.acme.rules.grpc.woserviceconnect.WorkOrderJobDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsActionsServiceImpl implements DroolsActionsService {

    @Autowired
    private DroolsRuleServiceImpl droolsService;
    @Autowired
    private RuleLogDAO ruleLogDAO;
    @Autowired
    WorkOrderUtils workOrderUtils;
    @Autowired
    private WoErrorsService woErrorService;

    public static Logger log = LoggerFactory.getLogger(DroolsActionsServiceImpl.class);

    // ARREGLAR
    private void updateJobListInWoRule(WoRuleAdapter adapter) {
        List<String> codes = adapter.getJobCodeList();
        //adapter.setJobCodeList(workOrderUtils.getCodeWoJobs(jobService.findByCodes(codes)));
    }

    @Override
    public WorkOrderDTO recoverImpactRules(RuntimeException e, RuleTypeAdapter facts) {
        return WorkOrderDTO.builder().build();
    }

    private RuleType buildNewRuleType(String name) {
        RuleType ruleType = new RuleType();
        if (name != null) {
            String ruleTypeName = name.substring(0, 2);
            ruleType.setId(20L);
            ruleType.setName(ruleTypeName.equalsIgnoreCase("GI") ? "Global" : ruleTypeName);
            ruleType.setCode(ruleTypeName.equalsIgnoreCase("GI") ? "REGL" : "RE" + ruleTypeName);
            ruleType.setHeader(ruleTypeName.equalsIgnoreCase("GI") ? "REGL.drl" : "RE" + ruleTypeName + ".drl");
            ruleType.setVisible(Constants.YES);
            ruleType.setGrouping(ruleTypeName.equalsIgnoreCase("GI") ? "A" : ruleTypeName.substring(0, 1));
        }
        return ruleType;
    }

    private WorkOrderJobDTO buildNewJob(WorkOrderDTO wo, Rule rule, JobDTO job) {
        return WorkOrderJobDTO.builder()
                .jobCode(job.getCode())
                .activeStatus(Constants.YES)
                .woNumber(wo.getWoNumber())
                .appliedRule(rule.getName())
                .build();
    }

    // ARREGLAR
    @Override
    public WorkOrderDTO impactRules(RuleTypeAdapter facts) {
        /*
        String uuid = UUID.randomUUID().toString();
        WorkOrderDTO wo = woService.findByWoNumber(facts.getWoInProcess().getWoNumber());
        List<ActionAdapter> actions = facts.getActions().stream()
                .sorted(Comparator.comparing(ActionAdapter::getCreationDate))
                .toList();
        for (ActionAdapter action : actions) {
            List<RuleLog> log1 = getLogInicial(action, uuid);
            switch(action.getAction()) {
                case "addJob": addJob(action, wo);
                case "removeJobs": removeJobs(action, wo);
                case "removeAllJobs": removeAllJobs(action, wo);
                case "removeAllJobsExceptOne": removeAllJobsExceptOne(action, wo);
                case "replaceJob": replaceJob(action, wo);
            }
            List<RuleLog> log2 = getLogFinal(log1, action.getWorkOrders());
            ruleLogDAO.saveAll(log2);
        }
        return wo;
        */
         return WorkOrderDTO.builder().build();
    }

    // ARREGLAR
    @Override
    public void addJob(ActionAdapter action, WorkOrderDTO wo) {
        /*
        String newJob = action.getNewJob();
        String ruleName = action.getRule();
        Rule rule = droolsService.getRule(ruleName);
        JobDTO job = jobService.findByCode(newJob);
        WorkOrderJobDTO activateJob = buildNewJob(wo, rule, job);
        activateJob.setAppliedRule(rule.getName());
        List<String> codes = new ArrayList<>(wo.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList());
        codes.add(activateJob.getJobCode());
         */
    }

    // ARREGLAR
    @Override
    public void removeJobs(ActionAdapter action, WorkOrderDTO wo){
        /*
        String oldJob = action.getOldJob();
        String ruleName = action.getRule();
        List<String> codes = wo.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList();
        List<WorkOrderJobDTO> woJobs = woJobService.findByCodes(codes);
        Rule rule = droolsService.getRule(ruleName);
        woJobs.stream()
            .filter(a -> a.getJobCode().equals(oldJob)
                    && a.getActiveStatus().equals(Constants.ACTIVE_Y) && (a.getAppliedRule() == null))
            .forEach(a -> {
                a.setActiveStatus(Constants.ACTIVE_N);
                a.setAppliedRule(rule.getName());
            });
         */
    }

    // ARREGLAR
    @Override
    public void removeAllJobs(ActionAdapter action, WorkOrderDTO wo) {
        /*
        String ruleName = action.getRule();
        List<String> codes = wo.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList();
        List<WorkOrderJobDTO> woJobs = woJobService.findByCodes(codes);
        Rule rule = droolsService.getRule(ruleName);
        woJobs.stream()
            .filter(a -> a.getActiveStatus().equals(Constants.ACTIVE_Y) && (a.getAppliedRule() ==  null))
            .forEach(a -> {
                a.setActiveStatus(Constants.ACTIVE_N);
                a.setAppliedRule(rule.getName());
            });
         */
    }

    // ARREGLAR
    @Override
    public void removeAllJobsExceptOne(ActionAdapter action, WorkOrderDTO wo) {
        /*
        String oldJob = action.getOldJob();
        String ruleName = action.getRule();
        List<String> codes = wo.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList();
        List<WorkOrderJobDTO> woJobs = woJobService.findByCodes(codes);
        Rule rule = droolsService.getRule(ruleName);
        woJobs.stream()
            .filter(a -> a.getJobCode().equals(oldJob)
                    && a.getActiveStatus().equals(Constants.ACTIVE_Y) && (a.getAppliedRule() ==  null))
            .forEach(a -> {
                a.setActiveStatus(Constants.ACTIVE_N);
                a.setAppliedRule(rule.getName());
            });
         */
    }

    // ARREGLAR
    @Override
    public void replaceJob(ActionAdapter action, WorkOrderDTO wo) {
        /*
        String newJob = action.getNewJob();
        String oldJob = action.getOldJob();
        String ruleName = action.getRule();
        int amount = action.getAmount();
        Rule rule = droolsService.getRule(ruleName);
        List<String> codes = wo.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList();
        List<WorkOrderJobDTO> woJobs = woJobService.findByCodes(codes);
        JobDTO job = jobService.findByCode(newJob);
        List<WorkOrderJobDTO> oldJobs = woJobs.stream()
                .filter(a -> a.getJobCode().equals(oldJob)
                        && a.getActiveStatus().equals(Constants.ACTIVE_Y) && (a.getAppliedRule() ==  null))
                .toList();
        if (job != null && amount > 0 && oldJobs.getFirst().getQuantity() >= amount) {
            oldJobs.getFirst().setQuantity(oldJobs.getFirst().getQuantity() - amount);
            WorkOrderJobDTO woJob = WorkOrderJobDTO.builder()
                    .jobCode(job.getCode())
                    .activeStatus(Constants.ACTIVE_Y)
                    .quantity(amount)
                    .woNumber(oldJobs.getFirst().getWoNumber())
                    .appliedRule(rule.getName())
                    .build();
            woJobService.save(woJob);
        }
         */
    }

    // ARREGLAR
    @Override
    public WorkOrderDTO recover(RuntimeException e, RuleTypeAdapter facts) {
        woErrorService.add(facts.getWoInProcess().getWoNumber(), "", e);
    //    return  woService.findByWoNumber(facts.getWoInProcess().getWoNumber());
        return WorkOrderDTO.builder().build();
    }

    // ARREGLAR
    private WorkOrderJobDTO otActividadBuilder(String jobCode, Integer amount, String woNumber, Rule rule) {
        /*
        JobDTO job = jobService.findByCode(jobCode);
        return WorkOrderJobDTO.builder()
                .jobCode(jobCode)
                .activeStatus(Constants.ACTIVE_Y)
                .quantity(amount)
                .woNumber(woNumber)
                .appliedRule(rule.getName())
                .build();
         */
        return new WorkOrderJobDTO();
    }

    private List<RuleLog> getLogFinal(List<RuleLog> logs, List<WoRuleAdapter> woList) {
        for (RuleLog log : logs) {
            log.setEndDate(new Date());
            log.setFinalActiveJob(getJobs(woList, log.getWoNumber(), Constants.YES));
            log.setFinalInactiveJob(getJobs(woList, log.getWoNumber(), Constants.NO));
        }
        return logs;
    }

    private List<RuleLog> getLogInicial(ActionAdapter action, String uuid) {
        List<RuleLog> logs = new ArrayList<RuleLog>();
        for (WoRuleAdapter adapter : action.getWorkOrders()) {
            RuleLog log = new RuleLog();
            log.setRunId(uuid);
            log.setRuleName(action.getRule() + " - " + action.getAction());
            log.setWoNumber(adapter.getWoNumber());
            log.setStartDate(new Date());
            log.setInitialActiveJob(getJob(adapter.getWoNumber(), Constants.NO));
            log.setInitialInactiveJob(getJob(adapter.getWoNumber(), Constants.NO));
            logs.add(log);
        }
        return logs;
    }

    private String getJobs(List<WoRuleAdapter> woList, String woNumber, Character active) {
        Optional<WoRuleAdapter> wo = woList.stream()
                .filter(x -> x.getWoNumber().equalsIgnoreCase(woNumber))
                .findFirst();
        return wo.map(woRuleAdapter -> getJob(woRuleAdapter.getWoNumber(), active)).orElse(null);
    }

    // ARREGLAR
    private String getJob(String woNumber, Character active) {
        /*
        WorkOrderDTO wo = woService.findByWoNumber(woNumber);
        List<String> codes = wo.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList();
        List<WorkOrderJobDTO> jobList = woJobService.findByCodes(codes);
        return jobList.stream()
                .filter(a -> a.getActiveStatus()==active)
                .map(WorkOrderJobDTO::getJobCode)
                .collect(Collectors.joining(", "));
         */
        return "";
    }

}