package acme.example.work_order.jobtype.internal;

import acme.example.work_order.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "job_type_seq", allocationSize = 1)
@Table(name = "JOB_TYPE")
public class JobType extends BaseEntity {
    @NotNull
    @Column(unique=true)
    private String code;
    private String name;
    private Character activeStatus;
    private String clientType;
}
