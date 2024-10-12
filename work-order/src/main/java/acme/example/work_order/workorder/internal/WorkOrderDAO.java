package acme.example.work_order.workorder.internal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderDAO extends JpaRepository<WorkOrder,Long> {
    WorkOrder findByWoNumber(String woNumber);
    Boolean existsByWoNumber(String woNumber);
}
