package org.acme.work_order.grpc;

import io.grpc.stub.StreamObserver;
import org.acme.work_order.grpc.internal.ParseGrpcService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
public class GrpcFlowTest {

    @InjectMocks
    private ServerWoRules woRulesServer;

    @Mock
    private StreamObserver<com.google.protobuf.Empty> respObsMock;

    @Mock
    private ParseGrpcService serviceMock;

    @SpyBean
    private ParseGrpcService parseGrpcService;

    @Test
    @DisplayName("Test that when gRPC request is received it's send to process and returns a boolean response")
    void woWithRulesTest() throws Exception {
        // Arrange
        Mockito.when(serviceMock.processWorkOrder(TestData.req1)).thenReturn(Boolean.TRUE);
        // Act
        woRulesServer.woWithRules(TestData.req1, respObsMock);
        // Assert
        Mockito.verify(respObsMock, Mockito.times(1)).onCompleted();
        Mockito.verify(serviceMock, Mockito.times(1)).processWorkOrder(TestData.req1);
    }

    @Test
    @DisplayName("Test that the gRPC request message is parsed correctly into a WorkOrderDTO")
    void parseRequest_success() throws Exception {
        // Arrange
        // Act
        parseGrpcService.processWorkOrder(TestData.req1);
        // Assert
        Mockito.verify(parseGrpcService, Mockito.times(1)).grpcToDto(TestData.req1);
        Assertions.assertEquals(TestData.dto1,parseGrpcService.grpcToDto(TestData.req1),"The dtos are not equal");
    }


}
