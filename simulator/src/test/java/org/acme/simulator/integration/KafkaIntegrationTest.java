package org.acme.simulator.integration;

import org.acme.simulator.kafkaproducer.MessageProducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaIntegrationTest extends BaseKafkaTest{

    @Autowired
    public KafkaTemplate<String, String> template;

    @MockBean
    private KafkaTemplate<String, String> mockTemplate;

    @InjectMocks
    public MessageProducer messageProducer;

    private static final String topic = "wo-new";

    @Test
    @DisplayName("Test sending a message with orders to the right topic")
    void sendMessageTest() {
        // Arrange
        String msg = "List of orders";
        // Act
        template.send(topic, msg);
        // Assert
        Mockito.verify(mockTemplate,Mockito.times(1)).send(topic, msg);
    }


}
