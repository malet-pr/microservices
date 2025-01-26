package org.acme.orders.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.orders.common.LocalDateTimeTypeAdapter;
import org.acme.orders.order.internal.Order;
import org.acme.orders.order.internal.WoData;
import org.acme.orders.order.internal.WoJob;
import org.acme.orders.orderjob.internal.OrderJob;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitMessage {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    public String createMessage(Order order){
        List<OrderJob> jobs = order.getJobs();
        List<WoJob> woJobs = new ArrayList<>();
        jobs.forEach(j -> {
            woJobs.add(WoJob.builder()
                    .woNumber(order.getWoNumber())
                    .jobCode(j.getJob().getCode())
                    .quantity(j.getQuantity())
                    .activeStatus(j.getActiveStatus().toString())
                    .build());
        });
        WoData data = WoData.builder()
                .woNumber(order.getWoNumber())
                .woJobs(woJobs)
                .woCreationDate(order.getWoCreationDate())
                .woCompletionDate(order.getWoCompletionDate())
                .jobTypeCode(order.getJobType().getCode())
                .state(order.getState())
                .clientId(order.getClientId())
                .clientType("")
                .build();
        return gson.toJson(data);
    }


}
