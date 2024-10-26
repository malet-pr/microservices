package org.acme.rules.drools.internal.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "rule_log_seq", allocationSize = 1)
@Table(name = "RULE_LOG")
public class RuleLog extends BaseEntity {
	@NotNull
	private String runId;
	@NotNull
	private String ruleName;
	@NotNull
	private String woNumber;
	@NotNull
	private LocalDateTime startDate;
	@NotNull
	private LocalDateTime endDate;
	private String initialActiveJob;
	private String initialInactiveJob;
	private String finalActiveJob;
	private String finalInactiveJob;
	private String jobTypeCode;
	@NotNull
	private LocalDateTime woCreationDate;
	@NotNull
	private LocalDateTime woCompletionDate;
}
