package org.acme.orders.order;

import org.acme.orders.order.internal.Order;

public interface OrderService {
    Boolean save(OrderDTO workOrderDTO);
    Boolean updateAfterRules(OrderDTO dto);
    OrderDTO findById(Long id);
    OrderDTO findByWoNumber(String woNumber);

    String createMessage(Order order);
}
