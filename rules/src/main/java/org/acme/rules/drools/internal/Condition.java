package org.acme.rules.drools.internal;

import org.springframework.stereotype.Service;

@Service
public class Condition {

    public boolean containsJobCode(WoData woData, String jobCode){
        return woData.getWoJobs().stream().anyMatch(j -> j.getJobCode().equals(jobCode));
    }

}

// CONDITIONS
// contains a list of jobCodes
// does not contain single jobCode
// does not contain a list of jobCodes
// contains repeated jobCodes
// check quantity