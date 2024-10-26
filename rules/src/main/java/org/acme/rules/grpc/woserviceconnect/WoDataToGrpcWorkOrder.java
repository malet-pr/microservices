package org.acme.rules.grpc.woserviceconnect;

import org.acme.rules.drools.WorkOrderData;
import org.acme.rules.grpc.WorkOrderRequest;
import org.acme.rules.grpc.WorkOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Component
public class WoDataToGrpcWorkOrder {

    private static final Logger log = LoggerFactory.getLogger(WoDataToGrpcWorkOrder.class);


    public WorkOrderRequest dtoToGrpc(WorkOrderData dto) {
        log.info("Converting DTO for WO: {}", dto.getWoNumber());
        WorkOrderRequest wor = WorkOrderRequest.newBuilder()
                .setWoNumber(dto.getWoNumber())
                //.addAllWoJobs(woJob.convertListToGrpc(dto.getWoJobs()))
                .setJobTypeCode(dto.getJobTypeCode())
                .setAddress(dto.getAddress())
                .setCity(dto.getCity())
                .setWoCreationDate(convertToTimestamp(dto.getWoCreationDate()))
                .setWoCompletionDate(convertToTimestamp(dto.getWoCompletionDate()))
                .setClientId(dto.getClientId())
                .setHasRules(Boolean.TRUE)
                .build();
        log.info("Converted DTO for WO {} succeeded: {}", dto.getWoNumber(), wor != null);
        return wor;
    }
    public WorkOrderData grpcToDto(WorkOrderResponse grpc) {
        return WorkOrderData.builder()
                .woNumber(grpc.getWoNumber())
                //.woJobs(grpc.getWoJobsList())
                .jobTypeCode(grpc.getJobTypeCode())
                .address(grpc.getAddress())
                .city(grpc.getCity())
                .woCreationDate(convertToLocalDateTime(grpc.getWoCreationDate()))
                .woCompletionDate(convertToLocalDateTime(grpc.getWoCompletionDate()))
                .clientId(grpc.getClientId())
                //.hasRules(grpc.getHasRules())
                .build();
    }

    private Timestamp convertToTimestamp(LocalDateTime ldt) {
        Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    private LocalDateTime convertToLocalDateTime(Timestamp ts) {
        Instant instant = Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
