package acme.example.work_order.workorder;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WORuleDTO {
	private String woNumber;
	private LocalDateTime woCreationDate;
	private LocalDateTime woCompletionDate;
	private String ruleType;
	private String jobTypeCode;
	private String jobTypeName;
	private String address;
	private String city;
	private String state;
	private String clientId;
	private String jobCodesList;
}
