package org.acme.simulator.simulations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.simulator.api.KafkaController;
import org.acme.simulator.simulations.internal.LocalDateTimeTypeAdapter;
import org.acme.simulator.simulations.internal.WorkOrder;
import org.acme.simulator.simulations.internal.WorkOrderSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Simulations {

    private static final Logger log = LoggerFactory.getLogger(KafkaController.class);
    private final WorkOrderSimulator simu;
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    public Simulations(WorkOrderSimulator simu) {
        this.simu = simu;
    }

    public List<WorkOrder> simulateWorkOrders(int quantity){
        List<WorkOrder> woList = new ArrayList<>();
        log.info("Simulating {} work orders ... ", quantity);
        while(quantity > 0){
            woList.add(simu.simulate());
            quantity--;
        }
        return woList;
    }

    public String convertToJsonArray(List<WorkOrder> workOrders) {
        String json = gson.toJson(workOrders);
        return json;
    }

    public String prepareKafkaMessages(int quantity) {
        List<WorkOrder> list = simulateWorkOrders(quantity);
        return convertToJsonArray(list);
    }

}
