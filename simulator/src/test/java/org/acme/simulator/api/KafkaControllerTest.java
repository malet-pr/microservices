package org.acme.simulator.api;

import nl.altindag.log.LogCaptor;
import org.acme.simulator.kafkaproducer.MessageProducer;
import org.acme.simulator.simulations.Simulations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class KafkaControllerTest {

    LogCaptor logCaptor;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageProducer producerMock;

    @MockBean
    private Simulations simuMock;

    @InjectMocks
    private KafkaController controller;

    @BeforeEach
    void beforeEach() {
        logCaptor = LogCaptor.forClass(KafkaController.class);
    }

    @Test
    @DisplayName("Test endpoint for sending orders")
    void sendMessage() throws Exception {
        // Arrange
        Mockito.when(simuMock.prepareKafkaMessages(anyInt())).thenReturn("orders");
        // Act
        mockMvc.perform(get("/send")
                        .param("topic", "topic")
                        .param("quantity",String.valueOf(5))
                )
                .andExpect(status().isOk());
        // Assert
        Mockito.verify(simuMock,Mockito.times(1)).prepareKafkaMessages(5);
        Mockito.verify(producerMock,Mockito.times(1)).sendMessage("topic","orders");
        Assertions.assertTrue(logCaptor.getInfoLogs().contains("Sending 5 messages to topic"));
    }

    @Test
    @DisplayName("Test testing endpoint")
    void testEndpoint() throws Exception {
        // Arrange
        // Act
        mockMvc.perform(get("/test")
                        .param("topic", "topic")
                )
                .andExpect(status().isOk());
        // Assert
        Mockito.verify(producerMock,Mockito.times(1)).sendMessage("topic","Hello World");
        Assertions.assertTrue(logCaptor.getInfoLogs().contains("Sending a message to topic"));
    }

}

// Add bad request tests