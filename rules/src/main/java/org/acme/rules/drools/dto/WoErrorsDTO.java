package org.acme.rules.drools.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WoErrorsDTO {
    private Long ruleTypeId;
    private String woNumber;
    private String errorMessage;
    private String status;
    private Integer numberRetrials;
}
