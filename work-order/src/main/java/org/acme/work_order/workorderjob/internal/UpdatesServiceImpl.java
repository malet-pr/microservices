package org.acme.work_order.workorderjob.internal;

import jakarta.transaction.Transactional;
import org.acme.work_order.job.JobService;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorder.internal.WorkOrder;
import org.acme.work_order.workorder.internal.WorkOrderDAO;
import org.acme.work_order.workorderjob.WorkOrderJobDTO;
import org.acme.work_order.workorderjob.WorkOrderJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.acme.work_order.workorderjob.UpdatesService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UpdatesServiceImpl implements UpdatesService {

    private static final Logger log = LoggerFactory.getLogger(UpdatesServiceImpl.class);

    @Autowired
    private WorkOrderJobDAO woJobDAO;

    @Autowired
    private WorkOrderDAO woDAO;

    @Autowired
    private WorkOrderJobService woJobService;

    @Autowired
    private JobService jobService;


    @Override
    public boolean updateAfterRules(WorkOrderDTO newDto, WorkOrder oldWO) {
        boolean updated = false;

        // retrieve some data
        String woNumber = oldWO.getWoNumber();
        List<String> oldJobCodes = oldWO.getJobs().stream().map(el -> el.getJob().getCode()).toList();
        List<WorkOrderJobDTO> dtos = newDto.getWoJobDTOs();

        // split the list in two maps. map1 will be updated, map2 will be inserted
        AtomicInteger qUpdate = new AtomicInteger(0);
        Map<String,WorkOrderJobDTO> map1 = dtos.stream().filter(el -> oldJobCodes.contains(el.getJobCode()))
                .collect(Collectors.toMap(WorkOrderJobDTO::getJobCode, Function.identity()));
        map1.forEach((k,v)->{
            Long jobId = jobService.findIdByCode(v.getJobCode());
            int up = woJobDAO.rulesUpdate(v.getQuantity(),v.getAppliedRule(),v.getActiveStatus(),oldWO.getId(),jobId);
            if(up > 0) qUpdate.getAndIncrement();
        });
        Map<String,WorkOrderJobDTO> map2 = dtos.stream().filter(el -> !oldJobCodes.contains(el.getJobCode()))
                .collect(Collectors.toMap(WorkOrderJobDTO::getJobCode, Function.identity()));
        int qInsert = woJobService.saveAll(map2.values().stream().toList());

        // collect all rules applied and add them to the WO
        StringBuilder rulesBuilder = new StringBuilder();
        if(oldWO.getAppliedRule() != null && !oldWO.getAppliedRule().isBlank())
            rulesBuilder.append(oldWO.getAppliedRule()).append(",");
        dtos.forEach(d -> {
            if(!d.getAppliedRule().isBlank()) rulesBuilder.append(d.getAppliedRule()).append(",");
        });
        if(!rulesBuilder.isEmpty()) rulesBuilder.deleteCharAt(rulesBuilder.length()-1);
        int updateWO = woDAO.updateAppliedRule(rulesBuilder.toString(),woNumber);

        if(qUpdate.get()+qInsert == dtos.size() && updateWO == 1) updated = true;
        return updated;
    }

}
