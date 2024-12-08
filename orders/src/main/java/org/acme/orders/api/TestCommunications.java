package org.acme.orders.api;

import org.acme.orders.rabbitmq.RabbitMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestCommunications {

    @Autowired
    private RabbitMessageSender msgSender;

    @PostMapping("/rabbitmq")
    public String sendMsg(@RequestParam String message) {
        msgSender.sendMessage("test-queue", message);
        return "Message sent!";
    }


}
