package org.acme.work_order.grpc;

import com.google.protobuf.Timestamp;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorderjob.WorkOrderJobDTO;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class TestData {

    static Instant instant1 = Instant.ofEpochSecond(1729003244L);
    static Instant instant2 = Instant.ofEpochSecond(1729089644L);

    static org.acme.work_order.grpc.WoJob woJob1 = org.acme.work_order.grpc.WoJob.newBuilder()
            .setWoNumber("ABC123").setJobCode("code1")
            .setQuantity(3).setActiveStatus("N").setAppliedRule("A1").build();

    static org.acme.work_order.grpc.WoJob woJob2 = org.acme.work_order.grpc.WoJob.newBuilder()
            .setWoNumber("ABC123").setJobCode("code2")
            .setQuantity(1).setActiveStatus("Y").setAppliedRule("").build();

    static org.acme.work_order.grpc.WorkOrderRequest req1 = org.acme.work_order.grpc.WorkOrderRequest.newBuilder()
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

    static WorkOrderJobDTO WOJob1 = WorkOrderJobDTO.builder()
            .woNumber("ABC123").jobCode("code1").quantity(3)
            .activeStatus('N').appliedRule("A1").build();

    static WorkOrderJobDTO WOJob2 = WorkOrderJobDTO.builder()
            .woNumber("ABC123").jobCode("code2").quantity(1)
            .activeStatus('Y').appliedRule("").build();

    static WorkOrderDTO dto1 = WorkOrderDTO.builder()
            .woNumber("ABC123").jobTypeCode("jobTypeCode").address("address").city("city").state("state")
            .woCreationDate(instant1.atZone(ZoneId.systemDefault()).toLocalDateTime())
            .woCompletionDate(instant2.atZone(ZoneId.systemDefault()).toLocalDateTime())
            .clientId("clientId").hasRules(Boolean.TRUE).woJobDTOs(List.of(WOJob1,WOJob2)).build();


}

