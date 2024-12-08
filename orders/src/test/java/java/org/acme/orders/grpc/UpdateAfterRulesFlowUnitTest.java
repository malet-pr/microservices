package java.org.acme.orders.grpc;

import io.grpc.stub.StreamObserver;
import org.acme.orders.grpc.ServerWoRules;
import org.acme.orders.grpc.internal.ParseGrpcService;
import org.acme.orders.job.JobService;
import org.acme.orders.order.OrderService;
import org.acme.orders.order.internal.OrderDAO;
import org.acme.orders.orderjob.OrderJobService;
import org.acme.orders.orderjob.UpdatesService;
import org.acme.orders.orderjob.internal.OrderJobDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateAfterRulesFlowUnitTest {

    @InjectMocks
    private ServerWoRules woRulesServer;

    @Mock
    private StreamObserver<com.google.protobuf.Empty> respObsMock;

    @Mock
    private ParseGrpcService serviceMock;

    @SpyBean
    private OrderService woService;

    @Mock
    private OrderDAO woDAO;

    @Mock
    private OrderJobService wjService;

    @Mock
    private OrderJobDAO wjDAO;

    @SpyBean
    private UpdatesService updService;

    @SpyBean
    private ParseGrpcService parseGrpcService;

    @SpyBean
    JobService jobService;

    /*
    @Test
    @DisplayName("Test that when gRPC request is received it's send to process and returns a boolean response")
    void woWithRulesTest(){
        // Arrange
        when(serviceMock.processWorkOrder(TestData.req1)).thenReturn(Boolean.TRUE);
        // Act
        woRulesServer.woWithRules(TestData.req1, respObsMock);
        // Assert
        Mockito.verify(respObsMock, Mockito.times(1)).onCompleted();
        Mockito.verify(serviceMock, Mockito.times(1)).processWorkOrder(TestData.req1);
    }

    @Test
    @DisplayName("Test that the gRPC request message is parsed correctly into a OrderDTO")
    void parseRequest_success(){
        // Arrange
        // Act
        parseGrpcService.processWorkOrder(TestData.req1);
        // Assert
        Mockito.verify(parseGrpcService, Mockito.times(1)).grpcToDto(TestData.req1);
        Assertions.assertEquals(TestData.dto1,parseGrpcService.grpcToDto(TestData.req1),"The dtos are not equal");
    }

    @Test
    @DisplayName("Test the response when a gRPC request message is not parsed correctly into a OrderDTO")
    void parseRequest_failure(){
        // Arrange
        // Act
        boolean success = parseGrpcService.processWorkOrder(TestData.reqFail);
        // Assert
        Mockito.verify(parseGrpcService, Mockito.times(1)).grpcToDto(TestData.reqFail);
        Assertions.assertFalse(success);
    }

    @Test
    @DisplayName("Test that the work order sent to be updated")
    void workOrderSentToBeUpdated(){
        // Arrange
        when(parseGrpcService.grpcToDto(TestData.req1)).thenReturn(TestData.dto1);
        // Act
        parseGrpcService.processWorkOrder(TestData.req1);
        // Assert
        Mockito.verify(woService, Mockito.times(1)).updateAfterRules(TestData.dto1);
    }

    @Test
    @DisplayName("Test that the work order updates correctly")
    void updateAfterRules_success(){
        // Arrange
        when(wjDAO.rulesUpdate(Mockito.anyInt(),Mockito.anyString(),Mockito.anyChar(),
                Mockito.anyLong(),Mockito.anyLong())).thenReturn(1);

        //when(jobService.findIdByCode(Mockito.anyString())).thenReturn(1L);
        // Act
        updService.updateAfterRules(TestData.dto1,TestData.wo1);
        // Assert
        Mockito.verify(jobService,Mockito.times(1)).findIdByCode(Mockito.anyString());
        Mockito.verify(wjDAO,Mockito.times(1))
                .rulesUpdate(3,"A1",'N', Mockito.anyLong(),1L);
        Mockito.verify(wjService,Mockito.times(1)).saveAll(List.of(TestData.WOJob2));
        Mockito.verify(woDAO,Mockito.times(1)).updateHasRule(Mockito.anyString());
    }

    @Test
    @DisplayName("Test that ")
    void updateAfterRules_failure(){
        Assertions.assertFalse(Boolean.FALSE);
    }
    */

}

// jobService.findIdByCode(v.getJobCode())  x1
// woJobDAO.rulesUpdate(v.getQuantity(),v.getAppliedRule(),v.getActiveStatus(),oldWO.getId(),jobId) x1
// woJobService.saveAll(map2.values().stream().toList()) x1
// woDAO.updateHasRule(newDto.getWoNumber())  x1