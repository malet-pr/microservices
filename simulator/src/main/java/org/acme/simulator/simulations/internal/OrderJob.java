package org.acme.simulator.simulations.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderJob {
    private String woNumber;
    private String jobCode;
    private Integer quantity;
    private Character activeStatus;
    private String appliedRule;
}
