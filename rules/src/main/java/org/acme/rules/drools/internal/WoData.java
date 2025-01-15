package org.acme.rules.drools.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class WoData{

    private String woNumber;
    private List<WoJob> woJobs;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCreationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCompletionDate;
    private String jobTypeCode;
    private String state;
    private String clientId;
    private String clientType;
    private boolean hasRules;

}
