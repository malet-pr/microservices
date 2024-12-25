package org.acme.simulator.simulations;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.simulator.api.KafkaController;
import org.acme.simulator.simulations.internal.LocalDateTimeTypeAdapter;
import org.acme.simulator.simulations.internal.Order;
import org.acme.simulator.simulations.internal.OrderSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Simulations {

    private static final Logger log = LoggerFactory.getLogger(KafkaController.class);
    private final OrderSimulator simu;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();
    Map<String,String> lastOrders = new HashMap<>();

    public Simulations(OrderSimulator simu) {
        this.simu = simu;
    }

    public List<Order> simulateWorkOrders(int quantity){
        List<Order> woList = new ArrayList<>();
        log.info("Simulating {} work orders ... ", quantity);
        int q1 = (int) Math.floor(quantity*0.55);
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

    private String getOrderNumber(String source){
        if(!lastOrders.containsKey(source)){
            initLastOrdersBySource(source);
        }
        long num = Long.parseLong(lastOrders.get(source).split("-")[1]);
        String woNum = lastOrders.get(source).split("-")[0]
                .concat("-").concat(Strings.padStart(String.valueOf(num+1), 10, '0'));
        lastOrders.put(source, woNum);
        return woNum;
    }

    private void initLastOrdersBySource(String source){
        if(!lastOrders.containsKey(source)){
            switch (source){
                case "S1" -> lastOrders.put(source, "J-0000000000");
                case "S2" -> lastOrders.put(source, "L-0000000000");
                case "S3" -> lastOrders.put(source, "W-0000000000");
                default -> throw new IllegalStateException("Unexpected value: " + source);
            }
        }
    }

    public String convertToJsonArray(List<Order> orders) {
        return gson.toJson(orders);
    }

    public String prepareKafkaMessages(int quantity) {
        List<Order> list = simulateWorkOrders(quantity);
        return convertToJsonArray(list);
    }

}

enum Source {
    S1,S2,S3;
}
