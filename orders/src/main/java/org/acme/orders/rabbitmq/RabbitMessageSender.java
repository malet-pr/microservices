package org.acme.orders.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageSender {

    private static final Logger log = LoggerFactory.getLogger(RabbitMessageSender.class);

    @Autowired
    private RabbitTemplate template;

    public void sendMessage(String queueName, String message) {
        template.convertAndSend("test-queue", message);
    }

    public void sendWorkOrder(String queueName, String message) {
        template.convertAndSend("work-order-queue", message);
    }

}