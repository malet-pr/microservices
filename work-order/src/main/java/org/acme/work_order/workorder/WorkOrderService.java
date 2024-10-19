package org.acme.work_order.workorder;

import org.acme.work_order.workorder.internal.WorkOrder;

public interface WorkOrderService {
    Boolean save(WorkOrderDTO workOrderDTO);
    WorkOrderDTO findById(Long id);
    WorkOrderDTO findByWoNumber(String woNumber);
}
