package org.acme.rules.drools.internal.util;

import lombok.*;
import org.acme.rules.grpc.woserviceconnect.Order;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionAdapter {
	
	private String action;
	private List<Order> woData;
	private String newJob;
	private String oldJob;
	private int amount;
	private String rule;
	private LocalDateTime creationDate;

}
