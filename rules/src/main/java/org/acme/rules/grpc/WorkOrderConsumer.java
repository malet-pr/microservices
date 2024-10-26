package org.acme.rules.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.acme.rules.drools.WorkOrderData;
import org.acme.rules.grpc.woserviceconnect.WoDataToGrpcWorkOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderConsumer extends org.acme.rules.grpc.WorkOrderServiceGrpc.WorkOrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderConsumer.class);

    @Autowired
    private WoDataToGrpcWorkOrder mapper;

    @GrpcClient("rules-service")
    org.acme.rules.grpc.WorkOrderServiceGrpc.WorkOrderServiceBlockingStub synchronousClient;

    public org.acme.rules.grpc.WorkOrderRequest convertDtoToRequest(WorkOrderData dto) {
        org.acme.rules.grpc.WorkOrderRequest request = mapper.dtoToGrpc(dto);
        log.info("Mapped WO {} to request", dto.getWoNumber());
        return request;
    }

    public WorkOrderData convertResponseToData(org.acme.rules.grpc.WorkOrderResponse response) {
        WorkOrderData dto = mapper.grpcToDto(response);
        log.info("Mapped WO {} to response", response.getWoNumber());
        return dto;
    }

    public Boolean callWorkOrder(WorkOrderData dto) {
        log.info("Sending WO {} to rules service", dto.getWoNumber());
        org.acme.rules.grpc.WorkOrderRequest request = convertDtoToRequest(dto);
        org.acme.rules.grpc.WorkOrderResponse response = synchronousClient.runRulesToWO(request);
        return response != null;
    }


}
