package org.acme.orders.orderjob.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJobDAO extends JpaRepository<OrderJob,Long> {
    @Query(value = "select wj.* from wo.WO_JOB wj inner join wo.JOB j on wj.job_id=j.id where j.code in ?1", nativeQuery = true)
    List<OrderJob> findByCodes(List<String> jobCodeList);
    @Query(value = "select wj.* from wo.WO_JOB wj inner join wo.ORDER o on wj.order_id = o.id where o.wo_number = ?1", nativeQuery = true)
    List<OrderJob> findByWorkOrderNumber(String woNumber);

    // ACÀ HAY UN ERROR
    @Modifying
    @Query(value = "update wo.wo_job set quantity = :quantity, applied_rule = :appliedRule, active_status = :activeStatus " +
            "where order_id = :woId and job_id = :job_id", nativeQuery = true)
    int rulesUpdate(@Param("quantity") int quantity, @Param("appliedRule") String appliedRule,
                    @Param("activeStatus") Character activeStatus, @Param("woId") Long woId,
                    @Param("job_id") Long jobId) ;
}

