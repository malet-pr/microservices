package org.acme.rules.drools.internal.util;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkOrderUtils {

    /*
    private static JobService jobService;
    private static WorkOrderService workOrderService;
    private static WorkOrderJobService woJobService;
     */

    public List<String> getCodeWoJobs(List<Long> ids){
        /*
        List<WorkOrderJobDTO> woJobs = woJobService.findByIds(ids);
        List<String> jobCodeList = new ArrayList<>();
        woJobs.stream()
                .map(WorkOrderJobDTO::getJobCode)
                .forEach(a -> {
                    JobDTO job = jobService.findByCodeAndActiveStatus(a, Constants.ACTIVE_Y);
                    if(job != null){
                        jobCodeList.add(job.getCode());
                    }
                });
        return jobCodeList;
         */
        return new ArrayList<>();
    }


}
