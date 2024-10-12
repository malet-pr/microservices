package acme.example.work_order.workorderjob;

import java.util.List;

public interface WorkOrderJobService {
    boolean save(WorkOrderJobDTO orden);
    WorkOrderJobDTO findById(Long id);
    List<WorkOrderJobDTO> findByIds(List<Long> ids);
    List<WorkOrderJobDTO> findByCodes(List<String> jobCodeList);
}
