package org.acme.orders.orderjob;

import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.internal.Order;


public interface UpdatesService {

    boolean updateAfterRules(OrderDTO newDto, Order oldWO);
}
