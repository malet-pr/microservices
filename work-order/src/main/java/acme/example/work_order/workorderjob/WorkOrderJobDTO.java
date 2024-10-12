package acme.example.work_order.workorderjob;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderJobDTO {
	private String woNumber;
	private String jobCode;
	private Integer quantity;
	private Character activeStatus;
	private String appliedRule;
}