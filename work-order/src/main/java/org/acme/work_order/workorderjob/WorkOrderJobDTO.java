package org.acme.work_order.workorderjob;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderJobDTO {
	private String woNumber;
	private String jobCode;
	private int quantity;
	private Character activeStatus;
	private String appliedRule;
}