package org.acme.rules.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.acme.rules.grpc.TestServiceGrpc.TestServiceImplBase;
import org.acme.rules.grpc.TestGo;
import org.acme.rules.grpc.TestBack;

@GrpcService
public class TestConnectionServer extends TestServiceImplBase {

    @Override
    public void test(TestGo request, StreamObserver<TestBack> responseObserver){
        TestBack msg = TestBack.newBuilder().setMsgIn("hello from rules").build();
        responseObserver.onNext(msg);
        responseObserver.onCompleted();
    }

}
