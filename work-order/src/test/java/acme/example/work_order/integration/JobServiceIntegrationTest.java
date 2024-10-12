package acme.example.work_order.integration;

import acme.example.work_order.job.internal.Job;
import acme.example.work_order.job.internal.JobDAO;
import acme.example.work_order.job.internal.JobMapper;
import acme.example.work_order.job.internal.JobServiceImpl;
import jakarta.transaction.Transactional;
import acme.example.work_order.job.JobDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@Sql(scripts = {"/sql/jobTestData.sql"})
public class JobServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private JobServiceImpl service;

    @Autowired
    private JobDAO jobDAO;

    @Autowired
    JobMapper jobMapper = new JobMapper();

    @Test
    @DisplayName("Test if an entity is saved in the database")
    void saveTest() {
        // Arrange
        JobDTO jobDTO = JobDTO.builder()
                .name("test1")
                .code("code1")
                .activeStatus('Y')
                .build();
        // Act
        boolean saved = service.save(jobDTO);
        Job entity = jobDAO.findByCode("code1");
        // Assert
        Assertions.assertTrue(saved, "The entity should have been saved successfully.");
        Assertions.assertNotNull(entity, "Entity should not be null after saving.");
        Assertions.assertNotNull(entity.getId(), "Entity ID should be generated.");
        Assertions.assertNotNull(entity.getCreationDate(), "Creation date should not be null.");
        Assertions.assertEquals("code1", entity.getCode(), "Entity code does not match.");
        Assertions.assertEquals("test1", entity.getName(), "Entity name does not match.");
        Assertions.assertEquals('Y', entity.getActiveStatus(), "Entity active status does not match.");
    }

    @Test
    @DisplayName("Test if a dto will be returned when searching by an existing id")
    void findByIdTest_found() {
        // Arrange
        // Act
        JobDTO dto = service.findById(1L);
        // Assert
        Assertions.assertNotNull(dto, "The DTO should not be null after searching by an existing id.");
        Assertions.assertEquals("JobCode1", dto.getCode(), "DTO code does not match.");
        Assertions.assertEquals("job name 1", dto.getName(), "DTO name does not match.");
        Assertions.assertEquals('Y', dto.getActiveStatus(), "DTO active status does not match.");
    }

    @Test
    @DisplayName("Test if null will be returned when searching by a non existing id")
    void findByIdTest_notFound() {
        // Arrange
        // Act
        JobDTO dto = service.findById(10L);
        // Assert
        Assertions.assertNull(dto, "The DTO should be null after searching by a non existing id.");
    }

    @Test
    @DisplayName("Test if the status will be returned when searching by an existing id")
    void getActiveStatusById_found() {
        // Arrange
        // Act
        Character status = service.getActiveStatusById(1L);
        // Assert
        Assertions.assertNotNull(status, "The status should not be null after searching by an existing id.");
        Assertions.assertEquals('Y', status, "Active status does not match.");
    }

    @Test
    @DisplayName("Test if null will be returned when searching the status by a non existing id")
    void getActiveStatusById_notFound() {
        // Arrange
        // Act
        Character status = service.getActiveStatusById(10L);
        // Assert
        Assertions.assertNull(status, "The status should be null after searching by a non existing id.");
    }

    @Test
    @DisplayName("Test if the name will be returned when searching by an existing id")
    void getNameById_found() {
        // Arrange
        // Act
        String name = service.getNameByJob(1L);
        // Assert
        Assertions.assertNotNull(name, "The name should not be null after searching by an existing id.");
        Assertions.assertEquals("job name 1", name, "Name does not match.");
    }

    @Test
    @DisplayName("Test if null will be returned when searching the name by a non existing id")
    void getNameById_notFound() {
        // Arrange
        // Act
        String name = service.getNameByJob(10L);
        // Assert
        Assertions.assertEquals("", name, "An empty string should be returned when searching the name by a non existing id.");
    }

    @Test
    @DisplayName("Test if a dto will be returned when searching by an existing code")
    void findByCode_found() {
        // Arrange
        // Act
        JobDTO dto = service.findByCode("JobCode1");
        // Assert
        Assertions.assertNotNull(dto, "The DTO should not be null after searching by an existing code.");
        Assertions.assertEquals("JobCode1", dto.getCode(), "DTO code does not match.");
        Assertions.assertEquals("job name 1", dto.getName(), "DTO name does not match.");
        Assertions.assertEquals('Y', dto.getActiveStatus(), "DTO active status does not match.");
    }

    @Test
    @DisplayName("Test if null will be returned when searching by a non existing code")
    void findByCode_notFound() {
        // Arrange
        // Act
        JobDTO dto = service.findByCode("not-exist");
        // Assert
        Assertions.assertNull(dto, "The DTO should be null after searching by a non existing id.");
    }

    @Test
    @DisplayName("Test if a dto will be returned when searching by an existing combination of code and active status")
    void findByCodeAndActiveStatus_found() {
        // Arrange
        // Act
        JobDTO dto = service.findByCodeAndActiveStatus("JobCode3",'N');
        // Assert
        Assertions.assertNotNull(dto, "The DTO should not be null after searching by an existing code and active status.");
        Assertions.assertEquals("JobCode3", dto.getCode(), "DTO code does not match.");
        Assertions.assertEquals("job name 3", dto.getName(), "DTO name does not match.");
        Assertions.assertEquals('N', dto.getActiveStatus(), "DTO active status does not match.");
    }

    @Test
    @DisplayName("Test if null will be returned when searching by a wrong code, right active status")
    void findByCodeAndActiveStatus_notFoundCode() {
        // Arrange
        // Act
        JobDTO dto = service.findByCodeAndActiveStatus("not-exist",'N');
        // Assert
        Assertions.assertNull(dto, "The DTO should be null after searching by a wrong code, right active status.");
    }

    @Test
    @DisplayName("Test if null will be returned when searching by a right code, wrong active status")
    void findByCodeAndActiveStatus_foundCode_wrongStatus() {
        // Arrange
        // Act
        JobDTO dto = service.findByCodeAndActiveStatus("JobCode3",'Y');
        // Assert
        Assertions.assertNull(dto, "The DTO should be null after searching by a right code, wrong active status.");
    }

    @Test
    @DisplayName("Test if the right list will be returned when searching by a list of codes, some of them existing")
    void findByCodes_foundSome() {
        // Arrange
        List<Job> jobs = jobDAO.findByCodes(List.of("JobCode1","JobCode2"));
        List<JobDTO> dtoList = jobs.stream().map(jobMapper::convertToDto).toList();
        // Act
        List<JobDTO> dtos = service.findByCodes(Arrays.asList("JobCode1","JobCode2","non-existing"));
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a List");
        Assertions.assertEquals(2,dtos.size(), "The size of the two lists did not match");
        Assertions.assertEquals(dtoList,dtos,"The method should return a List with given dtos");
    }

    @Test
    @DisplayName("Test if an empty list will be returned when searching by a list of non-existing codes")
    void findByCodes_noneFound() {
        // Arrange
        // Act
        List<JobDTO> dtos = service.findByCodes(Arrays.asList("bad-code","non-existing"));
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a List");
        Assertions.assertEquals(0,dtos.size(), "The method should return an empty list");
    }

    @Test
    @DisplayName("Test if the right list will be returned when searching with at least one existing combination of code and status")
    void findByCodesAndActiveStatus_someFound() {
        // Arrange
        // Act
        List<JobDTO> dtos = service.findByCodesAndActiveStatus(Arrays.asList("JobCode1","JobCode2","JobCode3"),'Y');
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a List");
        Assertions.assertEquals(2,dtos.size(), "The size of the two lists did not match");
        Assertions.assertEquals("JobCode1",dtos.get(0).getCode() ,"DTO code of first item does not match.");
        Assertions.assertEquals("JobCode2",dtos.get(1).getCode() ,"DTO code of second item does not match.");
        Assertions.assertEquals('Y', dtos.get(0).getActiveStatus(), "DTO active status of first item does not match.");
        Assertions.assertEquals('Y', dtos.get(1).getActiveStatus(), "DTO active status of second item does not match.");
    }

    @Test
    @DisplayName("Test if an empty list will be returned when searching by some existing codes with the wrong active status")
    void findByCodesAndActiveStatus_noneFoundWithThatStatus() {
        // Arrange
        // Act
        List<JobDTO> dtos = service.findByCodesAndActiveStatus(Arrays.asList("JobCode1","JobCode2"),'N');
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a List");
        Assertions.assertEquals(0,dtos.size(), "The list should be empty");
    }

    @Test
    @DisplayName("Test if an empty list will be returned when searching by all non-existing codes")
    void findByCodesAndActiveStatus_noneFoundWithThoseCodes() {
        // Arrange
        // Act
        List<JobDTO> dtos = service.findByCodesAndActiveStatus(Arrays.asList("not-exist","bad-code"),'N');
        // Assert
        Assertions.assertInstanceOf(List.class,dtos,"The method should return a List");
        Assertions.assertEquals(0,dtos.size(), "The list should be empty");
    }


}
