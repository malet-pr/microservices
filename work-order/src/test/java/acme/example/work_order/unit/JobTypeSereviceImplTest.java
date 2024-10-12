package acme.example.work_order.unit;

import acme.example.work_order.jobtype.JobTypeDTO;
import acme.example.work_order.jobtype.internal.JobType;
import acme.example.work_order.jobtype.internal.JobTypeDAO;
import acme.example.work_order.jobtype.internal.JobTypeServiceImpl;
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
public class JobTypeSereviceImplTest {

    @Mock
    JobTypeDAO jobTypeDAOMock;

    @InjectMocks
    JobTypeServiceImpl service;

    @Test()
    @DisplayName("Test that a proper JobTypeDTO will be returned when searching by id")
    public void findByIdTest(){
        // Arrange
        Long id = 1L;
        JobType jobType = JobType.builder().name("mockType").code("mockCode").activeStatus(('N')).build();
        jobType.setId(id);
        JobTypeDTO jobTypeDTO = JobTypeDTO.builder().name("mockType").code("mockCode").activeStatus(('N')).build();
        when(jobTypeDAOMock.findById(id)).thenReturn(Optional.of(jobType));
        // Act
        JobTypeDTO result = service.findById(id);
        // Assert
        Assertions.assertEquals(jobTypeDTO,result);
    }

    @Test()
    @DisplayName("Test that a proper entity will be saved from a DTO")
    public void saveTest(){
        // Arrange
        JobType jobType = JobType.builder().name("mockType").code("mockCode").activeStatus(('N')).build();
        JobTypeDTO jobTypeDTO = JobTypeDTO.builder().name("mockType").code("mockCode").activeStatus(('N')).build();
        // Act
        boolean result = service.save(jobTypeDTO);
        // Assert
        Mockito.verify(jobTypeDAOMock,times(1)).save(jobType);
        Assertions.assertTrue(result);
    }

    @Test()
    @DisplayName("Test that the status is returned when searching by id and the entity exists")
    public void getActiveStatusById_found(){
        // Arrange
        Long id = 1L;
        JobType jobType = JobType.builder().name("mockType").code("mockCode").activeStatus(('Y')).build();
        when(jobTypeDAOMock.findById(id)).thenReturn(Optional.of(jobType));
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
        when(jobTypeDAOMock.findById(id)).thenReturn(Optional.empty());
        // Act
        Character result = service.getActiveStatusById(id);
        // Assert
        Assertions.assertNull(result);
    }

    @Test()
    @DisplayName("Test that a proper JobTypeDTO will be returned when searching by code when it exists")
    public void findByCodeTest_found(){
        // Arrange
        String code = "mockCode";
        JobType jobType = JobType.builder().name("mockJob").code(code).activeStatus(('N')).build();
        JobTypeDTO jobTypeDTO = JobTypeDTO.builder().name("mockJob").code("mockCode").activeStatus(('N')).build();
        when(jobTypeDAOMock.findByCode(code)).thenReturn(jobType);
        // Act
        JobTypeDTO result = service.findByCode(code);
        // Assert
        Assertions.assertEquals(jobTypeDTO,result);
    }

    @Test()
    @DisplayName("Test that null will be returned when searching by code and it doesn't exists")
    public void findByCodeTest_notFound(){
        // Arrange
        String code = "mockCode";
        when(jobTypeDAOMock.findByCode(code)).thenReturn(null);
        // Act
        JobTypeDTO result = service.findByCode(code);
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
        JobType type1 = JobType.builder().name("mockType1").code(code1).activeStatus(('N')).build();
        type1.setId(1L);
        JobType type2 = JobType.builder().name("mockType2").code(code2).activeStatus(('Y')).build();
        type2.setId(2L);
        when(jobTypeDAOMock.findByCodes(Arrays.asList(code1,code2,code3))).thenReturn(Arrays.asList(type1,type2));
        // Act
        List<Long> result = service.findByCodes(Arrays.asList(code1,code2,code3));
        // Assert
        Assertions.assertEquals(Arrays.asList(1L,2L),result);
    }

    @Test()
    @DisplayName("Test that an empty list will be returned when looking by a list of codes and none exists")
    public void findByCodesTest_notFound(){
        // Arrange
        String code1 = "mockCode1";
        String code2 = "mockCode2";
        String code3 = "mockCode3";
        when(jobTypeDAOMock.findByCodes(Arrays.asList(code1,code2,code3))).thenReturn(new ArrayList<>());
        // Act
        List<Long> result = service.findByCodes(Arrays.asList(code1,code2,code3));
        // Assert
        Assertions.assertEquals(new ArrayList<>(),result);
    }

    @Test()
    @DisplayName("Test that a proper JobTypeDTO will be returned when searching by code and active when it exists")
    public void findByCodeAndActiveStatusTest_found(){
        // Arrange
        String code = "mockCode";
        JobType jobType = JobType.builder().name("mockType").code(code).activeStatus(('Y')).build();
        JobTypeDTO jobTypeDTO = JobTypeDTO.builder().name("mockType").code("mockCode").activeStatus(('Y')).build();
        when(jobTypeDAOMock.findByCodeAndActiveStatus(code,'Y')).thenReturn(jobType);
        // Act
        JobTypeDTO result = service.findByCodeAndActiveStatus(code,'Y');
        // Assert
        Assertions.assertEquals(jobTypeDTO,result);
    }

    @Test()
    @DisplayName("Test that null will be returned when searching by code and active and it doesn't exists")
    public void findByCodeAndActiveStatusTest_notFound(){
        // Arrange
        String code = "mockCode";
        when(jobTypeDAOMock.findByCodeAndActiveStatus(code,'Y')).thenReturn(null);
        // Act
        JobTypeDTO result = service.findByCodeAndActiveStatus(code,'Y');
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
        JobType type1 = JobType.builder().name("mockType1").code(code1).activeStatus(('Y')).build();
        JobType type2 = JobType.builder().name("mockType2").code(code2).activeStatus(('Y')).build();
        JobTypeDTO jobDTO1 = JobTypeDTO.builder().name("mockType1").code(code1).activeStatus(('Y')).build();
        JobTypeDTO jobDTO2 = JobTypeDTO.builder().name("mockType2").code(code2).activeStatus(('Y')).build();
        when(jobTypeDAOMock.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y')).thenReturn(Arrays.asList(type1,type2));
        // Act
        List<JobTypeDTO> result = service.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y');
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
        when(jobTypeDAOMock.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y')).thenReturn(new ArrayList<>());
        // Act
        List<JobTypeDTO> result = service.findByCodesAndActiveStatus(Arrays.asList(code1,code2,code3),'Y');
        // Assert
        Assertions.assertEquals(new ArrayList<>(),result);
    }

}

