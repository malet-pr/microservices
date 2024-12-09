package org.acme.orders.order.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDAO extends JpaRepository<Order,Long> {
    Order findByWoNumber(String woNumber);
    Boolean existsByWoNumber(String woNumber);

    @Modifying
    @Query(value = "update wo.order set has_rules = TRUE where wo_number = :woNumber", nativeQuery = true)
    void updateHasRule(@Param("woNumber") String woNumber);
    @Query(value = "select o.id from wo.order o where o.wo_number=?1 fetch first 1 rows only", nativeQuery = true)
    Boolean existsByOrderNumber(@Param("woNumber") String woNumber);
    @Query(value = "select * from wo.order o where o.wo_number = :woNumber", nativeQuery = true)
    Order findByOrderNumber(@Param("woNumber") String woNumber);
}
