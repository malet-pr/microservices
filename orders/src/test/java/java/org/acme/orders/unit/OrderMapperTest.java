package java.org.acme.orders.unit;


import org.acme.work_order.job.internal.Job;
import org.acme.work_order.job.internal.JobDAO;
import org.acme.work_order.jobtype.internal.JobType;
import org.acme.work_order.jobtype.internal.JobTypeDAO;
import org.acme.work_order.workorder.internal.WorkOrderMapper;
import org.acme.work_order.workorderjob.WorkOrderJobDTO;
import org.acme.work_order.workorderjob.internal.WorkOrderJobDAO;
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
public class OrderMapperTest {

    @Mock
    private JobTypeDAO typeMock;
    @Mock
    private WorkOrderJobDAO woJobMock;
    @Mock
    private JobDAO jobMock;
    @InjectMocks
    private WorkOrderMapper woMapper;

    static org.acme.work_order.workorder.internal.Order wo1;
    static org.acme.work_order.workorder.OrderDTO dto1;
    static JobType type1;
    static org.acme.work_order.workorderjob.internal.OrderJob woJob1,woJob2;
    static List<org.acme.work_order.workorderjob.internal.OrderJob> woJobs;
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
        woJob1 = org.acme.work_order.workorderjob.internal.OrderJob.builder().job(job1).build();
        woJob2 = org.acme.work_order.workorderjob.internal.OrderJob.builder().job(job2).build();
        woJobs = List.of(woJob1,woJob2);
        woJobDTO1 = WorkOrderJobDTO.builder().woNumber("woNumber1").jobCode("code1").build();
        woJobDTO2 = WorkOrderJobDTO.builder().woNumber("woNumber1").jobCode("code2").build();
        wo1 = org.acme.work_order.workorder.internal.Order.builder()
                .woNumber("woNumber1")
                .jobType(type1)
                .jobs(woJobs)
                .address("address").city("city").state("state")
                .woCreationDate(date1)
                .woCompletionDate(date2)
                .clientId("client1")
                .build();
        dto1 =  org.acme.work_order.workorder.OrderDTO.builder()
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
        org.acme.work_order.workorder.OrderDTO dto = woMapper.convertToDTO(wo1);
        // Assert
        Assertions.assertEquals(dto1,dto);
    }

    @Test()
    @DisplayName("Test the conversion from entity to dto when entity is null")
    public void convertToDTOTest_nullEntity() {
        // Arrange
        // Act
        org.acme.work_order.workorder.OrderDTO dto = woMapper.convertToDTO(null);
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
        org.acme.work_order.workorder.internal.Order wo = woMapper.convertToEntity(dto1);
        // Assert
        Assertions.assertEquals(wo1,wo);
    }

    @Test()
    @DisplayName("Test the conversion from dto to entity when dto is null")
    public void convertToEntityTest_nullDTO() {
        // Arrange
        // Act
        org.acme.work_order.workorder.internal.Order wo = woMapper.convertToEntity(null);
        // Assert
        Assertions.assertNull(wo);
    }

    // Test JobType not found
    // Test fail to retrieve JobList

}
