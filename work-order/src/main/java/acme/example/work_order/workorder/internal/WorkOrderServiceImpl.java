package acme.example.work_order.workorder.internal;


import acme.example.work_order.workorder.WorkOrderDTO;
import acme.example.work_order.workorder.WorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderServiceImpl.class);
    private final WorkOrderMapper woMapper;
    private final WorkOrderDAO woDAO;

    @Autowired
    public WorkOrderServiceImpl(WorkOrderMapper woMapper, WorkOrderDAO woDAO) {
        this.woMapper = woMapper;
        this.woDAO = woDAO;
    }

    @Override
    public Boolean save(WorkOrderDTO dto) {
        WorkOrder entity = woMapper.convertToEntity(dto);
        if(entity == null) {return false;}
        if(woDAO.existsByWoNumber(entity.getWoNumber())) {
            log.error("A work order with number {} already exists", entity.getWoNumber());
            return false;
        }
        try {
            entity.getJobs().forEach(j -> j.setWorkOrder(entity));
            woDAO.save(entity);
            log.info("Saved work order {}", entity);
            return true;
        }catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public WorkOrderDTO findById(Long id) {
        return woMapper.convertToDTO(woDAO.findById(id).orElse(null));
    }

    @Override
    public WorkOrderDTO findByWoNumber(String woNumber) {
        return woMapper.convertToDTO(woDAO.findByWoNumber(woNumber));
    }
}
