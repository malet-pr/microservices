package org.acme.orders.kafka;

import org.acme.orders.rabbitmq.RabbitMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private RabbitMessageSender rabbit;

    @KafkaListener(topics = "new-wo", groupId = "wo-group-2")
    public void listen(@Payload String message) {
        log.info("Received message: {}", message);
        log.info("Sending message via RabbitMQ");
        rabbit.sendWorkOrder("work-order-queue",message);
    }

}
