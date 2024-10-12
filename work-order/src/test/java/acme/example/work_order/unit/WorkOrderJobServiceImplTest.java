package acme.example.work_order.unit;

import acme.example.work_order.job.internal.Job;
import acme.example.work_order.workorder.internal.WorkOrderDAO;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorder.internal.WorkOrder;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.workorderjob.internal.WorkOrderJobDAO;
import acme.example.work_order.workorderjob.internal.WorkOrderJobMapper;
import acme.example.work_order.workorderjob.internal.WorkOrderJobServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
public class WorkOrderJobServiceImplTest {

    @Mock
    private WorkOrderJobDAO woJobDAOMock;

    @Mock
    private WorkOrderJobMapper woJobMapperMock;

    @Mock
    private WorkOrderDAO woDAOMock;

    @InjectMocks
    private WorkOrderJobServiceImpl service;

    static WorkOrderJobDTO dto1,dto2;
    static WorkOrderJob woJob1,woJob2;
    static List<WorkOrderJobDTO> dtos;
    static List<WorkOrderJob> woJobs;
    static WorkOrder wo1,wo2;

    @BeforeAll
    static void setUp() {
        dto1 = WorkOrderJobDTO.builder()
                .woNumber("ABC123")
                .jobCode("22er")
                .quantity(3)
                .activeStatus('S')
                .build();
        wo1 = WorkOrder.builder().woNumber("ABC123").build();
        wo1.setId(1L);
        wo1.setCreationDate(new Date());
        Job job1 = Job.builder().code("22er").build();
        job1.setId(1L);
        job1.setCreationDate(new Date());
        woJob1 = WorkOrderJob.builder()
                .workOrder(wo1)
                .job(job1)
                .quantity(3)
                .activeStatus('S')
                .build();
        dto2 = WorkOrderJobDTO.builder()
                .woNumber("ABC456")
                .jobCode("99er")
                .quantity(3)
                .activeStatus('S')
                .build();
        wo2 = WorkOrder.builder().woNumber("ABC456").build();
        wo2.setId(2L);
        wo2.setCreationDate(new Date());
        Job job2 = Job.builder().code("99er").build();
        job2.setId(2L);
        job2.setCreationDate(new Date());
        woJob2 = WorkOrderJob.builder()
                .workOrder(wo2)
                .job(job2)
                .quantity(3)
                .activeStatus('S')
                .build();
        dtos = Arrays.asList(dto1,dto2);
        woJobs = Arrays.asList(woJob1,woJob2);
    }

    @Test()
    @DisplayName("Test that a proper entity will be saved from a DTO")
    public void saveTest() {
        // Arrange
        when(woDAOMock.findByWoNumber(dto1.getWoNumber())).thenReturn(wo1);
        when(woJobMapperMock.convertToEntity(dto1)).thenReturn(woJob1);
        // Act
        service.save(dto1);
        // Assert
        Mockito.verify(woJobMapperMock, times(1)).convertToEntity(dto1);
        Mockito.verify(woJobDAOMock, times(1)).save(woJob1);
    }

    @Test()
    @DisplayName("Test that a proper WorkOrderDTO will be returned when searching by id")
    public void findByIdTest(){
        // Arrange
        when(woJobDAOMock.findById(1L)).thenReturn(Optional.of(woJob1));
        when(woJobMapperMock.convertToDTO(woJob1)).thenReturn(dto1);
        // Act
        WorkOrderJobDTO result = service.findById(1L);
        // Assert
        Assertions.assertEquals(dto1,result);
        Mockito.verify(woJobMapperMock, times(1)).convertToDTO(woJob1);
    }

    @Test()
    @DisplayName("Test that a list of DTOs will be returned when looking by a list of codes and some exist")
    public void findByIdsTest_found(){
        // Arrange
        when(woJobDAOMock.findAllById(Arrays.asList(1L,2L,3L))).thenReturn(woJobs);
        when(woJobMapperMock.convertListToDTO(woJobs)).thenReturn(dtos);
        // Act
        List<WorkOrderJobDTO> result = service.findByIds(Arrays.asList(1L,2L,3L));
        // Assert
        Assertions.assertEquals(dtos,result);
        Mockito.verify(woJobMapperMock, times(1)).convertListToDTO(woJobs);
    }

    @Test()
    @DisplayName("Test that an empty list will be returned when looking by a list of codes none exist")
    public void findByIdsTest_notFound(){
        // Arrange
        when(woJobDAOMock.findAllById(Arrays.asList(4L,5L,3L))).thenReturn(new ArrayList<>());
        when(woJobMapperMock.convertListToDTO(new ArrayList<>())).thenReturn(new ArrayList<>());
        // Act
        List<WorkOrderJobDTO> result = service.findByIds(Arrays.asList(4L,5L,3L));
        // Assert
        Assertions.assertEquals(new ArrayList<>(),result);
        Mockito.verify(woJobMapperMock, times(1)).convertListToDTO(new ArrayList<>());
    }

    @Test()
    @DisplayName("Test that a list of DTOs will be returned when looking by a list of ids and some exist")
    public void findByCodesTest_found(){
        // Arrange
        when(woJobDAOMock.findByCodes(Arrays.asList("22er","99er","xxx"))).thenReturn(woJobs);
        when(woJobMapperMock.convertListToDTO(woJobs)).thenReturn(dtos);
        // Act
        List<WorkOrderJobDTO> result = service.findByCodes(Arrays.asList("22er","99er","xxx"));
        // Assert
        Assertions.assertEquals(dtos,result);
        Mockito.verify(woJobMapperMock, times(1)).convertListToDTO(woJobs);
    }

    @Test()
    @DisplayName("Test that an empty list will be returned when looking by a list of ids and none exist")
    public void findByCodesTest_notFound(){
        // Arrange
        when(woJobDAOMock.findByCodes(Arrays.asList("yyy","xxx"))).thenReturn(new ArrayList<>());
        when(woJobMapperMock.convertListToDTO(new ArrayList<>())).thenReturn(new ArrayList<>());
        // Act
        List<WorkOrderJobDTO> result = service.findByCodes(Arrays.asList("yyy","xxx"));
        // Assert
        Assertions.assertEquals(new ArrayList<>(),result);
        Mockito.verify(woJobMapperMock, times(1)).convertListToDTO(new ArrayList<>());
    }

}
