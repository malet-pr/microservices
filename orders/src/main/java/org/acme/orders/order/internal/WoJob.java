package org.acme.orders.order.internal;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WoJob {
    private String woNumber;
    private String jobCode;
    private int quantity;
    private String activeStatus;
    private String appliedRule;
}