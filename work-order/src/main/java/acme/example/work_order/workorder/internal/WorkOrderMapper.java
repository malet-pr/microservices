package acme.example.work_order.workorder.internal;


import acme.example.work_order.job.internal.JobDAO;
import acme.example.work_order.job.internal.Job;
import acme.example.work_order.workorder.WorkOrderDTO;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.jobtype.internal.JobType;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.jobtype.internal.JobTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WorkOrderMapper {

    private final JobTypeDAO jobTypeDAO;
    private final JobDAO jobDAO;

    @Autowired
    public WorkOrderMapper(JobTypeDAO jobTypeDAO, JobDAO jobDAO) {
        this.jobTypeDAO = jobTypeDAO;
        this.jobDAO = jobDAO;
    }

    public WorkOrderDTO convertToDTO(WorkOrder wo) {
        if(wo == null) {return null;}
        List<WorkOrderJobDTO> jobs = new ArrayList<>();
        wo.getJobs().forEach(j -> {
            WorkOrderJobDTO jobDTO = WorkOrderJobDTO.builder()
                    .woNumber(wo.getWoNumber())
                    .jobCode(j.getJob().getCode())
                    .quantity(j.getQuantity())
                    .activeStatus(j.getActiveStatus())
                    .appliedRule(j.getAppliedRule())
                    .build();
            jobs.add(jobDTO);
        });
        return WorkOrderDTO.builder()
                .woNumber(wo.getWoNumber())
                .jobTypeCode(wo.getJobType().getCode())
                .woJobDTOs(jobs)
                .address(wo.getAddress())
                .city(wo.getCity())
                .state(wo.getState())
                .woCreationDate(wo.getWoCreationDate())
                .woCompletionDate(wo.getWoCompletionDate())
                .clientId(wo.getClientId())
                .appliedRule(wo.getAppliedRule())
                .build();
    }

    public WorkOrder convertToEntity(WorkOrderDTO dto) {
        if(dto == null) {return null;}
        JobType jobType = jobTypeDAO.findByCode(dto.getJobTypeCode());
        if(jobType == null) {return null;}
        List<WorkOrderJob> woJobList = new ArrayList<>();
        try{
            List<Job> jobList = jobDAO.findByCodes(dto.getWoJobDTOs().stream()
                                                    .map(WorkOrderJobDTO::getJobCode).toList());
            jobList.forEach(job -> {
                WorkOrderJob workOrderJob = new WorkOrderJob();
                Optional<WorkOrderJobDTO> woJob = dto.getWoJobDTOs().stream()
                        .filter(wj -> wj.getJobCode().equals(job.getCode())).findFirst();
                if(woJob.isPresent()) {
                    workOrderJob = WorkOrderJob.builder()
                            .job(job)
                            .quantity(woJob.get().getQuantity())
                            .activeStatus(woJob.get().getActiveStatus())
                            .appliedRule(woJob.get().getAppliedRule())
                            .build();
                }
                woJobList.add(workOrderJob);
            });
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return WorkOrder.builder()
                .woNumber(dto.getWoNumber())
                .jobType(jobType)
                .jobs(woJobList)
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .woCreationDate(dto.getWoCreationDate())
                .woCompletionDate(dto.getWoCompletionDate())
                .clientId(dto.getClientId())
                .appliedRule(dto.getAppliedRule())
                .build();
    }
}
