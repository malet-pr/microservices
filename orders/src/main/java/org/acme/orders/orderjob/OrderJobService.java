package org.acme.orders.orderjob;

import java.util.List;

public interface OrderJobService {
    boolean save(OrderJobDTO orden);
    int saveAll(List<OrderJobDTO> orden);
    OrderJobDTO findById(Long id);
    List<OrderJobDTO> findByIds(List<Long> ids);
    List<OrderJobDTO> findByCodes(List<String> jobCodeList);
}
