package acme.example.work_order.workorderjob.internal;


import acme.example.work_order.job.internal.JobDAO;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorder.internal.WorkOrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WorkOrderJobMapper {

    private final WorkOrderDAO workOrderDAO;
    private final JobDAO jobDAO;

    @Autowired
    public WorkOrderJobMapper(WorkOrderDAO workOrderDAO, JobDAO jobDAO) {
        this.workOrderDAO = workOrderDAO;
        this.jobDAO = jobDAO;
    }

    public WorkOrderJobDTO convertToDTO(WorkOrderJob woJob) {
        if(woJob == null) {return null;}
        return WorkOrderJobDTO.builder()
                .woNumber(woJob.getWorkOrder().getWoNumber())
                .jobCode(woJob.getJob().getCode())
                .activeStatus(woJob.getActiveStatus())
                .quantity(woJob.getQuantity())
                .appliedRule(woJob.getAppliedRule())
                .build();
    }

    public WorkOrderJob convertToEntity(WorkOrderJobDTO dto) {
        if(dto == null) {return null;}
        return WorkOrderJob.builder()
                .workOrder(workOrderDAO.findByWoNumber(dto.getWoNumber()))
                .job(jobDAO.findByCode(dto.getJobCode()))
                .activeStatus(dto.getActiveStatus())
                .quantity(dto.getQuantity())
                .appliedRule(dto.getAppliedRule())
                .build();
    }

    public List<WorkOrderJobDTO> convertListToDTO(List<WorkOrderJob> woJobs) {
        List<WorkOrderJobDTO> dtos = new ArrayList<>();
        woJobs.forEach(wj -> dtos.add(convertToDTO(wj)));
        return dtos;
    }

    public List<WorkOrderJob> convertListToEntity(List<WorkOrderJobDTO> dtos) {
        List<WorkOrderJob> jobs = new ArrayList<>();
        dtos.forEach(wj -> jobs.add(convertToEntity(wj)));
        return jobs;
    }


}
