package org.acme.orders.unit;

import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.internal.OrderDAO;
import org.acme.orders.order.internal.Order;
import org.acme.orders.order.internal.OrderServiceImpl;
import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.order.internal.OrderMapper;
import org.acme.orders.jobtype.internal.JobType;
import org.acme.orders.orderjob.internal.OrderJob;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderDAO woDAOMock;

    @Mock
    private OrderMapper woMapperMock;

    @InjectMocks
    private OrderServiceImpl service;

    static LocalDateTime date1,date2;
    static OrderDTO dto;
    static Order wo;

    @BeforeAll
    static void setUp() {
        date1 = LocalDateTime.now().minusDays(5);
        date2 = date1.minusDays(1);
        OrderJobDTO woJobDTO1 = OrderJobDTO.builder().jobCode("code1").build();
        OrderJobDTO woJobDTO2 = OrderJobDTO.builder().jobCode("code2").build();
        dto = OrderDTO.builder().woNumber("ABC123")
                .woJobDTOs(Arrays.asList(woJobDTO1, woJobDTO2))
                .jobTypeCode("type1")
                .address("address").city("city").state("state")
                .woCreationDate(date1)
                .woCompletionDate(date2)
                .clientId("xx1")
                .build();
        JobType jobType = new JobType();
        jobType.setId(1L);
        OrderJob job1 = new OrderJob();
        job1.setId(1L);
        OrderJob job2 = new OrderJob();
        job2.setId(2L);
        List<OrderJob> jobs = new ArrayList<>(Arrays.asList(job1, job2));
        wo = Order.builder().woNumber("ABC123")
                .jobs(jobs)
                .jobType(jobType)
                .address("address").city("city").state("state")
                .woCreationDate(date1)
                .woCompletionDate(date2)
                .clientId("xx1")
                .build();
        wo.setId(1L);
        wo.setCreationDate(new Date());
    }

    @Test()
    @DisplayName("Test that a proper entity will be saved from a DTO")
    public void saveTest(){
        // Arrange
        when(woMapperMock.convertToEntity(dto)).thenReturn(wo);
        // Act
        service.save(dto);
        // Assert
        Mockito.verify(woMapperMock, times(1)).convertToEntity(dto);
        Mockito.verify(woDAOMock, times(1)).save(wo);
    }

    @Test()
    @DisplayName("Test that a proper OrderDTO will be returned when searching by id")
    public void findByIdTest(){
        // Arrange
        when(woDAOMock.findById(1L)).thenReturn(Optional.of(wo));
        when(woMapperMock.convertToDTO(wo)).thenReturn(dto);
        // Act
        OrderDTO result = service.findById(1L);
        // Assert
        Assertions.assertEquals(dto,result);
    }

    @Test()
    @DisplayName("Test that a proper OrderDTO will be returned when searching by WO number")
    public void findByWoNumberTest(){
        // Arrange
        when(woDAOMock.findByOrderNumber("ABC123")).thenReturn(wo);
        when(woMapperMock.convertToDTO(wo)).thenReturn(dto);
        // Act
        OrderDTO result = service.findByWoNumber("ABC123");
        // Assert
        Assertions.assertEquals(dto,result);
    }

}
