package org.acme.orders.order.internal;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WoData {
    private String woNumber;
    private List<WoJob> woJobs;
    private LocalDateTime woCreationDate;
    private LocalDateTime woCompletionDate;
    private String jobTypeCode;
    private String state;
    private String clientId;
    private String clientType;
    private boolean hasRules;
}

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class WoJob {
    private String woNumber;
    private String jobCode;
    private int quantity;
    private String activeStatus;
    private String appliedRule;
}




