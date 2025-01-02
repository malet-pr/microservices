package org.acme.simulator.unit;

import nl.altindag.log.LogCaptor;
import org.acme.simulator.simulations.Simulations;
import org.acme.simulator.simulations.internal.Order;
import org.acme.simulator.simulations.internal.OrderSimulator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.RedisConnectionFailureException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static net.minidev.json.JSONValue.isValidJson;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimulationsTest {

    LogCaptor logCaptor = LogCaptor.forClass(Simulations.class);

    @Mock
    RedisTemplate<String, String> redisTemplateMock;

    @Mock
    private ValueOperations<String, String> valueOperationsMock;

    @Mock
    private OrderSimulator simuMock;

    @InjectMocks
    private Simulations simulations;

    @Test
    @DisplayName("Test creation of order number when redis is available")
    void getOrderNumberTest_success(){
        // Arrange
        String source = "S1";
        String lastOrder = "J-0000000001";
        String newOrder = "J-0000000002";
        when(redisTemplateMock.opsForValue()).thenReturn(valueOperationsMock);
        when(valueOperationsMock.get(source)).thenReturn(lastOrder);
        // Act
        String result = simulations.getOrderNumber(source);
        // Assert
        Assertions.assertTrue(result.matches("J-\\d{10}"), "Order number format is incorrect");
        Assertions.assertEquals(newOrder,result,"Order numbers do not match");
        Mockito.verify(redisTemplateMock.opsForValue(), Mockito.times(1)).get(source);
        Mockito.verify(redisTemplateMock.opsForValue(), Mockito.times(1)).set(source, newOrder);
    }

    @Test
    @DisplayName("Test an exception will be thrown when the source is not a valid key in redis")
    void getOrderNumberTest_invalidSource() {
        // Arrange
        String source = "S1";
        Mockito.when(redisTemplateMock.opsForValue()).thenReturn(valueOperationsMock);
        Mockito.when(valueOperationsMock.get(source)).thenReturn(null);
        // Act
        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> simulations.getOrderNumber(source));
        // Assert
        Assertions.assertEquals("Key does not exist in Redis", exception.getMessage());
        Assertions.assertTrue(logCaptor.getWarnLogs().contains("Key 'S1' does not exist in Redis"));
    }

    @Test
    @DisplayName("Test for Redis unavailability")
    void getOrderNumberTest_redisUnavailable() {
        // Arrange
        String source = "S1";
        Mockito.when(redisTemplateMock.opsForValue())
                .thenThrow(new RedisConnectionFailureException("Redis is unavailable"));
        // Act
        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> simulations.getOrderNumber(source));
        // Assert
        Assertions.assertTrue(exception.getMessage().contains("Redis is unavailable"));
        Assertions.assertTrue(logCaptor.getErrorLogs().contains("Redis is unavailable"));
    }

    static Stream<Arguments> provideSimulationTestData() {
        return Stream.of(
                Arguments.of(1, Map.of("A259HT4", 1)),
                Arguments.of(2, Map.of("A259HT4", 1, "F037NJ6", 1)),
                Arguments.of(5, Map.of("A259HT4", 3, "D703TV2", 1, "F037NJ6", 1)),
                Arguments.of(10, Map.of("A259HT4", 5, "D703TV2", 3, "F037NJ6", 2))
        );
    }

    @ParameterizedTest
    @MethodSource("provideSimulationTestData")
    @DisplayName("Tests simulation of work orders with different quantities")
    void simulateWorkOrdersTest(int quantity, Map<String, Integer> expectedCounts) {
        // Arrange
        when(redisTemplateMock.opsForValue()).thenReturn(valueOperationsMock);
        expectedCounts.forEach((jobType, count) -> {
            String sourceKey = switch (jobType) {
                case "A259HT4" -> "S1";
                case "D703TV2" -> "S2";
                case "F037NJ6" -> "S3";
                default -> throw new IllegalArgumentException("Unexpected job type: " + jobType);
            };
            when(valueOperationsMock.get(sourceKey)).thenReturn(sourceKey + "-0000000001");
            when(simuMock.simulate(sourceKey + "-0000000002"))
                    .thenReturn(Order.builder().jobType(jobType).build());
        });
        // Act
        List<Order> orders = simulations.simulateWorkOrders(quantity);
        // Assert
        Assertions.assertEquals(quantity, orders.size(), "Order count mismatch");
        Assertions.assertTrue(logCaptor.getInfoLogs().contains("Simulating " + quantity + " work orders ... "));
        Map<String, Long> actualCounts = orders.stream()
                .collect(Collectors.groupingBy(Order::getJobType, Collectors.counting()));
        expectedCounts.forEach((jobType, count) ->
                Assertions.assertEquals(count.longValue(), actualCounts.getOrDefault(jobType, 0L),
                        "Mismatch for job type: " + jobType));
    }

    @ParameterizedTest
    @DisplayName("Tests preparation of Kafka messages with various quantities")
    @ValueSource(ints = {1, 2, 5, 10})
    void prepareKafkaMessagesTest(int quantity){
        // Arrange
        Simulations simuSpy = Mockito.spy(simulations);
        when(redisTemplateMock.opsForValue()).thenReturn(valueOperationsMock);
        when(valueOperationsMock.get(anyString())).thenReturn("test-1");
        // Act
        String msg = simuSpy.prepareKafkaMessages(quantity);
        // Assert
        Mockito.verify(simuSpy,Mockito.times(1)).simulateWorkOrders(quantity);
        Assertions.assertNotNull(msg);
        Assertions.assertTrue(isValidJson(msg));
    }

}
