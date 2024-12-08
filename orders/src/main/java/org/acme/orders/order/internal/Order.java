package org.acme.orders.order.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.acme.orders.common.BaseEntity;
import org.acme.orders.jobtype.internal.JobType;
import org.acme.orders.orderjob.internal.OrderJob;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "work_order_seq", allocationSize = 1)
@Table(name = "WORK_ORDER")
public class Order extends BaseEntity {
    @NotNull
    @Column(unique=true)
    private String woNumber;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "WORK_ORDER_ID")
    private List<OrderJob> jobs;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "JOBTYPE_ID")
    private JobType jobType;
    private String address;
    private String city;
    private String state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCreationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCompletionDate;
    private String clientId;
    private Boolean hasRules;

    @Override
    public String toString() {
        return woNumber;
    }
    
}
