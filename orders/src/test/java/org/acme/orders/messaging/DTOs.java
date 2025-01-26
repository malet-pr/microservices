package org.acme.orders.messaging;

import org.acme.orders.order.OrderDTO;
import org.acme.orders.orderjob.OrderJobDTO;
import java.time.LocalDateTime;
import java.util.Collections;

public class DTOs {

    static OrderJobDTO woJobDTO1 = OrderJobDTO.builder()
            .jobCode("JobCode1")
            .activeStatus('N')
            .quantity(9)
            .build();

    public static OrderDTO dto1 = OrderDTO.builder()
            .woNumber("testNumber")
            .jobType("type1")
            .woJobDTOs(Collections.singletonList(woJobDTO1))
            .woCreationDate(LocalDateTime.now().minusDays(3))
            .woCompletionDate(LocalDateTime.now().minusHours(4))
            .address("address1")
            .city("city1")
            .state("state1")
            .clientId("client1")
            .build();

    public static OrderDTO dto2 = OrderDTO.builder()
            .woNumber("ABC123")
            .jobType("type1")
            .woJobDTOs(Collections.singletonList(woJobDTO1))
            .woCreationDate(LocalDateTime.now().minusDays(3))
            .woCompletionDate(LocalDateTime.now().minusHours(4))
            .address("address5")
            .city("city5")
            .state("state5")
            .clientId("client5")
            .build();



}
