package acme.example.work_order.workorderjob.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkOrderJobDAO extends JpaRepository<WorkOrderJob,Long> {
    @Query(value = "select wj.* from WO_JOB wj inner join JOB j on wj.job_id=j.id where j.code in ?1", nativeQuery = true)
    List<WorkOrderJob> findByCodes(List<String> jobCodeList);
    @Query(value = "select wj.* from WO_JOB wj inner join WORK_ORDER wo on wj.work_order_id = wo.id where wo.wo_number = ?1", nativeQuery = true)
    List<WorkOrderJob> findByWorkOrderNumber(String woNumber);
}
