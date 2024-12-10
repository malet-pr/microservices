package org.acme.orders.orderjob.internal;

import org.acme.orders.order.internal.Order;
import org.acme.orders.order.internal.OrderDAO;
import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.orderjob.OrderJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderJobServiceImpl implements OrderJobService {

    private static final Logger log = LoggerFactory.getLogger(OrderJobServiceImpl.class);

    @Autowired
    OrderJobDAO woJobDAO;

    @Autowired
    OrderDAO woDAO;

    @Autowired
    OrderJobMapper woJobMapper;


    @Override
    public boolean save(OrderJobDTO wo) {
        Order woEntity = woDAO.findByWoNumber(wo.getWoNumber());
        if (woEntity == null) {
            System.out.println("Cannot save the order job because the order does not exist");
            return false;
        }
        try{
            OrderJob entity = woJobMapper.convertToEntity(wo);
            if(entity == null) return false;
            woJobDAO.save(entity);
            return true;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    // IMPLEMENT THIS
    @Override
    public int saveAll(List<OrderJobDTO> woJobs) {
        try{
            woJobs.forEach(this::save);
            return woJobs.size();
        } catch(Exception e){
            log.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public OrderJobDTO findById(Long id) {
        return woJobMapper.convertToDTO(woJobDAO.findById(id).orElse(null));
    }

    @Override
    public List<OrderJobDTO> findByIds(List<Long> ids) {
        return woJobMapper.convertListToDTO(woJobDAO.findAllById(ids));
    }

    @Override
    public List<OrderJobDTO> findByCodes(List<String> jobCodeList) {
        return woJobMapper.convertListToDTO(woJobDAO.findByCodes(jobCodeList));
    }
}
