package org.acme.work_order.workorderjob.internal;

import org.acme.work_order.common.BaseEntity;
import org.acme.work_order.common.Constants;
import org.acme.work_order.common.GsonExclude;
import org.acme.work_order.job.internal.Job;
import org.acme.work_order.workorder.internal.WorkOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "work_order_job_seq", allocationSize = 1)
@Table(name = "WO_JOB")
public class WorkOrderJob extends BaseEntity {
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "WORK_ORDER_ID")
	@JsonBackReference
	@GsonExclude
	@JsonIgnore
	private WorkOrder workOrder;
	@ManyToOne
	@JoinColumn(name = "JOB_ID")
	private Job job;
	private Integer quantity;
	private Character activeStatus;
	private String appliedRule;

	@Override
	public WorkOrderJob clone() throws CloneNotSupportedException {
		WorkOrderJob clon = new WorkOrderJob();
		clon.setActiveStatus(Constants.YES);
		clon.setQuantity(this.quantity);
		clon.setAppliedRule(this.appliedRule);
		clon.setWorkOrder(this.workOrder);
		clon.setJob(this.job);
		return clon;
	}
}