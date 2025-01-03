package org.acme.rules.drools.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleDTO {
	private String name;
	private String description;
	private String grouping;
	private String drl;
	private Character activeStatus;

}