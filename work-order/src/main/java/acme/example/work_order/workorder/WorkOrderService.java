package acme.example.work_order.workorder;


public interface WorkOrderService {
    Boolean save(WorkOrderDTO workOrderDTO);
    WorkOrderDTO findById(Long id);
    WorkOrderDTO findByWoNumber(String woNumber);
}
