package org.acme.rules.grpc.woserviceconnect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
