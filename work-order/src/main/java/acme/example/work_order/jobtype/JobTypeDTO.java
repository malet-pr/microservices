package acme.example.work_order.jobtype;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobTypeDTO {
    private String code;
    private String name;
    private Character activeStatus;
    private String clientType;
}
