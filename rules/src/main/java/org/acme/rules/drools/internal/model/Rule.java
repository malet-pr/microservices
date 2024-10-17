package org.acme.rules.drools.internal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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