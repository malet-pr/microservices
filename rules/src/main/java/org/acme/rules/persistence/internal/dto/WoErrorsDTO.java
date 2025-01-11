package org.acme.rules.persistence.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
