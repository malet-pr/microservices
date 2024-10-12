package org.acme.simulator.api;


import org.acme.simulator.kafkaproducer.MessageProducer;
import org.acme.simulator.simulations.Simulations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private Simulations simu;

    private static final Logger log = LoggerFactory.getLogger(KafkaController.class);

    @PostMapping("/send")
    public void sendMessage(@RequestParam("quantity") int quantity, @RequestParam("topic") String topic) {
        log.info("Sending {} messages to {}", quantity,topic);
        String message = simu.prepareKafkaMessages(quantity);
        messageProducer.sendMessage(topic, message);
    }

}