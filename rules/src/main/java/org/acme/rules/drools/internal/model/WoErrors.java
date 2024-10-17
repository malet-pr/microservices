package org.acme.rules.drools.internal.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ERROR_WO_RULE")
public class WoErrors extends BaseEntity {
    private Long ruleTypeId;
    private String woNumber;
    private String errorMessage;
    private String status;
    private Integer numberRetrials;

}
