package acme.example.work_order.job;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDTO {
	private String code;
	private String name;
	private Character activeStatus;
}

