package org.acme.rules.rabbitmq;

import org.acme.rules.drools.MessageToWoData;
import org.acme.rules.drools.WorkOrderData;
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
        System.out.println("Received: " + message);
    }

    @RabbitListener(queues = "work-order-queue")
    public void receiveWorkOrder(String message) {
        log.info("Received: {}", message);
        WorkOrderData data = msgToData.readRabbitMessage(message);
    }

}