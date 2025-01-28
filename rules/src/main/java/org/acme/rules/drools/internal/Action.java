package org.acme.rules.drools.internal;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class Action {

    public static void disableJob(WoData woData, String jobCode, String ruleName){
        woData.getWoJobs().forEach(wj -> {
            if (wj.getJobCode().equals(jobCode)) {
                wj.setActiveStatus("N");
                wj.setAppliedRule(ruleName);
            }
        });
    }

    public static void addJob(WoData woData, String jobCode, int quantity, String ruleName){
        WoJob job = WoJob.builder()
                .woNumber(woData.getWoNumber())
                .jobCode(jobCode)
                .quantity(quantity)
                .activeStatus("Y")
                .appliedRule(ruleName)
                .build();
        woData.getWoJobs().add(job);
    }

    public static void disableJobs(WoData woData, List<String> jobCodes, String ruleName){
        jobCodes.forEach(code -> disableJob(woData, code, ruleName));
    }

    public static void addJobs(WoData woData, Map<String,Integer> newJobs, String ruleName){
        newJobs.forEach((key, value) -> {
            WoJob job = WoJob.builder()
                    .woNumber(woData.getWoNumber())
                    .jobCode(key)
                    .quantity(value)
                    .activeStatus("Y")
                    .appliedRule(ruleName)
                    .build();
            woData.getWoJobs().add(job);
        });
    }

    public static void combineJobs(WoData woData, String jobCode, String ruleName){
        AtomicInteger q = new AtomicInteger();
        woData.getWoJobs().forEach(wj -> {
            if (wj.getJobCode().equals(jobCode)) {
                q.getAndIncrement();
                wj.setActiveStatus("N");
                wj.setAppliedRule(ruleName);
            }
        });
        addJob(woData, jobCode, q.get(), ruleName);
    }

    public static void disableAllButOne(WoData woData, String jobCode, int quantity, String ruleName){
        disableJobs(woData,List.of(jobCode),ruleName);
        addJob(woData, jobCode, quantity, ruleName);
    }

    public static void changeQuantity(WoData woData, String jobCode, int quantity, String ruleName){
        woData.getWoJobs().forEach(wj -> {
            if (wj.getJobCode().equals(jobCode)) {
                wj.setQuantity(quantity);
                wj.setAppliedRule(ruleName);
            }
        });
    }

}
