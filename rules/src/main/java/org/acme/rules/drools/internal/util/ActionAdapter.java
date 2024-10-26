package org.acme.rules.drools.internal.util;

import lombok.*;
import org.acme.rules.drools.WorkOrderData;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionAdapter {
	
	private String action;
	private List<WorkOrderData> woData;
	private String newJob;
	private String oldJob;
	private int amount;
	private String rule;
	private LocalDateTime creationDate;

}
