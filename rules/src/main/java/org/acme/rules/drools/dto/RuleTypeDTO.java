package org.acme.rules.drools.dto;

import lombok.*;

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
