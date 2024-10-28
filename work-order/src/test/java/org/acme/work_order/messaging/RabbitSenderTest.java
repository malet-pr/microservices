package org.acme.work_order.messaging;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.work_order.common.LocalDateTimeTypeAdapter;
import org.acme.work_order.rabbitmq.RabbitMessageSender;
import org.acme.work_order.workorder.internal.WorkOrder;
import org.acme.work_order.workorder.internal.WorkOrderDAO;
import org.acme.work_order.workorder.internal.WorkOrderMapper;
import org.acme.work_order.workorder.internal.WorkOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RabbitSenderTest extends BaseRabbitTest{

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private RabbitMessageSender msgSender;

    @Mock
    private RabbitMessageSender msgMock;

    @Mock
    private WorkOrderMapper woMapperMock;

    @Mock
    private WorkOrderDAO woDAOMock;

    @InjectMocks
    private WorkOrderServiceImpl woService;

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();
/*
    @Test
    @DisplayName("Tests that a message is sent when the method is invoked with any string and any queue")
    public void sendWorkOrderTest_success() throws Exception {
        // Arrange
        String queueName = "test";
        String message = "test message";
        // Act
        msgSender.sendMessage(queueName, message);
        Object received = template.receiveAndConvert(queueName);
        // Assert
        Assertions.assertNotNull(received,"No message received");
        Assertions.assertEquals(message, received.toString(), "Messages did not match");
    }
*/
    @Test
    @DisplayName("Test that when a WO is saved, a message is sent to the appropriate queue with the serialized WO")
    public void sendMessageAfterSaveWorkOrderTest_success() throws Exception {
        // Arrange
        String queueName = "work-order-queue";
        String json = gson.toJson(DTOs.dto2);
        WorkOrder mockWorkOrder = WorkOrder.builder().woNumber("ABC123").build();
        mockWorkOrder.setJobs(new ArrayList<>());
        Mockito.when(woMapperMock.convertToEntity(DTOs.dto2)).thenReturn(mockWorkOrder);
        Mockito.when(woDAOMock.existsByWoNumber("ABC123")).thenReturn(false);
        Mockito.when(woDAOMock.save(mockWorkOrder)).thenReturn(mockWorkOrder);
        // Act
        woService.save(DTOs.dto2);
        // Assert
        verify(msgMock).sendWorkOrder(queueName, json);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(msgMock).sendWorkOrder(eq(queueName), messageCaptor.capture());
        String sentMessage = messageCaptor.getValue();
        Assertions.assertEquals(json, sentMessage, "The sent message does not match the expected message.");
    }

    @Mock
    private Logger log;

    @Test
    @DisplayName("Test that saving a Work Order succeeds even when RabbitMQ fails")
    public void sendMessageAfterSaveWorkOrderTest_rabbitFailure() {
        // Arrange
        String queueName = "work-order-queue";
        String json = gson.toJson(DTOs.dto2);
        WorkOrder mockWorkOrder = WorkOrder.builder().woNumber("ABC123").build();
        mockWorkOrder.setJobs(new ArrayList<>());
        Mockito.when(woMapperMock.convertToEntity(DTOs.dto2)).thenReturn(mockWorkOrder);
        Mockito.when(woDAOMock.existsByWoNumber(anyString())).thenReturn(false);
        Mockito.when(woDAOMock.save(mockWorkOrder)).thenReturn(mockWorkOrder);
        Mockito.doThrow(new RuntimeException("RabbitMQ not reachable")).when(msgMock)
                                                .sendWorkOrder(anyString(), anyString());
        // Act
        try{
            woService.save(DTOs.dto2);
        }catch (Exception e) {
            Assertions.fail("Exception was thrown: " + e.getMessage());
        }
        // Assert
        verify(woDAOMock).save(mockWorkOrder);
        verify(msgMock).sendWorkOrder(queueName, json);

    }

}
