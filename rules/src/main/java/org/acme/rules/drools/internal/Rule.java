package org.acme.rules.drools.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "rule_seq", allocationSize = 1)
@Table(name = "RULE")
public class Rule extends BaseEntity{
	@NotNull
	@Column(unique=true)
	private String name;
	private String description;
	@NotNull
	private String grouping;
	@NotNull
	private String drl;
	@NotNull
	private Character activeStatus;

}