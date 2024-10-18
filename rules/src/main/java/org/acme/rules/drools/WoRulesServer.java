package org.acme.rules.drools;

import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.acme.rules.grpc.WorkOrderServiceGrpc;
import org.acme.rules.grpc.WorkOrderRequest;
import org.acme.rules.grpc.WorkOrderResponse;

@GrpcService
public class WoRulesServer extends WorkOrderServiceGrpc.WorkOrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(WoRulesServer.class);

    @Autowired
    private WorkOrderRulesService workOrderRulesService;

    @Override
    public void runRulesToWO(WorkOrderRequest request, StreamObserver<WorkOrderResponse> responseObserver) {
        log.info("Request appliedRule in rules service: {}", request.getAppliedRule());
        WorkOrderResponse response = workOrderRulesService.processWorkOrder(request);
        log.info("Response appliedRule in rules service: {}", response.getAppliedRule());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}