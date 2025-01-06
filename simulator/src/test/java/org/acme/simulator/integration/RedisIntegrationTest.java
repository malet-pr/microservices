package org.acme.simulator.integration;

import nl.altindag.log.LogCaptor;
import org.acme.simulator.simulations.Simulations;
import org.acme.simulator.simulations.internal.OrderSimulator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class RedisIntegrationTest {

    LogCaptor logCaptor;

    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis")
            .withExposedPorts(6379);

    @Autowired
    private static RedisTemplate<String, String> redisTemplate;

    @Mock
    RedisTemplate<String, String> redisMock;

    @Mock
    ValueOperations<String, String> valueOps;

    @Autowired
    OrderSimulator simu;

    @InjectMocks
    private Simulations simulations;

    @BeforeAll
    static void setUp() {
        // Create a new RedisStandaloneConfiguration using the Testcontainers host and port
        String redisHost = redisContainer.getHost();
        Integer redisPort = redisContainer.getMappedPort(6379);
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfig);
        connectionFactory.afterPropertiesSet();
        // Set up the RedisTemplate
        redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
    }

    @BeforeEach
    void beforeEach() {
        logCaptor = LogCaptor.forClass(Simulations.class);
    }

    @AfterEach
    void cleanUp() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }


    @Test
    @DisplayName("Test redis connection (with testcontainers)")
    void testRedisTemplateOperations() {
        // Arrange
        String key = "S1";
        String value = "J-0000000005";
        // Act
        redisTemplate.opsForValue().set(key, value);
        String result = redisTemplate.opsForValue().get(key);
        // Assert
        assertThat(result).isEqualTo(value);
    }

    @Test
    @DisplayName("Test the correct order number is generated when the key exists in redis")
    void testGetOrderNumber_success() {
        // Arrange
        Map<String, String> redisData = new HashMap<>();
        redisData.put("S1", "J-0000000005");
        Mockito.when(redisMock.opsForValue()).thenReturn(valueOps);
        Mockito.doAnswer(invocation -> redisData.get(invocation.getArgument(0)))
                .when(valueOps).get(Mockito.anyString());
        Mockito.doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            String value = invocation.getArgument(1);
            redisData.put(key, value);
            return null;
        }).when(valueOps).set(Mockito.anyString(), Mockito.anyString());
        // Act
        String orderNumber = simulations.getOrderNumber("S1");
        // Assert
        Assertions.assertNotNull(orderNumber);
        Assertions.assertEquals("J-0000000006", orderNumber);
        Assertions.assertEquals(orderNumber,redisMock.opsForValue().get("S1"));
    }

    @Test
    @DisplayName("Test no order number is generated when the key doesn't exist in redis")
    void testGetOrderNumber_failure() {
        // Arrange
        Map<String, String> redisData = new HashMap<>();
        redisData.put("S1", "J-0000000005");
        Mockito.when(redisMock.opsForValue()).thenReturn(valueOps);
        Mockito.doAnswer(invocation -> redisData.get(invocation.getArgument(0)))
                .when(valueOps).get(Mockito.anyString());
        Mockito.doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            String value = invocation.getArgument(1);
            redisData.put(key, value);
            return null;
        }).when(valueOps).set(Mockito.anyString(), Mockito.anyString());
        // Act
        RuntimeException exception = Assertions
                .assertThrows(RuntimeException.class, () -> simulations.getOrderNumber("S2"));
        // Assert
        Assertions.assertEquals("Key does not exist in Redis", exception.getMessage());
        Assertions.assertTrue(logCaptor.getWarnLogs().contains("Key 'S2' does not exist in Redis"));
    }


}



