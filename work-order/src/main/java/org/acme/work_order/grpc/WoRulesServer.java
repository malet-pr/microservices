package org.acme.work_order.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.acme.work_order.grpc.internal.WorkOrderRulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.acme.work_order.grpc.WorkOrderServiceGrpc;
import org.acme.work_order.grpc.WorkOrderRequest;
import org.acme.work_order.grpc.WorkOrderResponse;


@GrpcService
public class WoRulesServer extends WorkOrderServiceGrpc.WorkOrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(WoRulesServer.class);

    @Autowired
    private WorkOrderRulesService workOrderRulesService;

    @Override
    public void runRulesToWO(WorkOrderRequest request, StreamObserver<WorkOrderResponse> responseObserver) {
        log.info("Request appliedRule in rules service: {}", request.getHasRules());
        WorkOrderResponse response = workOrderRulesService.processWorkOrder(request);
        log.info("Response appliedRule in rules service: {}", response.getHasRules());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}

