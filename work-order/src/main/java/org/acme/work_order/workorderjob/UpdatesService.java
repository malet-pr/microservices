package org.acme.work_order.workorderjob;

import jakarta.transaction.Transactional;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorder.internal.WorkOrder;

import java.util.List;

public interface UpdatesService {

    boolean updateAfterRules(WorkOrderDTO newDto, WorkOrder oldWO);
}
