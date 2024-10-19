package org.acme.work_order.workorder.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkOrderDAO extends JpaRepository<WorkOrder,Long> {
    WorkOrder findByWoNumber(String woNumber);
    Boolean existsByWoNumber(String woNumber);
    @Modifying
    @Query(value = "update wo.work_order set applied_rule = :rules where wo_number = :woNumber", nativeQuery = true)
    int updateAppliedRule(@Param("rules") String rules,@Param("woNumber") String woNumber);
}
