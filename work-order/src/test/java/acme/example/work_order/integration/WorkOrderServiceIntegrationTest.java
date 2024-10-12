package acme.example.work_order.integration;

import acme.example.work_order.workorder.WorkOrderDTO;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorder.internal.WorkOrder;
import acme.example.work_order.workorder.internal.WorkOrderDAO;
import acme.example.work_order.workorder.internal.WorkOrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest
@Transactional
@Sql(scripts = "/sql/woTestData.sql")
public class WorkOrderServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private WorkOrderServiceImpl service;

    @Autowired
    private WorkOrderDAO woDAO;


    @Test
    @DisplayName("Test if an entity is saved in the database")
    void saveTest() {
        // Arrange
        WorkOrderJobDTO woJobDTO1 = WorkOrderJobDTO.builder().jobCode("JobCode1").build();
        WorkOrderJobDTO woJobDTO2 = WorkOrderJobDTO.builder().jobCode("JobCode2").build();
        WorkOrderDTO woDTO = WorkOrderDTO.builder()
                .woNumber("testNumber")
                .jobTypeCode("type1")
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
        WorkOrder entity = woDAO.findByWoNumber("testNumber");
        // Assert
        Assertions.assertTrue(saved, "The entity should have been saved successfully.");
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
        WorkOrderDTO dto = service.findById(1L);
        // Assert
        Assertions.assertNotNull(dto, "The dto should not be null after searching by existing id.");
        Assertions.assertEquals("ABC123",dto.getWoNumber(), "The WO number does not match.");
        Assertions.assertEquals("type1",dto.getJobTypeCode(),"The job type id does not match.");
    }

    @Test
    @DisplayName("Test if null is returned when searching by non-existing id")
    void findByIdTest_notFound(){
        // Arrange
        WorkOrderDTO dto = service.findById(10L);
        // Assert
        Assertions.assertNull(dto, "The dto should be null after searching by a non-existing id.");
    }

    @Test
    @DisplayName("Test if a dto is returned when searching by existing work order number")
    void findByWoNumberTest_found(){
        // Arrange
        // Act
        WorkOrderDTO dto = service.findByWoNumber("ABC123");
        // Assert
        Assertions.assertNotNull(dto, "The dto should not be null after searching by existing work order number.");
        Assertions.assertEquals(2,dto.getWoJobDTOs().size(), "The size of the WO job list does not match.");
        Assertions.assertEquals("type1",dto.getJobTypeCode(),"The job type id does not match.");
    }

    @Test
    @DisplayName("Test if null is returned when searching by non-existing work order number")
    void findByWoNumberTest_notFound(){
        // Arrange
        WorkOrderDTO dto = service.findByWoNumber("non-existing");
        // Assert
        Assertions.assertNull(dto, "The dto should be null after searching by a non-existing work order number.");
    }

}
