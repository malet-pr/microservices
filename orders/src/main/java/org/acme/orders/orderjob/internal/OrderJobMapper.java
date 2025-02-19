package org.acme.orders.orderjob.internal;

import org.acme.orders.job.internal.Job;
import org.acme.orders.job.internal.JobDAO;
import org.acme.orders.order.internal.Order;
import org.acme.orders.order.internal.OrderDAO;
import org.acme.orders.orderjob.OrderJobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderJobMapper {

    private final OrderDAO orderDAO;
    private final JobDAO jobDAO;

    @Autowired
    public OrderJobMapper(OrderDAO orderDAO, JobDAO jobDAO) {
        this.orderDAO = orderDAO;
        this.jobDAO = jobDAO;
    }

    public OrderJobDTO convertToDTO(OrderJob woJob) {
        if(woJob == null) {return null;}
        return OrderJobDTO.builder()
                .woNumber(woJob.getOrder().getWoNumber())
                .jobCode(woJob.getJob().getCode())
                .activeStatus(woJob.getActiveStatus())
                .quantity(woJob.getQuantity())
                .appliedRule(woJob.getAppliedRule())
                .build();
    }

    public OrderJob convertToEntity(OrderJobDTO dto) {
        if(dto == null) {return null;}
        Order or = orderDAO.findByWoNumber(dto.getWoNumber());
        Job j = jobDAO.findByCode(dto.getJobCode());
        return OrderJob.builder()
                .order(or)
                .job(j)
                .activeStatus(dto.getActiveStatus())
                .quantity(dto.getQuantity())
                .appliedRule(dto.getAppliedRule())
                .build();
    }

    public List<OrderJobDTO> convertListToDTO(List<OrderJob> woJobs) {
        List<OrderJobDTO> dtos = new ArrayList<>();
        woJobs.forEach(wj -> dtos.add(convertToDTO(wj)));
        return dtos;
    }

    public List<OrderJob> convertListToEntity(List<OrderJobDTO> dtos) {
        List<OrderJob> jobs = new ArrayList<>();
        dtos.forEach(wj -> jobs.add(convertToEntity(wj)));
        return jobs;
    }


}
