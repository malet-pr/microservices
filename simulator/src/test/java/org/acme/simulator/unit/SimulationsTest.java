package org.acme.simulator.unit;

import nl.altindag.log.LogCaptor;
import org.acme.simulator.simulations.Simulations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.RedisConnectionFailureException;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimulationsTest {

    LogCaptor logCaptor = LogCaptor.forClass(Simulations.class);

    @Mock
    RedisTemplate<String, String> redisTemplateMock;

    @Mock
    private ValueOperations<String, String> valueOperationsMock;

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



}
