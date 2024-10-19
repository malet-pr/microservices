package org.acme.work_order.api;

import org.acme.work_order.common.LocalDateTimeTypeAdapter;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorder.internal.WorkOrder;
import org.acme.work_order.workorder.internal.WorkOrderDAO;
import org.acme.work_order.workorderjob.WorkOrderJobDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/sql/woTestData.sql")
public class WorkOrderControllerTest extends BaseApiTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkOrderDAO woDAO;

    @Autowired
    private WorkOrderServiceImpl woService;


    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    static WorkOrderJobDTO woJobDTO1, woJobDTO2;

    @BeforeEach
    void startingData() {
        woJobDTO1 = WorkOrderJobDTO.builder()
                .woNumber("testNumber")
                .jobCode("JobCode1")
                .quantity(2)
                .activeStatus('Y')
                .build();
        woJobDTO2 = WorkOrderJobDTO.builder()
                .woNumber("testNumber")
                .jobCode("JobCode2")
                .quantity(1)
                .activeStatus('Y')
                .build();
    }

    @Test
    @DisplayName("Test successful save of a work order")
    void createWorkOrder_success() throws Exception {
        // Arrange
        WorkOrderDTO woDTO = WorkOrderDTO.builder()
                .woNumber("testNumber")
                .jobTypeCode("type1")
                .woJobDTOs(Arrays.asList(woJobDTO1, woJobDTO2))
                .woCreationDate(LocalDateTime.now().minusDays(3))
                .woCompletionDate(LocalDateTime.now().minusHours(4))
                .address("address1")
                .city("city1")
                .state("state1")
                .clientId("client1")
                .build();
        //Mockito.doNothing().when(woService.runRules(woDTO));
        String json = gson.toJson(woDTO);
        // Act
        mockMvc.perform(post("/workorders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
        WorkOrder wo = woDAO.findByWoNumber("testNumber");
        // Assert
        Assertions.assertNotNull(wo, "WO should not be null");
        Assertions.assertNotNull(wo.getId(), "WO id should not be null");
        Assertions.assertNotNull(wo.getCreationDate(), "WO creation Date should not be null");
        Assertions.assertEquals("client1", wo.getClientId(), "WO Client Id should match");
    }

    @Test
    @DisplayName("Test that WO cannot be saved without a number")
    void createJobTest_failure_noNumber() throws Exception {
        // Arrange
        WorkOrderDTO woDTO = WorkOrderDTO.builder()
                .jobTypeCode("type1")
                .woJobDTOs(Arrays.asList(woJobDTO1, woJobDTO2))
                .woCreationDate(LocalDateTime.now().minusDays(3))
                .woCompletionDate(LocalDateTime.now().minusHours(4))
                .address("address1")
                .city("city1")
                .state("state1")
                .clientId("client1")
                .build();
        String json = gson.toJson(woDTO);
        // Act and Assert
        mockMvc.perform(post("/workorders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict());
    }


    @Test
    @DisplayName("Test that a proper dto will be retrieved when searching by anexisting number")
    void getWorkOrderByNumber_success() throws Exception {
        mockMvc.perform(get("/workorders")
                        .param("woNumber","ABC123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.woNumber").value("ABC123"))
                .andExpect(jsonPath("$.clientId").value("client1"));
    }

    @Test
    @DisplayName("Test that null will be retrieved when searching by a non-existing number")
    void getWorkOrderByNumber_failure() throws Exception {
        mockMvc.perform(get("/workorders")
                        .param("woNumber","fakeWO"))
                .andExpect(status().isNotFound());
    }
}
