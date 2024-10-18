package org.acme.work_order.workorder.internal;

import org.acme.work_order.grpc.*;
import org.acme.work_order.grpc.TestBack;
import org.acme.work_order.grpc.TestGo;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorder.WorkOrderService;
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
    private WorkOrderConsumer consumer;
    @Autowired
    private TestConnectionConsumer testConsumer;


    @Override
    public Boolean save(WorkOrderDTO dto) {
        boolean result = false;
        WorkOrder entity = woMapper.convertToEntity(dto);
        if(entity == null) {return false;}
        if(woDAO.existsByWoNumber(entity.getWoNumber())) {
            log.error("A work order with number {} already exists", entity.getWoNumber());
        }
        try {
            entity.getJobs().forEach(j -> j.setWorkOrder(entity));
            woDAO.save(entity);
            log.info("Successfully saved work order {}", entity);
            result = true;
            runRules(dto);
            // runTestConnection();
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    private void runRules(WorkOrderDTO dto) {
        try{
            WorkOrderDTO newDto = consumer.callRules(dto);
            if(newDto != null) {
                log.info("Successfully run rules for {}", newDto);
                save(newDto);
                log.info("Successfully saved work order {}", newDto);
            } else {
                log.error("Failed to save resulting dto {}", newDto);
            }
        }catch (Exception e) {
            log.error("Failed to run rules - " + e.getMessage());
        }
    }

    private void runTestConnection() {
        TestGo go = TestGo.newBuilder().setMsgOut("hello from work-order").build();
        TestBack resp = testConsumer.testConnection(go);
        log.info("Successfully run test connection to work-order");
        log.info("Result: {}", resp);
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