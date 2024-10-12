package acme.example.work_order.job.internal;

import acme.example.work_order.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "job_seq", allocationSize = 1)
@Table(name = "JOB")
public class Job extends BaseEntity{
	@NotNull
	@Column(unique=true)
	private String code;
	private String name;
	private Character activeStatus;
}

