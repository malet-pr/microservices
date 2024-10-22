package org.acme.rules.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.acme.rules.grpc.TestServiceGrpc;
import org.acme.rules.grpc.TestServiceGrpc.TestServiceBlockingStub;
import org.acme.rules.grpc.TestGo;
import org.acme.rules.grpc.TestBack;
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
