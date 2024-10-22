package org.acme.rules.grpc.woserviceconnect;

import org.acme.rules.grpc.WoJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class WoJobDtoToGrpcWoJob {

    private static final Logger log = LoggerFactory.getLogger(WoDtoToGrpcWorkOrder.class);

    public WorkOrderJobDTO convertToDto(WoJob woJob) {
        return WorkOrderJobDTO.builder()
                .woNumber(woJob.getWoNumber())
                .jobCode(woJob.getJobCode())
                .quantity(woJob.getQuantity())
                .activeStatus(woJob.getActiveStatus().charAt(0))
                .appliedRule(woJob.getAppliedRule())
                .build();
    }

    public List<WorkOrderJobDTO> convertListToDto(List<WoJob> woJobs) {
        List<WorkOrderJobDTO> dtos = new ArrayList<>();
        woJobs.forEach(job -> dtos.add(convertToDto(job)));
        return dtos;
    }

    public WoJob convertToGrpc(WorkOrderJobDTO dto) {
        log.info("converting: {}", dto);
        WoJob wo = WoJob.newBuilder()
                .setWoNumber(dto.getWoNumber())
                .setJobCode(dto.getJobCode())
                .setQuantity(dto.getQuantity())
                .setActiveStatus(String.valueOf(dto.getActiveStatus()))
                .setAppliedRule(dto.getAppliedRule() != null ? dto.getAppliedRule().toUpperCase() : "")
                .build();
        return wo;
    }

    public List<WoJob> convertListToGrpc(List<WorkOrderJobDTO> dtos) {
        List<WoJob> woJobs = new ArrayList<>();
        log.info("Converting List to Grpc");
        dtos.forEach(dto -> woJobs.add(convertToGrpc(dto)));
        log.info("Converted List");
        return woJobs;
    }


}

