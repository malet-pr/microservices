package org.acme.simulator.simulations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.simulator.simulations.internal.LocalDateTimeTypeAdapter;
import org.acme.simulator.simulations.internal.Order;
import org.acme.simulator.simulations.internal.OrderSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Simulations {

    private static final Logger log = LoggerFactory.getLogger(Simulations.class);

    private OrderSimulator simu;
    private RedisTemplate<String, String> redisTemplate;

    public Simulations(OrderSimulator simu, RedisTemplate<String, String> redisTemplate) {
        this.simu = simu;
        this.redisTemplate = redisTemplate;
    }

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    public List<Order> simulateWorkOrders(int quantity){
        List<Order> woList = new ArrayList<>();
        log.info("Simulating {} work orders ... ", quantity);
        int q1 = (int) Math.ceil(quantity*0.50);
        int q2 = (int) Math.floor(quantity*0.35);
        int q3 = quantity-q1-q2;
        while(q1 > 0){
            woList.add(simu.simulate(getOrderNumber(Source.S1.name())));
            q1--;
        }
        while(q2 > 0){
            woList.add(simu.simulate(getOrderNumber(Source.S2.name())));
            q2--;
        }
        while(q3 > 0){
            woList.add(simu.simulate(getOrderNumber(Source.S3.name())));
            q3--;
        }
        return woList;
    }

    public String getOrderNumber(String source){
        String lastOrders;
        try {
            lastOrders = redisTemplate.opsForValue().get(source);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis is unavailable", e);
            throw new RuntimeException("Redis is unavailable", e);
        } catch (Exception e) {
            log.error("Unexpected error while accessing Redis", e);
            throw new RuntimeException("Unexpected error while accessing Redis", e);
        }
        if (lastOrders == null) {
            log.warn("Key '{}' does not exist in Redis", source);
            throw new RuntimeException("Key does not exist in Redis");
        }
        long num = Long.parseLong(lastOrders.split("-")[1]);
        String newOrder = lastOrders.split("-")[0]
                .concat("-")
                .concat(String.format("%010d", num + 1));
        redisTemplate.opsForValue().set(source, newOrder);
        return newOrder;
    }

    public String prepareKafkaMessages(int quantity) {
        List<Order> list = simulateWorkOrders(quantity);
        return gson.toJson(list);
    }

}

enum Source {
    S1,S2,S3;
}
