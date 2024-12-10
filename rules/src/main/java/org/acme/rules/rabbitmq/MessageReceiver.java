package org.acme.rules.rabbitmq;

import com.google.gson.JsonArray;
<<<<<<< HEAD
=======
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
>>>>>>> develop
import com.google.gson.JsonParser;
import org.acme.rules.drools.MessageToWoData;
import org.acme.rules.drools.WorkOrderData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageReceiver {

    private static final Logger log = LoggerFactory.getLogger(MessageReceiver.class);

    @Autowired
    MessageToWoData msgToData;

    @RabbitListener(queues = "test-queue")
    public void receiveMessage(String message) {
        log.info("Received: {}", message);
    }

    // VOLVER A MODIFICAR PARA QUE RECIBA DE A UN MENSAJE
    @RabbitListener(queues = "work-order-queue")
    public void receiveWorkOrder(String message) {
        log.info("Received: {}", message);
<<<<<<< HEAD
        JsonArray data = (JsonArray) JsonParser.parseString(message);
        List<WorkOrderData> result = msgToData.readRabbitMessage(data);
        result.forEach(wo -> {
            log.info("Result:");
            log.info("WO Number: {}", wo.getWoNumber());
            log.info("Address: {}", wo.getAddress());
            log.info("State: {}",wo.getState());
        });
=======
        JsonElement data = JsonParser.parseString(message);
        WorkOrderData result = msgToData.readRabbitMessage(data);
        log.info("Result: {}", result.getWoNumber());
>>>>>>> develop

    }

}