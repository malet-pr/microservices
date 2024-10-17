package org.acme.rules.drools.internal.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RULE_LOG")
public class RuleLog extends BaseEntity {
	@NotNull
	private String runId;
	@NotNull
	private String ruleName;
	@NotNull
	private String woNumber;
	@NotNull
	private Date startDate;
	@NotNull
	private Date endDate;
	private String initialActiveJob;
	private String initialInactiveJob;
	private String finalActiveJob;
	private String finalInactiveJob;
	private String jobTypeCode;
	@NotNull
	private Date woCreationDate;
	@NotNull
	private Date woCompletionDate;
}
