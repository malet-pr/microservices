package acme.example.work_order.workorderjob.internal;

import acme.example.work_order.workorder.internal.WorkOrder;
import acme.example.work_order.workorder.internal.WorkOrderDAO;
import acme.example.work_order.workorderjob.WorkOrderJobService;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WorkOrderJobServiceImpl implements WorkOrderJobService {

    @Autowired
    WorkOrderJobDAO woJobDAO;

    @Autowired
    WorkOrderDAO woDAO;

    @Autowired
    WorkOrderJobMapper woJobMapper;


    @Override
    public boolean save(WorkOrderJobDTO wo) {
        WorkOrder woEntity = woDAO.findByWoNumber(wo.getWoNumber());
        if (woEntity == null) {
            System.out.println("Cannot save the work order job because the work order does not exist");
            return false;
        }
        try{
            WorkOrderJob entity = woJobMapper.convertToEntity(wo);
            if(entity == null) return false;
            woJobDAO.save(entity);
            return true;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public WorkOrderJobDTO findById(Long id) {
        return woJobMapper.convertToDTO(woJobDAO.findById(id).orElse(null));
    }

    @Override
    public List<WorkOrderJobDTO> findByIds(List<Long> ids) {
        return woJobMapper.convertListToDTO(woJobDAO.findAllById(ids));
    }

    @Override
    public List<WorkOrderJobDTO> findByCodes(List<String> jobCodeList) {
        return woJobMapper.convertListToDTO(woJobDAO.findByCodes(jobCodeList));
    }
}
