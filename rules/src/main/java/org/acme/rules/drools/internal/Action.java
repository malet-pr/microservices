package org.acme.rules.drools.internal;

import org.springframework.stereotype.Service;

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
}


// ACTIONS
// add Job
// remove list of jobs
// add list of jobs
// deal with repeated jobCodes --> keep one with added quantities
// change quantity