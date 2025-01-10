package org.acme.orders.order.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.orders.common.LocalDateTimeTypeAdapter;
import org.acme.orders.jobtype.internal.JobType;
import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.OrderService;
import org.acme.orders.orderjob.UpdatesService;
import org.acme.orders.rabbitmq.RabbitMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderMapper woMapper;
    @Autowired
    private OrderDAO woDAO;
    @Autowired
    private UpdatesService updService;
    @Autowired
    private RabbitMessageSender msgSender;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    @Override
    public Boolean save(OrderDTO dto) {
        boolean result = Boolean.FALSE;
        Order entity = woMapper.convertToEntity(dto);
        if(entity == null) {return false;}
        if(Boolean.TRUE.equals(woDAO.findByWoNumber(entity.getWoNumber()))) {
            log.error("A work order with number {} already exists", entity.getWoNumber());
            return result;
        }
        try {
            entity.getJobs().forEach(j -> j.setOrder(entity));
            Order order = woDAO.save(entity);
            String json = gson.toJson(dto); //TODO: revisit data sent to rules service
            msgSender.sendWorkOrder("work-order-queue",json);
            log.info("Successfully saved work order {}", order);
            result = Boolean.TRUE;
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Boolean updateAfterRules(OrderDTO dto) {
        boolean result = Boolean.FALSE;
        try{
            Order wo = woDAO.findByWoNumber(dto.getWoNumber());
            if(wo == null) {
                log.info("Cannot updated WO {} because it doesn't exist", dto.getWoNumber());
                return result;
            }else{
                result = updService.updateAfterRules(dto,wo);
                log.info("WO {} updated after applied rules: {}", dto.getWoNumber(),result);
            }
        }catch (Exception e) {
            log.error("Failed to update WO after running rules\nMessage: {}", e.getMessage());
        }
        return result;
    }

    @Override
    public OrderDTO findById(Long id) {
        return woMapper.convertToDTO(woDAO.findById(id).orElse(null));
    }

    @Override
    public OrderDTO findByWoNumber(String woNumber) {
        return woMapper.convertToDTO(woDAO.findByWoNumber(woNumber));
    }

}
