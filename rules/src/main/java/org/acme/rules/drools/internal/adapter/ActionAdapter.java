package org.acme.rules.drools.internal.adapter;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionAdapter {
	
	private String action;
	private List<WoRuleAdapter> workOrders;		// nroOT
	private String newJob;
	private String oldJob;
	private int amount;
	private String rule;
	private Date creationDate;

}
