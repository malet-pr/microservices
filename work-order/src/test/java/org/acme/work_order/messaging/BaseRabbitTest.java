package org.acme.work_order.messaging;

import org.acme.work_order.WorkOrderApplication;
import org.acme.work_order.rabbitmq.RabbitMessageSender;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest(classes = WorkOrderApplication.class, properties = "spring.profiles.active=test")
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(classes = BaseRabbitTest.RabbitMQTestConfig.class)
class BaseRabbitTest {

        @Container
        static final RabbitMQContainer rabbit = new RabbitMQContainer(DockerImageName.parse("rabbitmq:management"))
                .withExposedPorts(5672, 15672);

        @DynamicPropertySource
        static void rabbitProperties(DynamicPropertyRegistry registry) {
            registry.add("spring.rabbitmq.host", rabbit::getHost);
            registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
            registry.add("spring.rabbitmq.username", () -> "guest");
            registry.add("spring.rabbitmq.password", () -> "guest");
        }
        @Configuration
        static class RabbitMQTestConfig {

            @Bean
            public CachingConnectionFactory rabbitConnectionFactory(
                    @Value("${spring.rabbitmq.host}") String host,
                    @Value("${spring.rabbitmq.port}") int port,
                    @Value("${spring.rabbitmq.username}") String username,
                    @Value("${spring.rabbitmq.password}") String password) {
                CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
                connectionFactory.setUsername(username);
                connectionFactory.setPassword(password);
                return connectionFactory;
            }

            @Bean
            public Queue testQueue() {
                return new Queue("test-queue", false);
            }

            @Bean
            public Queue workOrderQueue() {
                return new Queue("work-order-queue", false);
            }

            @Bean
            public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
                RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
                rabbitAdmin.declareQueue(testQueue());
                rabbitAdmin.declareQueue(workOrderQueue());
                return rabbitAdmin;
            }

            @Bean
            public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
                return new RabbitTemplate(connectionFactory);
            }

            @Bean
            public RabbitMessageSender rabbitMessageSender() {
                return new RabbitMessageSender();
            }
        }

        @BeforeAll
        static void setup() {
            rabbit.start();
        }

        @AfterAll
        static void teardown(){
            rabbit.stop();
        }


}
