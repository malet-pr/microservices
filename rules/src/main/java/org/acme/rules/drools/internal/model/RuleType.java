package org.acme.rules.drools.internal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "rule_type_seq", allocationSize = 1)
@Table(name = "RULE_TYPE")
public class RuleType extends BaseEntity {
	private String name;
	@NotNull
	@Column(unique=true)
	private String code;
	private String header;
	@NotNull
	private String grouping;
	private Long priority;
	private String headerDefault;
}
