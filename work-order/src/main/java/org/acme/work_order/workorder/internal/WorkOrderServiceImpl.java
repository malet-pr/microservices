package org.acme.work_order.workorder.internal;

import org.acme.work_order.grpc.*;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorder.WorkOrderService;
import org.acme.work_order.workorderjob.UpdatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderServiceImpl implements WorkOrderService {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderServiceImpl.class);

    @Autowired
    private WorkOrderMapper woMapper;
    @Autowired
    private WorkOrderDAO woDAO;
    @Autowired
    private UpdatesService updService;


    @Override
    public Boolean save(WorkOrderDTO dto) {
        boolean result = false;
        WorkOrder entity = woMapper.convertToEntity(dto);
        if(entity == null) {return false;}
        if(woDAO.existsByWoNumber(entity.getWoNumber())) {
            log.error("A work order with number {} already exists", entity.getWoNumber());
            return false;
        }
        try {
            entity.getJobs().forEach(j -> j.setWorkOrder(entity));
            woDAO.save(entity);
            log.info("Successfully saved work order {}", entity);
            result = true;
        }catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return result;
    }

    @Override
    public void updateAfterRules(WorkOrderDTO dto) {
        try{
            WorkOrder wo = woDAO.findByWoNumber(dto.getWoNumber());
            if(wo == null) {
                log.info("Cannot updated WO {} because it doesn't exist", dto.getWoNumber());
            }else{
                boolean upd = updService.updateAfterRules(dto,wo);
                log.info("WO {} updated after applied rules: {}", dto.getWoNumber(), upd);
            }
        }catch (Exception e) {
            log.error("Failed to update WO after running rules \n" + e.getMessage());
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
