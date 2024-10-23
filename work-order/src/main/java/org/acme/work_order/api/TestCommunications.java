package org.acme.work_order.api;

import org.acme.work_order.rabbitmq.TestMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestCommunications {

    @Autowired
    private TestMessageSender msgSender;

    @PostMapping("/rabbitmq")
    public String sendMsg(@RequestParam String message) {
        msgSender.sendMessage("test-queue", message);
        return "Message sent!";
    }


}
