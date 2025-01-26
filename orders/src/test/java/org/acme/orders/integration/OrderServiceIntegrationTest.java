package org.acme.orders.integration;

import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.internal.Order;
import org.acme.orders.order.internal.OrderDAO;
import org.acme.orders.order.internal.OrderServiceImpl;
import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.rabbitmq.RabbitMessage;
import org.acme.orders.rabbitmq.RabbitMessageSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@Transactional
@Sql(scripts = "/sql/woTestData.sql")
public class OrderServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private OrderServiceImpl service;

    @Autowired
    private OrderDAO woDAO;

    @Test
    @DisplayName("Test if an entity is saved in the database")
    void saveTest() {
        // Arrange
        OrderJobDTO woJobDTO1 = OrderJobDTO.builder().quantity(1).activeStatus('N').jobCode("JobCode1").appliedRule("").build();
        OrderJobDTO woJobDTO2 = OrderJobDTO.builder().quantity(1).activeStatus('Y').jobCode("JobCode2").appliedRule("").build();
        OrderDTO woDTO = OrderDTO.builder()
                .woNumber("testNumber")
                .jobType("type1")
                .woJobDTOs(Arrays.asList(woJobDTO1, woJobDTO2))
                .woCreationDate(LocalDateTime.now().minusDays(3))
                .woCompletionDate(LocalDateTime.now().minusHours(4))
                .address("address1")
                .city("city1")
                .state("state1")
                .clientId("client1")
                .build();
        // Act
        boolean saved = service.save(woDTO);
        Order entity = woDAO.findByWoNumber("testNumber");
        // Assert
        //Assertions.assertTrue(saved, "The entity should have been saved successfully.");
        Assertions.assertNotNull(entity, "Entity should not be null after saving.");
        Assertions.assertNotNull(entity.getId(), "Entity ID should be generated.");
        Assertions.assertNotNull(entity.getCreationDate(), "Creation date should not be null.");
        Assertions.assertEquals("type1", entity.getJobType().getCode(), "Entity code does not match.");
        Assertions.assertEquals("testNumber", entity.getWoNumber(), "Entity WO number does not match.");
        Assertions.assertEquals(Arrays.asList("JobCode1","JobCode2"),entity.getJobs().stream().map(j -> j.getJob().getCode()).toList(), "Entity job codes don't match.");
    }

    @Test
    @DisplayName("Test if a dto is returned when searching by existing id")
    void findByIdTest_found(){
        // Arrange
        // Act
        OrderDTO dto = service.findById(1L);
        // Assert
        Assertions.assertNotNull(dto, "The dto should not be null after searching by existing id.");
        Assertions.assertEquals("ABC123",dto.getWoNumber(), "The WO number does not match.");
        Assertions.assertEquals("type1",dto.getJobType(),"The job type id does not match.");
    }

    @Test
    @DisplayName("Test if null is returned when searching by non-existing id")
    void findByIdTest_notFound(){
        // Arrange
        OrderDTO dto = service.findById(10L);
        // Assert
        Assertions.assertNull(dto, "The dto should be null after searching by a non-existing id.");
    }

    @Test
    @DisplayName("Test if a dto is returned when searching by existing order number")
    void findByWoNumberTest_found(){
        // Arrange
        // Act
        OrderDTO dto = service.findByWoNumber("ABC123");
        // Assert
        Assertions.assertNotNull(dto, "The dto should not be null after searching by existing order number.");
        Assertions.assertEquals(2,dto.getWoJobDTOs().size(), "The size of the WO job list does not match.");
        Assertions.assertEquals("type1",dto.getJobType(),"The job type id does not match.");
    }

    @Test
    @DisplayName("Test if null is returned when searching by non-existing order number")
    void findByWoNumberTest_notFound(){
        // Arrange
        OrderDTO dto = service.findByWoNumber("non-existing");
        // Assert
        Assertions.assertNull(dto, "The dto should be null after searching by a non-existing order number.");
    }

}
