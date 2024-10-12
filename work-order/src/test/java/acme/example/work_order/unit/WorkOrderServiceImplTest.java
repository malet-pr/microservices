package acme.example.work_order.unit;

import acme.example.work_order.workorder.WorkOrderDTO;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorder.internal.WorkOrderMapper;
import acme.example.work_order.workorder.internal.WorkOrderServiceImpl;
import acme.example.work_order.jobtype.internal.JobType;
import acme.example.work_order.workorder.internal.WorkOrder;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.workorder.internal.WorkOrderDAO;
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
public class WorkOrderServiceImplTest {

    @Mock
    private WorkOrderDAO woDAOMock;

    @Mock
    private WorkOrderMapper woMapperMock;

    @InjectMocks
    private WorkOrderServiceImpl service;

    static LocalDateTime date1,date2;
    static WorkOrderDTO dto;
    static WorkOrder wo;

    @BeforeAll
    static void setUp() {
        date1 = LocalDateTime.now().minusDays(5);
        date2 = date1.minusDays(1);
        WorkOrderJobDTO woJobDTO1 = WorkOrderJobDTO.builder().jobCode("code1").build();
        WorkOrderJobDTO woJobDTO2 = WorkOrderJobDTO.builder().jobCode("code2").build();
        dto = WorkOrderDTO.builder().woNumber("ABC123")
                .woJobDTOs(Arrays.asList(woJobDTO1, woJobDTO2))
                .jobTypeCode("type1")
                .address("address").city("city").state("state")
                .woCreationDate(date1)
                .woCompletionDate(date2)
                .clientId("xx1")
                .build();
        JobType jobType = new JobType();
        jobType.setId(1L);
        WorkOrderJob job1 = new WorkOrderJob();
        job1.setId(1L);
        WorkOrderJob job2 = new WorkOrderJob();
        job2.setId(2L);
        List<WorkOrderJob> jobs = new ArrayList<>(Arrays.asList(job1, job2));
        wo = WorkOrder.builder().woNumber("ABC123")
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
    @DisplayName("Test that a proper WorkOrderDTO will be returned when searching by id")
    public void findByIdTest(){
        // Arrange
        when(woDAOMock.findById(1L)).thenReturn(Optional.of(wo));
        when(woMapperMock.convertToDTO(wo)).thenReturn(dto);
        // Act
        WorkOrderDTO result = service.findById(1L);
        // Assert
        Assertions.assertEquals(dto,result);
    }

    @Test()
    @DisplayName("Test that a proper WorkOrderDTO will be returned when searching by WO number")
    public void findByWoNumberTest(){
        // Arrange
        when(woDAOMock.findByWoNumber("ABC123")).thenReturn(wo);
        when(woMapperMock.convertToDTO(wo)).thenReturn(dto);
        // Act
        WorkOrderDTO result = service.findByWoNumber("ABC123");
        // Assert
        Assertions.assertEquals(dto,result);
    }

}
