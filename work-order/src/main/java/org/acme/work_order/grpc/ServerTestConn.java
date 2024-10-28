package org.acme.work_order.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.acme.work_order.grpc.TestServiceGrpc.TestServiceImplBase;

@GrpcService
public class ServerTestConn extends TestServiceImplBase {

    @Override
    public void test(TestGo request, StreamObserver<TestBack> responseObserver){
        TestBack msg = TestBack.newBuilder().setMsgIn("hello from work-order service").build();
        responseObserver.onNext(msg);
        responseObserver.onCompleted();
    }

}
