package org.acme.rules.drools.internal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleTypeDTO {
	private String name;
	private String code;
	private String header;
	private Character visible;
	private String grouping;
	private Long priority;
	private String headerDefault;
}
