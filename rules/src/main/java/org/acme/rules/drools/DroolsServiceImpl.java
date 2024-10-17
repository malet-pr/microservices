package org.acme.rules.drools;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.acme.rules.grpc.WorkOrderRequest;
import org.acme.rules.grpc.WorkOrderResponse;
import org.acme.rules.grpc.WorkOrderServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class DroolsServiceImpl extends WorkOrderServiceGrpc.WorkOrderServiceImplBase {

    @Autowired
    private WorkOrderRulesService workOrderRulesService;

    @Override
    public void runRulesToWO(WorkOrderRequest request, StreamObserver<WorkOrderResponse> responseObserver) {
        WorkOrderResponse response = workOrderRulesService.processWorkOrder(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}