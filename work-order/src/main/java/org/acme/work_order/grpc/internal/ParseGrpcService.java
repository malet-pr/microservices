package org.acme.work_order.grpc.internal;

import com.google.protobuf.Timestamp;
import org.acme.work_order.grpc.WorkOrderRequest;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
public class ParseGrpcService {

    private static final Logger log = LoggerFactory.getLogger(ParseGrpcService.class);

    @Autowired
    WoJobDtoToGrpcWoJob woJob;

    // TODO: add more logs
    public boolean processWorkOrder(WorkOrderRequest request) {
        boolean success = Boolean.FALSE;
        try{
            WorkOrderDTO dto = this.grpcToDto(request);
            if (dto != null) {success=Boolean.TRUE;}

            // TODO: call method to save changes

        } catch (Exception e){
            log.error("Error parsing request \n" + e.getMessage());
        }
        return success;
    }

    public WorkOrderDTO grpcToDto(WorkOrderRequest grpc) {
        return WorkOrderDTO.builder()
                .woNumber(grpc.getWoNumber())
                .woJobDTOs(woJob.convertListToDto(grpc.getWoJobsList()))
                .jobTypeCode(grpc.getJobTypeCode())
                .address(grpc.getAddress())
                .city(grpc.getCity())
                .state(grpc.getState())
                .woCreationDate(convertToLocalDateTime(grpc.getWoCreationDate()))
                .woCompletionDate(convertToLocalDateTime(grpc.getWoCompletionDate()))
                .clientId(grpc.getClientId())
                .hasRules(grpc.getHasRules())
                .build();
    }

    /*
    private Timestamp convertToTimestamp(LocalDateTime ldt) {
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
    */

    private LocalDateTime convertToLocalDateTime(Timestamp ts) {
        Instant instant = Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }


}
