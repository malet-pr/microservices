package org.acme.rules.drools.internal.adapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

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
	private LocalDate woCreationDate;
	private LocalDate woCompletionDate;
	private int woCompletionYear;
	private int woCompletionDay;
	private String jobTypeCode;
	private String jobTypeName;
	private String address;
	private String city;
	private String state;
	private List<String> jobCodeList;
	private String clientID;

}
