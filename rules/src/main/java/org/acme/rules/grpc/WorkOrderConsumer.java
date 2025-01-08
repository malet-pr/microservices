package org.acme.rules.grpc;


import net.devh.boot.grpc.client.inject.GrpcClient;
import org.acme.rules.grpc.woserviceconnect.Order;
import org.acme.rules.grpc.woserviceconnect.WoDataToGrpcWorkOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkOrderConsumer extends org.acme.rules.grpc.OrderServiceGrpc.OrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderConsumer.class);

    @Autowired
    private WoDataToGrpcWorkOrder mapper;

    @GrpcClient("rules-service")
    OrderServiceGrpc.OrderServiceBlockingStub synchronousClient;

    public OrderRequest convertDtoToRequest(Order dto) {
        OrderRequest request = mapper.dtoToGrpc(dto);
        log.info("Mapped WO {} to request", dto.getWoNumber());
        return request;
    }

    public void callWorkOrder(Order dto) {
        log.info("Sending WO {} to rules service", dto.getWoNumber());
        OrderRequest request = convertDtoToRequest(dto);
        synchronousClient.woWithRules(request);
    }

}
