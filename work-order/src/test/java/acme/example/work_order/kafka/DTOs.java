package acme.example.work_order.kafka;

import acme.example.work_order.workorder.WorkOrderDTO;
import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import java.time.LocalDateTime;
import java.util.Collections;

public class DTOs {

    static WorkOrderJobDTO woJobDTO1 = WorkOrderJobDTO.builder()
            .jobCode("JobCode1")
            .activeStatus('N')
            .quantity(9)
            .build();

    public static WorkOrderDTO dto1 = WorkOrderDTO.builder()
            .woNumber("testNumber")
            .jobTypeCode("type1")
            .woJobDTOs(Collections.singletonList(woJobDTO1))
            .woCreationDate(LocalDateTime.now().minusDays(3))
            .woCompletionDate(LocalDateTime.now().minusHours(4))
            .address("address1")
            .city("city1")
            .state("state1")
            .clientId("client1")
            .build();

    public static WorkOrderDTO dto2 = WorkOrderDTO.builder()
            .woNumber("ABC123")
            .jobTypeCode("type2")
            .woJobDTOs(Collections.singletonList(woJobDTO1))
            .woCreationDate(LocalDateTime.now().minusDays(3))
            .woCompletionDate(LocalDateTime.now().minusHours(4))
            .address("address5")
            .city("city5")
            .state("state5")
            .clientId("client5")
            .build();



}
