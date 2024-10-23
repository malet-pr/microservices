package org.acme.work_order.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestMessageSender {

    @Autowired
    private RabbitTemplate template;

    String queueName1 = "test-queue";

    public void sendMessage(String queueName1, String message) {
        template.convertAndSend(queueName1, message);
    }

    public void sendWorkOrder(String queueName, String message) {
        template.convertAndSend(queueName, message);
    }
}