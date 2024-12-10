package org.acme.orders.order.internal;


import org.acme.orders.job.internal.Job;
import org.acme.orders.job.internal.JobDAO;
import org.acme.orders.jobtype.internal.JobType;
import org.acme.orders.jobtype.internal.JobTypeDAO;
import org.acme.orders.order.OrderDTO;
import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.orderjob.internal.OrderJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderMapper {

    private final JobTypeDAO jobTypeDAO;
    private final JobDAO jobDAO;

    @Autowired
    public OrderMapper(JobTypeDAO jobTypeDAO, JobDAO jobDAO) {
        this.jobTypeDAO = jobTypeDAO;
        this.jobDAO = jobDAO;
    }

    public OrderDTO convertToDTO(Order wo) {
        if(wo == null) {return null;}
        List<OrderJobDTO> jobs = new ArrayList<>();
        wo.getJobs().forEach(j -> {
            OrderJobDTO jobDTO = OrderJobDTO.builder()
                    .woNumber(wo.getWoNumber())
                    .jobCode(j.getJob().getCode())
                    .quantity(j.getQuantity())
                    .activeStatus(j.getActiveStatus())
                    .appliedRule(j.getAppliedRule())
                    .build();
            jobs.add(jobDTO);
        });
        return OrderDTO.builder()
                .woNumber(wo.getWoNumber())
                .jobType(wo.getJobType().getCode())
                .woJobDTOs(jobs)
                .address(wo.getAddress())
                .city(wo.getCity())
                .state(wo.getState())
                .woCreationDate(wo.getWoCreationDate())
                .woCompletionDate(wo.getWoCompletionDate())
                .clientId(wo.getClientId())
                .hasRules(wo.getHasRules())
                .build();
    }

    public Order convertToEntity(OrderDTO dto) {
        if(dto == null) {return null;}
        if(dto.getJobType() == null) return null;
        JobType jobType = jobTypeDAO.findByCode(dto.getJobType())
        if(jobType == null) {return null;}
        List<OrderJob> woJobList = new ArrayList<>();
        try{
            List<Job> jobList = jobDAO.findByCodes(dto.getWoJobDTOs().stream()
                                                    .map(OrderJobDTO::getJobCode).toList());
            jobList.forEach(job -> {
                OrderJob orderJob = new OrderJob();
                Optional<OrderJobDTO> woJob = dto.getWoJobDTOs().stream()
                        .filter(wj -> wj.getJobCode().equals(job.getCode())).findFirst();
                if(woJob.isPresent()) {
                    orderJob = OrderJob.builder()
                            .job(job)
                            .quantity(woJob.get().getQuantity())
                            .activeStatus(woJob.get().getActiveStatus())
                            .appliedRule(woJob.get().getAppliedRule())
                            .build();
                }
                woJobList.add(orderJob);
            });
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return Order.builder()
                .woNumber(dto.getWoNumber())
                .jobType(jobType)
                .jobs(woJobList)
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .woCreationDate(dto.getWoCreationDate())
                .woCompletionDate(dto.getWoCompletionDate())
                .clientId(dto.getClientId())
                .hasRules(dto.getHasRules())
                .build();
    }
}
