package org.acme.rules.drools.internal.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

// TODO: use one kind of date for each creation and completion, handle variants as needed

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WoRuleAdapter {

	private Long id;
	private String woNumber;
	private LocalDateTime woCreationDateTime;
	private LocalDateTime woCompletionDateTime;
	private String jobTypeCode;
	private String jobTypeName;
	private String address;
	private String city;
	private String state;
	private List<String> jobCodeList;
	private String clientID;

}
