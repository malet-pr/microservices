package org.acme.rules.drools.internal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "rule_type_seq", allocationSize = 1)
@Table(name = "RULE_TYPE")
public class RuleType extends BaseEntity{
    @NotNull
    @Column(unique=true)
    private String name;
    private String code;
    @NotNull
    private String header;
    @NotNull
    @Column(unique=true)
    private String grouping;
    private int priority;
}
