package org.acme.rules.persistence.internal.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "wo_errors_seq", allocationSize = 1)
@Table(name = "ERROR_WO_RULE")
public class WoErrors extends BaseEntity {
    private String ruleTypeCode;
    private String woNumber;
    private String errorMessage;
    private String status;
    private Integer numberRetrials;

}
