package org.acme.rules.drools.internal.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.acme.rules.drools.internal.util.ActionAdapter;
import org.acme.rules.drools.internal.util.RuleTypeAdapter;
import org.acme.rules.drools.internal.dto.WoRuleAdapter;
import org.acme.rules.drools.internal.model.Rule;
import org.acme.rules.drools.internal.model.RuleLog;
import org.acme.rules.drools.internal.util.Constants;
import org.acme.rules.drools.internal.util.WorkOrderUtils;
import org.acme.rules.grpc.woserviceconnect.Order;
import org.acme.rules.grpc.woserviceconnect.WoJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsActionsServiceImpl implements DroolsActionsService {

    @Autowired
    private DroolsRuleServiceImpl droolsService;
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

    private WoJob buildNewJob(Order order, Rule rule, String code) {
        return WoJob.builder()
                .jobCode(code)
                .activeStatus(Constants.YES)
                .woNumber(order.getWoNumber())
                .appliedRule(rule.getName())
                .build();
    }

    @Override
    public Order recoverImpactRules(RuntimeException e, RuleTypeAdapter facts) {
        return Order.builder().build();
    }


    // ARREGLAR
    @Override
    public Order impactRules(RuleTypeAdapter facts) {
        String uuid = UUID.randomUUID().toString();
        Order wo = facts.getWoInProcess();
        List<ActionAdapter> actions = facts.getActions().stream()
                .sorted(Comparator.comparing(ActionAdapter::getCreationDate))
                .toList();
        log.info("Applying rules to WO: {}", wo.getWoNumber());
        for (ActionAdapter action : actions) {
            log.info("Rule {}", action.getRule());
            switch(action.getAction()) {
                case "addJob": addJob(action, wo);
                case "removeJobs": removeJobs(action, wo);
                case "removeAllJobs": removeAllJobs(action, wo);
                case "updateJob": updateJob(action, wo);
            }
        }
        return wo;
    }

    @Override
    public void addJob(ActionAdapter action, Order wo) {
        Rule rule = droolsService.getRule(action.getRule());
        WoJob activateJob = buildNewJob(wo, rule, action.getNewJob());
        activateJob.setAppliedRule(rule.getName());
        List<String> codes = new ArrayList<>(wo.getWoJobs().stream().map(WoJob::getJobCode).toList());
        codes.add(activateJob.getJobCode());
        log.info("Added job {}", activateJob.getJobCode());
    }

    @Override
    public void removeJobs(ActionAdapter action, Order wo){
        List<String> codes = wo.getWoJobs().stream().map(WoJob::getJobCode).toList();
        Rule rule = droolsService.getRule(action.getRule());
        wo.getWoJobs().stream()
            .filter(a -> a.getJobCode().equals(action.getOldJob())
                    && a.getActiveStatus().equals(Constants.ACTIVE_Y) && (a.getAppliedRule() == null))
            .forEach(a -> {
                a.setActiveStatus(Constants.ACTIVE_N);
                a.setAppliedRule(rule.getName());
                log.info("Removed job {}", a.getJobCode());
            });
    }

    @Override
    public void removeAllJobs(ActionAdapter action, Order wo) {
        Rule rule = droolsService.getRule(action.getRule());
        wo.getWoJobs().stream()
            .filter(a -> a.getActiveStatus().equals(Constants.ACTIVE_Y) && (a.getAppliedRule() ==  null))
            .forEach(a -> {
                a.setActiveStatus(Constants.ACTIVE_N);
                a.setAppliedRule(rule.getName());
            });
        log.info("Removed jobs {}", wo.getWoJobs().stream().map(WoJob::getJobCode).collect(Collectors.joining(", ")));
    }

    @Override
    public void updateJob(ActionAdapter action, Order wo) {
        String newJob = action.getNewJob();
        String oldJob = action.getOldJob();
        String ruleName = action.getRule();
        int amount = action.getAmount();
        Rule rule = droolsService.getRule(ruleName);
        List<WoJob> oldJobs = wo.getWoJobs().stream()
                .filter(a -> a.getJobCode().equals(oldJob)
                        && a.getActiveStatus().equals(Constants.ACTIVE_Y) && (a.getAppliedRule() ==  null))
                .toList();
        if (action.getNewJob() != null && amount > 0 && oldJobs.getFirst().getQuantity() >= amount) {
            oldJobs.getFirst().setQuantity(oldJobs.getFirst().getQuantity() - amount);
            WoJob woJob = WoJob.builder()
                    .jobCode(action.getNewJob())
                    .activeStatus(Constants.ACTIVE_Y)
                    .quantity(amount)
                    .woNumber(oldJobs.getFirst().getWoNumber())
                    .appliedRule(rule.getName())
                    .build();
            log.info("Updated job {}", action.getNewJob());
        }
    }

    @Override
    public Order recover(RuntimeException e, RuleTypeAdapter facts) {
        woErrorService.add(facts.getWoInProcess().getWoNumber(), "", e);
        return facts.getWoInProcess();
    }

    private WoJob otActividadBuilder(String jobCode, Integer amount, String woNumber, Rule rule) {
        return WoJob.builder()
                .jobCode(jobCode)
                .activeStatus(Constants.ACTIVE_Y)
                .quantity(amount)
                .woNumber(woNumber)
                .appliedRule(rule.getName())
                .build();
    }

    private List<RuleLog> getLogFinal(List<RuleLog> logs,List<Order> woList) {
        for (RuleLog log : logs) {
            log.setEndDate(LocalDateTime.now());
            log.setFinalActiveJob(getJobs(woList, log.getWoNumber(), Constants.YES));
            log.setFinalInactiveJob(getJobs(woList, log.getWoNumber(), Constants.NO));
        }
        return logs;
    }

    private List<RuleLog> getLogInicial(ActionAdapter action, String uuid) {
        List<RuleLog> logs = new ArrayList<RuleLog>();
        for (Order adapter : action.getWoData()) {
            RuleLog log = new RuleLog();
            log.setRunId(uuid);
            log.setRuleName(action.getRule() + " - " + action.getAction());
            log.setWoNumber(adapter.getWoNumber());
            log.setStartDate(LocalDateTime.now());
            log.setInitialActiveJob(getJob(adapter, Constants.ACTIVE_Y));
            log.setInitialInactiveJob(getJob(adapter, Constants.ACTIVE_N));
            logs.add(log);
        }
        return logs;
    }

    private String getJobs(List<Order> woList, String woNumber, Character active) {
        Optional<Order> wo = woList.stream()
                                        .filter(x -> x.getWoNumber().equalsIgnoreCase(woNumber))
                                        .findFirst();
        return wo.map(woRuleAdapter -> getJob(wo.get(), active)).orElse(null);
    }

    private String getJob(Order wo, Character active) {
        List<String> codes = wo.getWoJobs().stream()
                .filter(j -> j.getActiveStatus().equals(active))
                .map(WoJob::getJobCode)
                .toList();
        return String.join(", ", codes);

    }

}