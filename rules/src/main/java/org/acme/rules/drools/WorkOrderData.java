package org.acme.rules.drools;

import lombok.*;
import org.acme.rules.grpc.woserviceconnect.WoJob;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderData {
    private String woNumber;
    private List<WoJob> woJobs;
    private LocalDateTime woCreationDate;
    private LocalDateTime woCompletionDate;
    private String jobTypeCode;
    private String jobTypeName;
    private String address;
    private String city;
    private String state;
    private String clientId;
}
