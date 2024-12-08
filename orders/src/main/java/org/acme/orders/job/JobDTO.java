package org.acme.orders.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDTO {
	private String code;
	private String name;
	private Character activeStatus;
}

