package org.acme.orders.jobtype.internal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.orders.common.BaseEntity;

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
