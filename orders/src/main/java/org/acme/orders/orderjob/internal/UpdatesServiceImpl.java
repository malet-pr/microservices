package org.acme.orders.orderjob.internal;

import org.acme.orders.job.JobService;
import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.internal.Order;
import org.acme.orders.order.internal.OrderDAO;
import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.orderjob.OrderJobService;
import org.acme.orders.orderjob.UpdatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UpdatesServiceImpl implements UpdatesService {

    private static final Logger log = LoggerFactory.getLogger(UpdatesServiceImpl.class);

    private final OrderJobDAO woJobDAO;
    private final OrderDAO woDAO;
    private final OrderJobService woJobService;
    private final JobService jobService;
    
    public UpdatesServiceImpl(OrderJobDAO woJobDAO, OrderDAO woDAO, OrderJobService woJobService, JobService jobService) {
        this.woJobDAO = woJobDAO;
        this.woDAO = woDAO;
        this.woJobService = woJobService;
        this.jobService = jobService;
    }


    @Override
    public boolean updateAfterRules(OrderDTO newDto, Order oldWO) {
        boolean updated = false;

        log.info("Retrieving data for Order {}", oldWO.getId());
        List<String> oldJobCodes = oldWO.getJobs().stream().map(el -> el.getJob().getCode()).toList();
        List<OrderJobDTO> dtos = newDto.getWoJobDTOs();

        log.info("Splitting WorkOrderJobDTOs into one map with jobs to be updated/removed and one with new jobs");
        AtomicInteger qUpdate = new AtomicInteger(0);
        Map<String,OrderJobDTO> map1 = dtos.stream().filter(el -> oldJobCodes.contains(el.getJobCode()))
                .collect(Collectors.toMap(OrderJobDTO::getJobCode, Function.identity()));
        Map<String,OrderJobDTO> map2 = dtos.stream().filter(el -> !oldJobCodes.contains(el.getJobCode()))
                .collect(Collectors.toMap(OrderJobDTO::getJobCode, Function.identity()));

        log.info("update first map");
        //List<String> codes = map1.keySet().stream().toList();
        map1.forEach((k,v)->{
            Long jobId = jobService.findIdByCode(v.getJobCode());
            int up = woJobDAO.rulesUpdate(v.getQuantity(),v.getAppliedRule(),v.getActiveStatus(),oldWO.getId(),jobId);
            if(up > 0) qUpdate.getAndIncrement();
        });

        log.info("insert second map");
        int qInsert = woJobService.saveAll(map2.values().stream().toList());

        log.info("update work order and return");
        if(qUpdate.get()+qInsert == dtos.size()) {
            updated = true;
            woDAO.updateHasRule(newDto.getWoNumber());
        }
        return updated;
    }

}
