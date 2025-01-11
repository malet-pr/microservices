package org.acme.rules.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TestConnectionConsumer extends TestServiceGrpc.TestServiceImplBase {

    Logger log = LoggerFactory.getLogger(TestConnectionConsumer.class);

    @GrpcClient("rules-service")
    TestServiceGrpc.TestServiceBlockingStub synchronousClient;

    public TestBack testConnection(TestGo go) {
        TestBack resp = synchronousClient.test(go);
        log.info("Response: {}", resp);
        return resp;
    }

}
