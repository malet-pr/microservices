package acme.example.work_order.workorder.internal;

import java.time.LocalDateTime;
import java.util.List;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.jobtype.internal.JobType;
import acme.example.work_order.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(name = "default_generator", sequenceName = "work_order_seq", allocationSize = 1)
@Table(name = "WORK_ORDER")
public class WorkOrder extends BaseEntity {
    @NotNull
    @Column(unique=true)
    private String woNumber;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "WORK_ORDER_ID")
    private List<WorkOrderJob> jobs;
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
    private String appliedRule;

    @Override
    public String toString() {
        return woNumber;
    }
    
}
