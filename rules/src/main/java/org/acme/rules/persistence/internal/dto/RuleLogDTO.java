package org.acme.rules.persistence.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleLogDTO {
	private String runId;
	private String ruleName;
	private String woNumber;
	private Date startDate;
	private Date endDate;
	private String initialActiveJob;
	private String initialInactiveJob;
	private String finalActiveJob;
	private String finalInactiveJob;
	private String jobTypeCode;
	private Date woCreationDate;
	private Date woCompletionDate;
}
