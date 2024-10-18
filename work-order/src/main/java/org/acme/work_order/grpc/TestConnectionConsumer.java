package org.acme.work_order.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.acme.work_order.grpc.TestServiceGrpc;
import org.acme.work_order.grpc.TestServiceGrpc.TestServiceBlockingStub;
import org.acme.work_order.grpc.TestGo;
import org.acme.work_order.grpc.TestBack;
import io.grpc.stub.StreamObserver;

@Service
public class TestConnectionConsumer extends TestServiceGrpc.TestServiceImplBase {

    @GrpcClient("rules-service")
    TestServiceGrpc.TestServiceBlockingStub synchronousClient;

    public TestBack testConnection(TestGo go) {
        TestBack resp = synchronousClient.test(go);
        System.out.println("Response: " + resp);
        return resp;
    }

}
