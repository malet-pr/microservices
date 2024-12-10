package org.acme.orders.order;

public interface OrderService {
    Boolean save(OrderDTO workOrderDTO);
    Boolean updateAfterRules(OrderDTO dto);
    OrderDTO findById(Long id);
    OrderDTO findByWoNumber(String woNumber);
}
