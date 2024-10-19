package org.acme.work_order.grpc;


import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.acme.work_order.grpc.WorkOrderServiceGrpc;
import org.acme.work_order.grpc.WorkOrderResponse;
import org.acme.work_order.grpc.WorkOrderRequest;



@GrpcService
public class WorkOrderConsumer extends WorkOrderServiceGrpc.WorkOrderServiceImplBase {

    @Override
    public void runRulesToWO(WorkOrderRequest request, StreamObserver<WorkOrderResponse> responseObserver) {
        WorkOrderResponse response = WorkOrderResponse.newBuilder()
                .setWoNumber("ABC123")
                .build();
        System.out.println(response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
