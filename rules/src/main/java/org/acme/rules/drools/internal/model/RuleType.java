package org.acme.rules.drools.internal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RULE_TYPE")
public class RuleType extends BaseEntity {
	private String name;
	private String code;
	private String header;
	private Character visible;
	private String grouping;
	private Long priority;
	private String headerDefault;
}
