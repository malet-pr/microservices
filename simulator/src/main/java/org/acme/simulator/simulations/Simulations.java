package org.acme.simulator.simulations;

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
import java.util.List;

@Service
public class Simulations {

    private static final Logger log = LoggerFactory.getLogger(KafkaController.class);
    private final OrderSimulator simu;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    public Simulations(OrderSimulator simu) {
        this.simu = simu;
    }

    public List<Order> simulateWorkOrders(int quantity){
        List<Order> woList = new ArrayList<>();
        log.info("Simulating {} work orders ... ", quantity);
        while(quantity > 0){
            woList.add(simu.simulate("S1"));  // change this
            quantity--;
        }
        return woList;
    }

    public String convertToJsonArray(List<Order> orders) {
        String json = gson.toJson(orders);
        return json;
    }

    public String prepareKafkaMessages(int quantity) {
        List<Order> list = simulateWorkOrders(quantity);
        return convertToJsonArray(list);
    }

}
