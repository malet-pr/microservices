package org.acme.rules.grpc.woserviceconnect;

import org.acme.rules.grpc.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Component
public class WoDataToGrpcWorkOrder {

    private static final Logger log = LoggerFactory.getLogger(WoDataToGrpcWorkOrder.class);

    public OrderRequest dtoToGrpc(Order dto) {
        log.info("Converting DTO for WO: {}", dto.getWoNumber());
        OrderRequest wor = OrderRequest.newBuilder()
                .setWoNumber(dto.getWoNumber())
                //.addAllWoJobs(dto.getWoJobs())
                .setJobTypeCode(dto.getJobTypeCode())
                .setAddress(dto.getAddress())
                .setCity(dto.getCity())
                .setState(dto.getState())
                .setWoCreationDate(convertToTimestamp(dto.getWoCreationDate()))
                .setWoCompletionDate(convertToTimestamp(dto.getWoCompletionDate()))
                .setClientId(dto.getClientId())
                .setHasRules(Boolean.TRUE)
                .build();
        log.info("Converted DTO for WO {} succeeded: {}", dto.getWoNumber(), wor != null);
        return wor;
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
