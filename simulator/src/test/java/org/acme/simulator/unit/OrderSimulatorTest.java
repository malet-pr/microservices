package org.acme.simulator.unit;

import net.datafaker.Faker;
import org.acme.simulator.simulations.internal.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderSimulatorTest {

    private static final Logger log = LoggerFactory.getLogger(OrderSimulatorTest.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    @Mock
    Random mockedRandom = mock(Random.class);

    @InjectMocks
    OrderSimulator simulator;

    @Test()
    @DisplayName("Test creation of List<OrderJob> for an order")
    void simulateWOJTest(){
        // Arrange
        when(mockedRandom.nextInt(3)).thenReturn(0).thenReturn(1).thenReturn(2);
        when(mockedRandom.nextInt(1, 11)).thenReturn(5).thenReturn(8).thenReturn(2);
        // Act
        List<OrderJob> orderJobs = simulator.simulateWOJ("Test-1",JobType.A259HT4.name(),3);
        // Assert
        Assertions.assertEquals(3, orderJobs.size(),"Wrong number of order jobs");
        Assertions.assertEquals("YY171T", orderJobs.get(0).getJobCode(),"Wrong job code");
        Assertions.assertEquals(5, orderJobs.get(0).getQuantity(),"Wrong quantity");
        Assertions.assertEquals("ZY074P", orderJobs.get(1).getJobCode(),"Wrong job code");
        Assertions.assertEquals(8, orderJobs.get(1).getQuantity(),"Wrong quantity");
        Assertions.assertEquals("CI069K", orderJobs.get(2).getJobCode(),"Wrong job code");
        Assertions.assertEquals(2, orderJobs.get(2).getQuantity(),"Wrong quantity");
    }

    @Test()
    @DisplayName("Test creation of an Order")
    void simulateTest(){
        // Arrange
        when(mockedRandom.nextInt(0,JobType.NAMES.size())).thenReturn(1);
        when(mockedRandom.nextInt(1,5)).thenReturn(1);
        when(mockedRandom.nextInt(5)).thenReturn(2);
        when(mockedRandom.nextInt(1, 11)).thenReturn(5);
        when(mockedRandom.nextInt(1,6)).thenReturn(2);
        when(mockedRandom.nextLong(1,4)).thenReturn(1L);
        // Act
        Order order = simulator.simulate("Test-2");
        // Assert
        Assertions.assertNotNull(order,"did not get any order");
        Assertions.assertEquals("B055BE1",order.getJobType(),"Wrong job type");
        Assertions.assertEquals("PP088M",order.getWoJobDTOs().getFirst().getJobCode(),"Wrong job code");
        Assertions.assertEquals(5,order.getWoJobDTOs().getFirst().getQuantity(),"wrong quantity");
        Assertions.assertNotNull(order.getAddress(),"did not get any address");
        Assertions.assertNotNull(order.getCity(),"did not get any city");
        Assertions.assertNotNull(order.getState(),"did not get any state");
        Assertions.assertNotNull(order.getClientId(),"did not get any clientId");
        Assertions.assertFalse(order.getHasRules(),"has rules should be false");
        Assertions.assertTrue(order.getWoCreationDate() instanceof LocalDateTime,"order.getWoCreationDate() should be LocalDateTime");
        LocalDateTime expectedWoCompletionDate = order.getWoCreationDate().plusDays(1);
        Assertions.assertEquals(expectedWoCompletionDate,order.getWoCompletionDate(),"Wrong completion date");
    }

}
