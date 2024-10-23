package org.acme.rules.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestMessageReceiver {

    @RabbitListener(queues = "test-queue")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }

    @RabbitListener(queues = "work-order-queue")
    public void recieveWorkOrder(String message) {
        System.out.println("Received: " + message);
    }

}