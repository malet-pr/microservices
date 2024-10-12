package acme.example.work_order.integration;

import jakarta.transaction.Transactional;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.workorderjob.internal.WorkOrderJobDAO;
import acme.example.work_order.workorderjob.internal.WorkOrderJobServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Sql(scripts = "/sql/woTestData.sql")
public class WorkOrderJobServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private WorkOrderJobServiceImpl service;

    @Autowired
    private WorkOrderJobDAO woJobDAO;

    @Test
    @DisplayName("Tests if an entity is saved in the database when the parent entity exists")
    void saveTest_woExists() {
        // Arrange
        WorkOrderJobDTO woJobDTO = WorkOrderJobDTO.builder()
                .woNumber("ZZZ999")
                .jobCode("jobCode1")
                .quantity(4)
                .activeStatus('Y')
                .appliedRule("A1")
                .build();
        // Act
        boolean saved = service.save(woJobDTO);
        List<WorkOrderJob> list = woJobDAO.findByWorkOrderNumber("ZZZ999");
        Optional<WorkOrderJob> entity = woJobDAO.findById(10L);
        // Assert
        Assertions.assertTrue(saved, "The entity should have been saved successfully.");
        Assertions.assertFalse(list.isEmpty(), "There should be at least one object in the list");
        Assertions.assertTrue(entity.isPresent(), "The entity should have been saved with id = 10L.");
        Assertions.assertNotNull(entity.get().getCreationDate(), "Creation date should not be null.");
        Assertions.assertEquals(4,entity.get().getQuantity(), "Entity quantity does not match.");
        Assertions.assertEquals('Y',entity.get().getActiveStatus(), "Entity active status does not match.");
        Assertions.assertEquals("A1",entity.get().getAppliedRule(), "Entity applied rule does not match.");
    }

    @Test
    @DisplayName("Tests no entity is saved in the database when the parent entity doesn't exists")
    void saveTest_woDoesNotExists() {
        // Arrange
        WorkOrderJobDTO woJobDTO = WorkOrderJobDTO.builder()
                .woNumber("non-existent")
                .jobCode("jobCode1")
                .quantity(4)
                .activeStatus('Y')
                .appliedRule("A1")
                .build();
        // Act
        boolean saved = service.save(woJobDTO);
        // Assert
        Assertions.assertFalse(saved, "The entity should not have been saved because it`s parent doesn't exist.");
    }

    @Test
    @DisplayName("Tests a dto is returned when searching by existing id")
    void findByIdTest_idExists() {
        // Arrange
        // Act
        WorkOrderJobDTO woJobDTO = service.findById(1L);
        // Assert
        Assertions.assertNotNull(woJobDTO,"There should be a work order job dto returned when searching by existing id");
        Assertions.assertEquals("ABC123",woJobDTO.getWoNumber(),"wo number does not match.");
        Assertions.assertEquals("JobCode1",woJobDTO.getJobCode(),"job code does not match.");
        Assertions.assertEquals(5,woJobDTO.getQuantity(),"quantity does not match.");
        Assertions.assertEquals('Y',woJobDTO.getActiveStatus(),"active status does not match.");
        Assertions.assertEquals("",woJobDTO.getAppliedRule(),"applied rule does not match.");
    }

    @Test
    @DisplayName("Tests null is returned when searching by a non-existing id")
    void findByIdTest_idNotExists() {
        // Arrange
        // Act
        WorkOrderJobDTO woJobDTO = service.findById(100L);
        // Assert
        Assertions.assertNull(woJobDTO,"No DTO should be returned when searching by a non-existing id");
    }

    @Test
    @DisplayName("Tests a list of dtos is returned when searching by at least one existing id")
    void findByIdsTest_someIdExist() {
        // Arrange
        // Act
        List<WorkOrderJobDTO> dtos = service.findByIds(List.of(1L,3L,20L,30L));
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a list");
        Assertions.assertFalse(dtos.isEmpty(), "There should be at least one object in the list");
        Assertions.assertEquals(2,dtos.size(),"There should be two objects in the list");
        Assertions.assertTrue(dtos.stream().map(WorkOrderJobDTO::getWoNumber).toList().containsAll(List.of("ABC123","ABC456")),
                "The list of wo numbers should contain ABC123 and ABC456");
    }

    @Test
    @DisplayName("Tests an empty list is returned when searching by a list of non-existing ids")
    void findByIdsTest_noneIdExist() {
        // Arrange
        // Act
        List<WorkOrderJobDTO> dtos = service.findByIds(List.of(20L, 30L,50L));
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a list");
        Assertions.assertTrue(dtos.isEmpty(), "There should be no objects in the list");
    }

    @Test
    @DisplayName("Tests a list of dtos is returned when searching by at least one existing code")
    void findByCodesTest_someCodesExist() {
        // Arrange
        // Act
        List<WorkOrderJobDTO> dtos = service.findByCodes(List.of("JobCode1","none"));
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a list");
        Assertions.assertFalse(dtos.isEmpty(), "There should be at least one object in the list");
        Assertions.assertEquals(2,dtos.size(),"There should be one object in the list");
        Assertions.assertTrue(dtos.stream().map(WorkOrderJobDTO::getWoNumber).toList().contains("ABC123"),
                "The list of wo numbers should contain only ABC123");
    }

    @Test
    @DisplayName("Tests an empty list is returned when searching by a list of non-existing codes")
    void findByCodesTest_noCodeExist() {
        // Arrange
        // Act
        List<WorkOrderJobDTO> dtos = service.findByCodes(List.of("some-fruit","non-existing"));
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a list");
        Assertions.assertTrue(dtos.isEmpty(), "There should be no objects in the list");
    }
}

