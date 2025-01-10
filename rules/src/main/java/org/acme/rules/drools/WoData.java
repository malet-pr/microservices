package org.acme.rules.drools;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WoData {
    private String woNumber;
    private List<WoJob> woJobs;
    private LocalDateTime woCreationDate;
    private LocalDateTime woCompletionDate;
    private String jobTypeCode;
    private String state;
    private String clientId;
    private String clientType;

    public boolean containsJobCode(String jobCode){
        return this.getWoJobs().stream().anyMatch(j -> j.getJobCode().equals(jobCode));
    }

    public void disableJob(String jobCode, String ruleName){
        this.getWoJobs().forEach(wj -> {
            if (wj.getJobCode().equals(jobCode)) {
                wj.setActiveStatus("N");
                wj.setAppliedRule(ruleName);
            }
        });
    }

}

// contains a list of jobCodes
// does not contain single jobCode
// does not contain a list of jobCodes
// add Job
//
