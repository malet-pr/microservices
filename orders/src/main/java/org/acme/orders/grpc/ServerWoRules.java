package org.acme.orders.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.acme.orders.grpc.internal.ParseGrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@GrpcService
public class ServerWoRules extends OrderServiceGrpc.OrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(ServerWoRules.class);

    @Autowired
    private ParseGrpcService parseGrpcService;

    public void woWithRules(OrderRequest request, StreamObserver<Empty> responseObserver) {
        log.info("Rules applied to WO: {}", request.getWoNumber());
        parseGrpcService.processWorkOrder(request);
        responseObserver.onCompleted();
    }
}

