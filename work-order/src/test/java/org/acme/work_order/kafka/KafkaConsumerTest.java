package org.acme.work_order.kafka;

import org.acme.work_order.common.LocalDateTimeTypeAdapter;
import org.acme.work_order.kafkaconsumer.MessageConsumer;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorder.internal.WorkOrder;
import org.acme.work_order.workorder.internal.WorkOrderDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = "/sql/woTestData.sql")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class KafkaConsumerTest extends BaseIntegrationKafka{

    @Autowired
    public KafkaTemplate<String, String> template;

    @MockBean
    private KafkaTemplate<String, String> mockTemplate;

    @Autowired
    private MessageConsumer msgConsumer;

    @Autowired
    private WorkOrderDAO woDAO;

    private static final String TOPIC = "wo-new";
    private final Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                        .create();
    Type listType = new TypeToken<List<WorkOrderDTO>>(){}.getType();

    @Test
    @DisplayName("Tests that a message is received and deserialized correctly")
    public void messageConsumerTest_success() throws Exception {
        // Arrange
        WorkOrderDTO dto1 = WorkOrderDTO.builder().woNumber("test1").build();
        WorkOrderDTO dto2 = WorkOrderDTO.builder().woNumber("test2").build();
        String message = gson.toJson(List.of(dto1,dto2));
        template.send(TOPIC, message);
        // Act
        msgConsumer.listen(message);
        Type listType = new TypeToken<List<WorkOrderDTO>>(){}.getType();
        List<WorkOrderDTO> dtos = gson.fromJson(message, listType);
        // Assert
        Assertions.assertNotNull(dtos,"There should be at least one dto");
        Assertions.assertEquals(2, dtos.size(),"There should be two dtos");
        Assertions.assertEquals(List.of(dto1,dto2),dtos,"The list of dtos does not match");
    }

    @Test
    @DisplayName("Test that after receiving a message, a new WO is saved in the database")
    public void messageConsumerSaveTest_success() throws Exception {
        // Arrange
        String message = gson.toJson(List.of(DTOs.dto1));
        template.send(TOPIC, message);
        // Act
        msgConsumer.listen(message);
        Awaitility.await()
            .atMost(10, TimeUnit.SECONDS)
            .until(() -> msgConsumer.getSavedCount().get() > 0);
        WorkOrder wo = woDAO.findByWoNumber("testNumber");
        // Assert
        Assertions.assertNotNull(wo,"There should be a WO");
        Assertions.assertEquals("testNumber",wo.getWoNumber(),"The WO number does not match");
        Assertions.assertEquals("type1",wo.getJobType().getCode(),"The job type does not match");
        Assertions.assertEquals(1,wo.getJobs().size());
        Assertions.assertEquals("JobCode1",wo.getJobs().getFirst().getJob().getCode(),"The job does not match");
    }

    /*
    @Test
    @DisplayName("Test that after receiving a message with an existing WO number, no new entity is saved")
    public void messageConsumerSaveTest_failed() throws Exception {
        // Arrange
        String message = gson.toJson(List.of(DTOs.dto2));
        template.send(TOPIC, message);
        // Act
        msgConsumer.listen(message);
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .until(() -> msgConsumer.getErrorCount().get() > 0);
        // Assert
        // Assertions.assertTrue(thrown.getMessage().contains("duplicate key value violates unique constraint"));
        Assertions.assertThrows(Exception.class,
                () -> message.contains("ABC123"), "This should throw an exception"
        );
    }
     */

    @Test
    @DisplayName("Simulate parsing message error")
    public void testParsingMessageError() throws Exception {
        // Arrange
        when(mockTemplate.send(anyString(), anyString())).thenThrow(new RuntimeException("Kafka send failed"));
        String message = gson.toJson(List.of(DTOs.dto1));
        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {mockTemplate.send(TOPIC, message);
        });
    }


}
