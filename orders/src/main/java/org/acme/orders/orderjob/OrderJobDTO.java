package org.acme.orders.orderjob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderJobDTO {
	private String woNumber;
	private String jobCode;
	private int quantity;
	private Character activeStatus;
	private String appliedRule;
}