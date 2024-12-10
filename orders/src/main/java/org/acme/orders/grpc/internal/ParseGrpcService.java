package org.acme.orders.grpc.internal;

import com.google.protobuf.Timestamp;
import org.acme.orders.grpc.OrderRequest;
import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Service
public class ParseGrpcService {

    private static final Logger log = LoggerFactory.getLogger(ParseGrpcService.class);
    final WoJobDtoToGrpcWoJob woJob;
    final OrderService woService;

    public ParseGrpcService(WoJobDtoToGrpcWoJob woJob, OrderService woService) {
        this.woJob = woJob;
        this.woService = woService;
    }


    public boolean processWorkOrder(OrderRequest request) {
        boolean success = Boolean.FALSE;
        try{
            OrderDTO dto = this.grpcToDto(request);
            if (dto != null) {
                log.info("The response sent by rules for work order {} was correctly parsed.", dto.getWoNumber());
                success = woService.updateAfterRules(dto);
                log.info("Changes to WO {} were saved: {}", dto.getWoNumber(),success);
            }
        } catch (Exception e){
            log.error("Error parsing request\nMessage: {}", e.getMessage());
        }
        return success;
    }

    public OrderDTO grpcToDto(OrderRequest grpc) {
        return OrderDTO.builder()
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

    private LocalDateTime convertToLocalDateTime(Timestamp ts) {
        Instant instant = Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
