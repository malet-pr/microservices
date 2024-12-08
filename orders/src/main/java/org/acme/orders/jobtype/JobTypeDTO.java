package org.acme.orders.jobtype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobTypeDTO {
    private String code;
    private String name;
    private Character activeStatus;
    private String clientType;
}
