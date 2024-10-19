package org.acme.work_order.workorder.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrderDAO extends JpaRepository<WorkOrder,Long> {
    WorkOrder findByWoNumber(String woNumber);
    Boolean existsByWoNumber(String woNumber);
}
