package org.acme.rules.rabbitmq;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.acme.rules.drools.MessageToWoData;
import org.acme.rules.grpc.woserviceconnect.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public void receiveOrder(String message) {
        log.info("Received: {}", message);
        JsonElement data = JsonParser.parseString(message);
        Order result = msgToData.readRabbitMessage(data);
        log.info("Result: {}", result.getWoNumber());
    }

}