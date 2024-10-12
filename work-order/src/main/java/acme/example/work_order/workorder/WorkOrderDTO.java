package acme.example.work_order.workorder;

import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderDTO implements Serializable {
    private String woNumber;
    private List<WorkOrderJobDTO> woJobDTOs;
    private String jobTypeCode;
    private String address;
    private String city;
    private String state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCreationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime woCompletionDate;
    private String clientId;
    private String appliedRule;
    
}
