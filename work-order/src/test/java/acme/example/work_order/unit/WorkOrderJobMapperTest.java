package acme.example.work_order.unit;

import acme.example.work_order.job.internal.Job;
import acme.example.work_order.job.internal.JobDAO;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorder.internal.WorkOrder;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.workorder.internal.WorkOrderDAO;
import acme.example.work_order.workorderjob.internal.WorkOrderJobMapper;
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
public class WorkOrderJobMapperTest {

    @Mock
    private WorkOrderDAO woDAO;
    @Mock
    private JobDAO jobDAO;
    @InjectMocks
    private WorkOrderJobMapper woJobMapper;

    static WorkOrderJobDTO dto1,dto2;
    static WorkOrderJob woJob1,woJob2;
    static List<WorkOrderJobDTO> dtos;
    static List<WorkOrderJob> woJobs;
    static WorkOrder wo1,wo2;
    static Job job1,job2;

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
        job1 = Job.builder().code("22er").build();
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
        job2 = Job.builder().code("99er").build();
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

    @Test
    @DisplayName("Test the conversion from entity to dto")
    public void convertToDTOTest(){
        // Arrange
        // Act
        WorkOrderJobDTO dto = woJobMapper.convertToDTO(woJob1);
        // Assert
        Assertions.assertEquals(dto1,dto);
    }

    @Test
    @DisplayName("Test the conversion from entity to dto where entity is null")
    public void convertToDTOTest_nullEntity(){
        // Arrange
        // Act
        WorkOrderJobDTO dto = woJobMapper.convertToDTO(null);
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
        WorkOrderJob woJob = woJobMapper.convertToEntity(dto1);
        // Assert
        Assertions.assertEquals(woJob1,woJob);
    }

    @Test
    @DisplayName("Test the conversion from dto to entity where dto is null")
    public void convertToEntityTest_nullDTO(){
        // Arrange
        // Act
        WorkOrderJob woJob = woJobMapper.convertToEntity(null);
        // Assert
        Assertions.assertNull(woJob);
    }

    @Test()
    @DisplayName("Test the conversion from a List of entities to a list of dtos")
    public void convertListToDTOTest(){
        // Arrange
        // Act
        List<WorkOrderJobDTO> testDTOs = woJobMapper.convertListToDTO(woJobs);
        // Assert
        Assertions.assertEquals(dtos,testDTOs);
    }

    @Test()
    @DisplayName("Test the conversion from a List of entities to a list of dtos when the list is empty")
    public void convertListToDTOTest_emptyList(){
        // Arrange
        // Act
        List<WorkOrderJobDTO> testDTOs = woJobMapper.convertListToDTO(new ArrayList<>());
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
        List<WorkOrderJob> testJobs = woJobMapper.convertListToEntity(dtos);
        // Assert
        Assertions.assertEquals(woJobs,testJobs);
    }

    @Test()
    @DisplayName("Test the conversion from a list of dtos to a list of entities when the list is empty")
    public void convertListToEntitiesTest_emptyList() {
        // Arrange
        // Act
        List<WorkOrderJob> testJobs = woJobMapper.convertListToEntity(new ArrayList<>());
        // Assert
        Assertions.assertEquals(new ArrayList<>(),testJobs);
    }

}
