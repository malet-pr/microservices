package acme.example.work_order.unit;


import acme.example.work_order.job.internal.Job;
import acme.example.work_order.job.internal.JobDAO;
import acme.example.work_order.jobtype.internal.JobType;
import acme.example.work_order.jobtype.internal.JobTypeDAO;
import acme.example.work_order.workorder.WorkOrderDTO;
import acme.example.work_order.workorder.internal.WorkOrder;
import acme.example.work_order.workorder.internal.WorkOrderMapper;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.workorderjob.internal.WorkOrderJobDAO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkOrderMapperTest {

    @Mock
    private JobTypeDAO typeMock;
    @Mock
    private WorkOrderJobDAO woJobMock;
    @Mock
    private JobDAO jobMock;
    @InjectMocks
    private WorkOrderMapper woMapper;

    static WorkOrder wo1;
    static WorkOrderDTO dto1;
    static JobType type1;
    static WorkOrderJob woJob1,woJob2;
    static List<WorkOrderJob> woJobs;
    static Job job1,job2;
    static List<Job> jobs;
    static WorkOrderJobDTO woJobDTO1,woJobDTO2;
    static LocalDateTime date1,date2;

    @BeforeAll
    static void setUp(){
        date1 = LocalDateTime.now().minusDays(5);
        date2 = date1.minusDays(1);
        type1 = JobType.builder().code("typeCode").build();
        type1.setId(1L);
        job1 = Job.builder().code("code1").build();
        job2 = Job.builder().code("code2").build();
        jobs = List.of(job1,job2);
        woJob1 = WorkOrderJob.builder().job(job1).build();
        woJob2 = WorkOrderJob.builder().job(job2).build();
        woJobs = List.of(woJob1,woJob2);
        woJobDTO1 = WorkOrderJobDTO.builder().woNumber("woNumber1").jobCode("code1").build();
        woJobDTO2 = WorkOrderJobDTO.builder().woNumber("woNumber1").jobCode("code2").build();
        wo1 = WorkOrder.builder()
                .woNumber("woNumber1")
                .jobType(type1)
                .jobs(woJobs)
                .address("address").city("city").state("state")
                .woCreationDate(date1)
                .woCompletionDate(date2)
                .clientId("client1")
                .build();
        dto1 =  WorkOrderDTO.builder()
                .woNumber("woNumber1")
                .jobTypeCode(wo1.getJobType().getCode())
                .woJobDTOs(List.of(woJobDTO1,woJobDTO2))
                .address("address").city("city").state("state")
                .woCreationDate(date1)
                .woCompletionDate(date2)
                .clientId("client1")
                .build();
    }

    @Test()
    @DisplayName("Test the conversion from entity to dto")
    public void convertToDTOTest() {
        // Arrange
        // Act
        WorkOrderDTO dto = woMapper.convertToDTO(wo1);
        // Assert
        Assertions.assertEquals(dto1,dto);
    }

    @Test()
    @DisplayName("Test the conversion from entity to dto when entity is null")
    public void convertToDTOTest_nullEntity() {
        // Arrange
        // Act
        WorkOrderDTO dto = woMapper.convertToDTO(null);
        // Assert
        Assertions.assertNull(dto);
    }

    @Test()
    @DisplayName("Test the conversion from dto to entity")
    public void convertToEntityTest() {
        // Arrange
        when(typeMock.findByCode(dto1.getJobTypeCode())).thenReturn(type1);
        when(jobMock.findByCodes(anyList())).thenReturn(jobs);
        // Act
        WorkOrder wo = woMapper.convertToEntity(dto1);
        // Assert
        Assertions.assertEquals(wo1,wo);
    }

    @Test()
    @DisplayName("Test the conversion from dto to entity when dto is null")
    public void convertToEntityTest_nullDTO() {
        // Arrange
        // Act
        WorkOrder wo = woMapper.convertToEntity(null);
        // Assert
        Assertions.assertNull(wo);
    }

    // Test JobType not found
    // Test fail to retrieve JobList

}
