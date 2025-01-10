package org.acme.rules.drools;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class WoData{

    private String woNumber;
    private List<WoJob> woJobs;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCreationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCompletionDate;
    private String jobTypeCode;
    private String state;
    private String clientId;
    private String clientType;
    private boolean hasRules;

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

// CONDITIONS
// contains a list of jobCodes
// does not contain single jobCode
// does not contain a list of jobCodes
// contains repeated jobCodes
// check quantity

// ACTIONS
// add Job
// remove list of jobs
// add list of jobs
// deal with repeated jobCodes --> keep one with added quantities
// change quantity
