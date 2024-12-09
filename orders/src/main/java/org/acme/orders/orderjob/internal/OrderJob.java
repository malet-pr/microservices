package org.acme.orders.orderjob.internal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.acme.orders.common.BaseEntity;
import org.acme.orders.common.Constants;
import org.acme.orders.common.GsonExclude;
import org.acme.orders.job.internal.Job;
import org.acme.orders.order.internal.Order;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "order_job_seq", allocationSize = 1)
@Table(name = "WO_JOB")
public class OrderJob extends BaseEntity {
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDER_ID")
	@JsonBackReference
	@GsonExclude
	@JsonIgnore
	private Order order;
	@ManyToOne
	@JoinColumn(name = "JOB_ID")
	private Job job;
	private int quantity;
	private Character activeStatus;
	private String appliedRule;

	@Override
	public OrderJob clone() throws CloneNotSupportedException {
		OrderJob clon = new OrderJob();
		clon.setActiveStatus(Constants.YES);
		clon.setQuantity(this.quantity);
		clon.setAppliedRule(this.appliedRule);
		clon.setOrder(this.order);
		clon.setJob(this.job);
		return clon;
	}
}