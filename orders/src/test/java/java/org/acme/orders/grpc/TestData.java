package java.org.acme.orders.grpc;

import com.google.protobuf.Timestamp;
import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.internal.Order;
import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.orderjob.internal.OrderJob;
import org.acme.orders.job.internal.Job;
import org.acme.orders.jobtype.internal.JobType;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class TestData {

    /*
    static Instant instant1 = Instant.ofEpochSecond(1729003244L);
    static Instant instant2 = Instant.ofEpochSecond(1729089644L);

    static WoJob woJob1 = WoJob.newBuilder()
            .setWoNumber("ABC123").setJobCode("code1")
            .setQuantity(3).setActiveStatus("N").setAppliedRule("A1").build();

    static WoJob woJob2 = WoJob.newBuilder()
            .setWoNumber("ABC123").setJobCode("code2")
            .setQuantity(1).setActiveStatus("Y").setAppliedRule("").build();

    static OrderRequest req1 = OrderRequest.newBuilder()
            .setWoNumber("ABC123")
            .setJobTypeCode("jobTypeCode")
            .setAddress("address")
            .setCity("city")
            .setState("state")
            .setWoCreationDate(Timestamp.newBuilder().setSeconds(1729003244).build())
            .setWoCompletionDate(Timestamp.newBuilder().setSeconds(1729089644).build())
            .setClientId("clientId")
            .setHasRules(Boolean.TRUE)
            .addAllWoJobs(List.of(woJob1,woJob2))
            .build();

    static OrderJobDTO WOJob1 = OrderJobDTO.builder()
            .woNumber("ABC123").jobCode("code1").quantity(3)
            .activeStatus('N').appliedRule("A1").build();

    static OrderJobDTO WOJob2 = OrderJobDTO.builder()
            .woNumber("ABC123").jobCode("code2").quantity(1)
            .activeStatus('Y').appliedRule("").build();

    static OrderDTO dto1 = OrderDTO.builder()
            .woNumber("ABC123").jobTypeCode("jobTypeCode").address("address").city("city").state("state")
            .woCreationDate(instant1.atZone(ZoneId.systemDefault()).toLocalDateTime())
            .woCompletionDate(instant2.atZone(ZoneId.systemDefault()).toLocalDateTime())
            .clientId("clientId").hasRules(Boolean.TRUE).woJobDTOs(List.of(WOJob1,WOJob2)).build();

    static OrderRequest reqFail = null;

    static JobType jobType = JobType.builder().code("jobTypeCode").name("name")
            .clientType("corporate").activeStatus('Y').build();

    static Job job1 = Job.builder().code("code1").build();
    static Job job2 = Job.builder().code("code2").build();

    static OrderJob wj1 = OrderJob.builder().job(job1).quantity(3)
            .activeStatus('Y').build();

    static OrderJob wj2 = OrderJob.builder().job(job2).quantity(1)
            .activeStatus('Y').appliedRule("").build();

    static Order wo1 = Order.builder()
            .woNumber("ABC123")
            .jobType(jobType)
            .address("address")
            .city("city")
            .state("state")
            .woCreationDate(instant1.atZone(ZoneId.systemDefault()).toLocalDateTime())
            .woCompletionDate(instant2.atZone(ZoneId.systemDefault()).toLocalDateTime())
            .clientId("clientId")
            .hasRules(Boolean.FALSE)
            .jobs(List.of(wj1))
            .build();
*/
}
