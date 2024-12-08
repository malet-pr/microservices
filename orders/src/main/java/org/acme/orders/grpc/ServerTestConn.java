package org.acme.orders.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ServerTestConn extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void test(TestGo request, StreamObserver<TestBack> responseObserver){
        TestBack msg = TestBack.newBuilder().setMsgIn("hello from work-order service").build();
        responseObserver.onNext(msg);
        responseObserver.onCompleted();
    }
}
