package acme.example.work_order.api;

import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorderjob.internal.WorkOrderJob;
import acme.example.work_order.workorderjob.internal.WorkOrderJobDAO;
import acme.example.work_order.workorderjob.internal.WorkOrderJobMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/sql/woTestData.sql")
public class WorkOrderJobControllerTest extends BaseApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkOrderJobDAO woJobDAO;

    @Autowired
    private WorkOrderJobMapper woJobMapper;

    Gson gson = new Gson();

    @Test
    @DisplayName("Test that an entity will be saved when the WO exists")
    void createWorkOrderJobTest_success() throws Exception {
        // Arrange
        WorkOrderJobDTO woJobDTO = WorkOrderJobDTO.builder()
                .woNumber("ZZZ999")
                .jobCode("jobCode1")
                .quantity(4)
                .activeStatus('Y')
                .appliedRule("A1")
                .build();
        String json = gson.toJson(woJobDTO);
        // Act
        MvcResult result = mockMvc.perform(post("/workorderjobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();
        boolean saved = Boolean.parseBoolean(result.getResponse().getContentAsString());
        List<WorkOrderJob> woJobs = woJobDAO.findByWorkOrderNumber("ZZZ999");
        List<WorkOrderJob> target = woJobs.stream().filter(w -> w.getWorkOrder().getWoNumber().equals("ZZZ999")).toList();
        // Assert
        Assertions.assertTrue(saved, "The entity should have been saved successfully.");
        Assertions.assertFalse(woJobs.isEmpty(), "There should be at least one object in the list");
        Assertions.assertFalse(target.isEmpty(), "There should be at least one object in the list with the required WO number");
        Assertions.assertEquals(1,target.size(), "There should be exactly one object in the list");
        Assertions.assertNotNull(target.getFirst().getCreationDate(), "The object should have a creation date");
        Assertions.assertNotNull(target.getFirst().getId(), "The object should have an id");
        Assertions.assertEquals(10,target.getFirst().getId(), "The object's id should match");
        Assertions.assertEquals(4,target.getFirst().getQuantity(), "The object's quantity should match");
    }

    @Test
    @DisplayName("Test that an entity will not be saved when the WO does not exists")
    void createWorkOrderJobTest_failure() throws Exception {
        // Arrange
        WorkOrderJobDTO woJobDTO = WorkOrderJobDTO.builder()
                .woNumber("non-existent")
                .jobCode("jobCode1")
                .quantity(4)
                .activeStatus('Y')
                .appliedRule("A1")
                .build();
        String json = gson.toJson(woJobDTO);
        // Act
        MvcResult result = mockMvc.perform(post("/workorderjobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andReturn();
        boolean saved = Boolean.parseBoolean(result.getResponse().getContentAsString());
        // Assert
        Assertions.assertFalse(saved, "The entity should not have been saved because it`s parent doesn't exist.");
    }

    @Test
    @DisplayName("Test that a proper dto will be retrieved when searching by an existing id")
    void getWorkOrderJobByIdTest_success() throws Exception {
        mockMvc.perform(get("/workorderjobs/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.woNumber").value("ABC123"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    @DisplayName("Test that null will be retrieved when searching by a non-existing id")
    void getWorkOrderJobByIdTest_failure() throws Exception {
        mockMvc.perform(get("/workorderjobs/{id}", 100L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test a list of dtos is returned when searching for at least one existing code")
    void findByCodesTest_success() throws Exception {
        // Arrange
        List<WorkOrderJob> woJobs = woJobDAO.findByCodes(List.of("JobCode1","JobCode2"));
        List<WorkOrderJobDTO> dtoList = woJobs.stream().map(woJobMapper::convertToDTO).toList();
        // Act
        MvcResult result = mockMvc.perform(get("/workorderjobs/codes")
                        .param("codeList",new String[]{"JobCode1", "JobCode2","sarasa"}))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        TypeToken<List<WorkOrderJobDTO>> typeToken = new TypeToken<>(){};
        List<WorkOrderJobDTO> dtos = new Gson().fromJson(json, typeToken.getType());
        // Assert
        Assertions.assertNotNull(dtos,"There should be at least one dto");
        Assertions.assertEquals(3, dtos.size(),"There should be two dtos");
        Assertions.assertEquals(dtoList, dtos, "The list of dtos should match");
    }

    @Test
    @DisplayName("An empty list should be returned when searching by a list of non-existing codes")
    void findByCodesTest_failure() throws Exception {
        MvcResult result = mockMvc.perform(get("/workorderjobs/codes")
                        .param("codeList",new String[]{"non", "existing"}))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        TypeToken<List<WorkOrderJobDTO>> typeToken = new TypeToken<>(){};
        List<WorkOrderJobDTO> dtos = new Gson().fromJson(json, typeToken.getType());
        Assertions.assertTrue(dtos.isEmpty(),"There should be no dtos");
    }

}
