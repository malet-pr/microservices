package org.acme.orders.unit;

import org.acme.orders.job.internal.Job;
import org.acme.orders.job.internal.JobDAO;
import org.acme.orders.order.internal.Order;
import org.acme.orders.order.internal.OrderDAO;
import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.orderjob.internal.OrderJob;
import org.acme.orders.orderjob.internal.OrderJobMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderJobMapperTest {

    @Mock
    private OrderDAO woDAO;
    @Mock
    private JobDAO jobDAO;
    @InjectMocks
    private OrderJobMapper woJobMapper;

    static OrderJobDTO dto1,dto2;
    static OrderJob woJob1,woJob2;
    static List<OrderJobDTO> dtos;
    static List<OrderJob> woJobs;
    static Order wo1,wo2;
    static Job job1,job2;

    @BeforeAll
    static void setUp() {
        dto1 = OrderJobDTO.builder()
                .woNumber("ABC123")
                .jobCode("22er")
                .quantity(3)
                .activeStatus('S')
                .build();
        wo1 = Order.builder().woNumber("ABC123").build();
        wo1.setId(1L);
        wo1.setCreationDate(new Date());
        job1 = Job.builder().code("22er").build();
        job1.setId(1L);
        job1.setCreationDate(new Date());
        woJob1 = OrderJob.builder()
                .order(wo1)
                .job(job1)
                .quantity(3)
                .activeStatus('S')
                .build();
        dto2 = OrderJobDTO.builder()
                .woNumber("ABC456")
                .jobCode("99er")
                .quantity(3)
                .activeStatus('S')
                .build();
        wo2 = Order.builder().woNumber("ABC456").build();
        job2 = Job.builder().code("99er").build();
        job2.setId(2L);
        job2.setCreationDate(new Date());
        woJob2 = OrderJob.builder()
                .order(wo2)
                .job(job2)
                .quantity(3)
                .activeStatus('S')
                .build();
        dtos = Arrays.asList(dto1,dto2);
        woJobs = Arrays.asList(woJob1,woJob2);
    }

    @Test
    @DisplayName("Test the conversion from entity to dto")
    public void convertToDTOTest(){
        // Arrange
        // Act
        OrderJobDTO dto = woJobMapper.convertToDTO(woJob1);
        // Assert
        Assertions.assertEquals(dto1,dto);
    }

    @Test
    @DisplayName("Test the conversion from entity to dto where entity is null")
    public void convertToDTOTest_nullEntity(){
        // Arrange
        // Act
        OrderJobDTO dto = woJobMapper.convertToDTO(null);
        // Assert
        Assertions.assertNull(dto);
    }

    @Test
    @DisplayName("Test the conversion from dto to entity")
    public void convertToEntityTest(){
        // Arrange
        when(woDAO.findByWoNumber("ABC123")).thenReturn(wo1);
        when(jobDAO.findByCode("22er")).thenReturn(job1);
        // Act
        OrderJob woJob = woJobMapper.convertToEntity(dto1);
        // Assert
        Assertions.assertEquals(woJob1,woJob);
    }

    @Test
    @DisplayName("Test the conversion from dto to entity where dto is null")
    public void convertToEntityTest_nullDTO(){
        // Arrange
        // Act
        OrderJob woJob = woJobMapper.convertToEntity(null);
        // Assert
        Assertions.assertNull(woJob);
    }

    @Test()
    @DisplayName("Test the conversion from a List of entities to a list of dtos")
    public void convertListToDTOTest(){
        // Arrange
        // Act
        List<OrderJobDTO> testDTOs = woJobMapper.convertListToDTO(woJobs);
        // Assert
        Assertions.assertEquals(dtos,testDTOs);
    }

    @Test()
    @DisplayName("Test the conversion from a List of entities to a list of dtos when the list is empty")
    public void convertListToDTOTest_emptyList(){
        // Arrange
        // Act
        List<OrderJobDTO> testDTOs = woJobMapper.convertListToDTO(new ArrayList<>());
        // Assert
        Assertions.assertEquals(new ArrayList<>(),testDTOs);
    }

    @Test()
    @DisplayName("Test the conversion from a list of dtos to a list of entities")
    public void convertListToEntitiesTest() {
        // Arrange
        when(woDAO.findByWoNumber("ABC123")).thenReturn(wo1);
        when(jobDAO.findByCode("22er")).thenReturn(job1);
        when(woDAO.findByWoNumber("ABC456")).thenReturn(wo2);
        when(jobDAO.findByCode("99er")).thenReturn(job2);
        // Act
        List<OrderJob> testJobs = woJobMapper.convertListToEntity(dtos);
        // Assert
        Assertions.assertEquals(woJobs,testJobs);
    }

    @Test()
    @DisplayName("Test the conversion from a list of dtos to a list of entities when the list is empty")
    public void convertListToEntitiesTest_emptyList() {
        // Arrange
        // Act
        List<OrderJob> testJobs = woJobMapper.convertListToEntity(new ArrayList<>());
        // Assert
        Assertions.assertEquals(new ArrayList<>(),testJobs);
    }

}
