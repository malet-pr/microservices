package org.acme.rules.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.acme.rules.grpc.woserviceconnect.WoDtoToGrpcWorkOrder;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.acme.rules.grpc.WorkOrderRequest;
import org.acme.rules.grpc.WorkOrderResponse;
import org.acme.rules.grpc.WorkOrderServiceGrpc;

@Service
public class WorkOrderConsumer extends WorkOrderServiceGrpc.WorkOrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderConsumer.class);

    @Autowired
    private WoDtoToGrpcWorkOrder mapper;

    @GrpcClient("rules-service")
    WorkOrderServiceGrpc.WorkOrderServiceBlockingStub synchronousClient;

    public WorkOrderRequest convertDtoToRequest(WorkOrderDTO dto) {
        WorkOrderRequest request = mapper.dtoToGrpc(dto);
        log.info("Mapped WO {} to request", dto.getWoNumber());
        return request;
    }

    public WorkOrderDTO convertResponseToDto(WorkOrderResponse response) {
        WorkOrderDTO dto = mapper.grpcToDto(response);
        log.info("Mapped WO {} to response", response.getWoNumber());
        return dto;
    }

    public WorkOrderDTO callWorkOrder(WorkOrderDTO dto) {
        log.info("Sending WO {} to rules service", dto.getWoNumber());
        WorkOrderRequest request = convertDtoToRequest(dto);
        //WorkOrderRequest request = this.forTesting();
        WorkOrderResponse response = synchronousClient.runRulesToWO(request);
        return convertResponseToDto(response);
    }

    private WorkOrderRequest forTesting(){
        return WorkOrderRequest.newBuilder()
                .setHasRules(false)
                .build();
    }

}
