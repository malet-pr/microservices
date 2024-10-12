package acme.example.work_order.unit;

import acme.example.work_order.job.JobDTO;
import acme.example.work_order.job.internal.Job;
import acme.example.work_order.job.internal.JobDAO;
import acme.example.work_order.job.internal.JobServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobServiceImplTest {

    @Mock
    JobDAO jobDAOMock;

    @InjectMocks
    JobServiceImpl service;

    @Test()
    @DisplayName("Test that a proper JobDTO will be returned when searching by id")
    public void findByIdTest(){
        // Arrange
        Long id = 1L;
        Job job = Job.builder().name("mockJob").code("mockCode").activeStatus(('N')).build();
        job.setId(id);
        JobDTO jobDTO = JobDTO.builder().name("mockJob").code("mockCode").activeStatus(('N')).build();
        when(jobDAOMock.findById(id)).thenReturn(Optional.of(job));
        // Act
        JobDTO result = service.findById(id);
        // Assert
        Assertions.assertEquals(jobDTO,result);
    }

    @Test()
    @DisplayName("Test that a proper entity will be saved from a DTO")
    public void saveTest(){
        // Arrange
        Job job = Job.builder().name("mockJob").code("mockCode").activeStatus(('N')).build();
        job.setId(1L);
        job.setCreationDate(new Date());
        JobDTO jobDTO = JobDTO.builder().name("mockJob").code("mockCode").activeStatus(('N')).build();
        // Act
        service.save(jobDTO);
        // Assert
        Mockito.verify(jobDAOMock,times(1)).save(job);
    }

    @Test()
    @DisplayName("Test that the name is returned when searching by id and the entity exists")
    public void getNameByJobTest_found(){
        // Arrange
        Long id = 1L;
        Job job = Job.builder().name("mockJob").code("mockCode").activeStatus(('Y')).build();
        job.setId(id);
        when(jobDAOMock.findById(id)).thenReturn(Optional.of(job));
        // Act
        String result = service.getNameByJob(id);
        // Assert
        Assertions.assertEquals("mockJob", result);
    }

    @Test()
    @DisplayName("Test that an empty string is returned when searching by id and the entity does not exists")
    public void getNameByJobTest_notFound(){
        // Arrange
        Long id = 1L;
        when(jobDAOMock.findById(id)).thenReturn(Optional.empty());
        // Act
        String result = service.getNameByJob(id);
        // Assert
        Assertions.assertEquals("", result);
    }

    @Test()
    @DisplayName("Test that the status is returned when searching by id and the entity exists")
    public void getActiveStatusById_found(){
        // Arrange
        Long id = 1L;
        Job job = Job.builder().name("mockJob").code("mockCode").activeStatus(('Y')).build();
        job.setId(id);
        when(jobDAOMock.findById(id)).thenReturn(Optional.of(job));
        // Act
        Character result = service.getActiveStatusById(id);
        // Assert
        Assertions.assertEquals('Y', result);
    }

    @Test()
    @DisplayName("Test that null is returned when searching by id and the entity does not exists")
    public void getActiveStatusById_notFound(){
        // Arrange
        Long id = 1L;
        when(jobDAOMock.findById(id)).thenReturn(Optional.empty());
        // Act
        Character result = service.getActiveStatusById(id);
        // Assert
        Assertions.assertNull(result);
    }

    @Test()
    @DisplayName("Test that a proper JobDTO will be returned when searching by code when it exists")
    public void findByCodeTest_found(){
        // Arrange
        String code = "mockCode";
        Job job = Job.builder().name("mockJob").code(code).activeStatus(('N')).build();
        JobDTO jobDTO = JobDTO.builder().name("mockJob").code("mockCode").activeStatus(('N')).build();
        when(jobDAOMock.findByCode(code)).thenReturn(job);
        // Act
        JobDTO result = service.findByCode(code);
        // Assert
        Assertions.assertEquals(jobDTO,result);
    }

    @Test()
    @DisplayName("Test that null will be returned when searching by code and it doesn't exists")
    public void findByCodeTest_notFound(){
        // Arrange
        String code = "mockCode";
        when(jobDAOMock.findByCode(code)).thenReturn(null);
        // Act
        JobDTO result = service.findByCode(code);
        // Assert
        Assertions.assertNull(result);
    }

    @Test()
    @DisplayName("Test that a list of DTOs will be returned when looking by a list of codes and some exist")
    public void findByCodesTest_found(){
        // Arrange
        String code1 = "mockCode1";
        String code2 = "mockCode2";
        String code3 = "mockCode3";
        Job job1 = Job.builder().name("mockJob1").code(code1).activeStatus('N').build();
        job1.setId(1L);
        Job job2 = Job.builder().name("mockJob2").code(code2).activeStatus('Y').build();
        job2.setId(2L);
        JobDTO dto1 = JobDTO.builder().name("mockJob1").code(code1).activeStatus('N').build();
        JobDTO dto2 = JobDTO.builder().name("mockJob2").code(code2).activeStatus('Y').build();
        when(jobDAOMock.findByCodes(Arrays.asList(code1,code2,code3))).thenReturn(Arrays.asList(job1,job2));
        // Act
        List<JobDTO> result = service.findByCodes(Arrays.asList(code1,code2,code3));
        // Assert
        Assertions.assertEquals(Arrays.asList(dto1,dto2),result);
    }

    @Test()
    @DisplayName("Test that an empty list will be returned when looking by a list of codes and none exists")
    public void findByCodesTest_notFound(){
        // Arrange
        String code1 = "mockCode1";
        String code2 = "mockCode2";
        String code3 = "mockCode3";
        when(jobDAOMock.findByCodes(Arrays.asList(code1,code2,code3))).thenReturn(new ArrayList<>());
        // Act
        List<JobDTO> result = service.findByCodes(Arrays.asList(code1,code2,code3));
        // Assert
        Assertions.assertEquals(new ArrayList<>(),result);
    }

    @Test()
    @DisplayName("Test that a proper JobDTO will be returned when searching by code and active when it exists")
    public void findByCodeAndActiveStatusTest_found(){
        // Arrange
        String code = "mockCode";
        Job job = Job.builder().name("mockJob").code(code).activeStatus(('Y')).build();
        JobDTO jobDTO = JobDTO.builder().name("mockJob").code("mockCode").activeStatus(('Y')).build();
        when(jobDAOMock.findByCodeAndActiveStatus(code,'Y')).thenReturn(job);
        // Act
        JobDTO result = service.findByCodeAndActiveStatus(code,'Y');
        // Assert
        Assertions.assertEquals(jobDTO,result);
    }

    @Test()
    @DisplayName("Test that null will be returned when searching by code and active and it doesn't exists")
    public void findByCodeAndActiveStatusTest_notFound(){
        // Arrange
        String code = "mockCode";
        when(jobDAOMock.findByCodeAndActiveStatus(code,'Y')).thenReturn(null);
        // Act
        JobDTO result = service.findByCodeAndActiveStatus(code,'Y');
        // Assert
        Assertions.assertNull(result);
    }

    @Test()
    @DisplayName("Test that a list of DTOs will be returned when looking by a list of codes and some exist")
    public void findByCodesAndActiveStatusTest_found(){
        // Arrange
        String code1 = "mockCode1";
        String code2 = "mockCode2";
        String code3 = "mockCode3";
        Job job1 = Job.builder().name("mockJob1").code(code1).activeStatus(('Y')).build();
        job1.setId(1L);
        Job job2 = Job.builder().name("mockJob2").code(code2).activeStatus(('Y')).build();
        job2.setId(2L);
        JobDTO jobDTO1 = JobDTO.builder().name("mockJob1").code(code1).activeStatus(('Y')).build();
        JobDTO jobDTO2 = JobDTO.builder().name("mockJob2").code(code2).activeStatus(('Y')).build();
        when(jobDAOMock.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y')).thenReturn(Arrays.asList(job1,job2));
        // Act
        List<JobDTO> result = service.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y');
        // Assert
        Assertions.assertEquals(Arrays.asList(jobDTO1,jobDTO2),result);
    }

    @Test()
    @DisplayName("Test that an empty list will be returned when looking by a list of codes and none exists")
    public void findByCodesAndActiveStatusTest_notFound(){
        // Arrange
        String code1 = "mockCode1";
        String code2 = "mockCode2";
        String code3 = "mockCode3";
        when(jobDAOMock.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y')).thenReturn(new ArrayList<>());
        // Act
        List<JobDTO> result = service.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y');
        // Assert
        Assertions.assertEquals(new ArrayList<>(),result);
    }

}

