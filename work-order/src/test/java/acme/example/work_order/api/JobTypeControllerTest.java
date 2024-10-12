package acme.example.work_order.api;

import acme.example.work_order.jobtype.internal.JobType;
import acme.example.work_order.jobtype.internal.JobTypeDAO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = "/sql/jobTypeTestData.sql")
public class JobTypeControllerTest extends BaseApiTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobTypeDAO typeDAO;

    @Test
    @DisplayName("Test getJobType() endpoint when the jobType exists")
    void getJobTypeTest_exist() throws Exception {
        mockMvc.perform(get("/jobtypes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value("type1"))
                .andExpect(jsonPath("$.name").value("job type name 1"))
                .andExpect(jsonPath("$.activeStatus").value("Y"))
                .andExpect(jsonPath("$.clientType").value("consumer"));
    }

    @Test
    @DisplayName("Test getJobType() endpoint when the jobType doesn't exists")
    void getJobTypeTest_notExist() throws Exception {
        mockMvc.perform(get("/jobtypes/{id}", 100L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test succesfull save of a jobType")
    void createJobTest_success() throws Exception {
        mockMvc.perform(post("/jobtypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"code\":\"newType\", \"name\":\"test saving jobTypes\", \"activeStatus\":\"Y\" , \"clientType\":\"consumer\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        JobType type = typeDAO.findByCode("newType");
        Assertions.assertNotNull(type, "JobType should not be null");
        Assertions.assertNotNull(type.getId(), "JobType id should not be null");
        Assertions.assertNotNull(type.getCreationDate(), "JobType creation Date should not be null");
        Assertions.assertEquals("test saving jobTypes", type.getName(), "JobType name should match");
    }

    @Test
    @DisplayName("Test that a jobtype cannot be saved without a code")
    void createJobTest_failure() throws Exception {
        mockMvc.perform(post("/jobtypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"code\":null, \"name\":\"test saving jobs\", \"activeStatus\":\"Y\", \"clientType\":\"consumer\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Test that a status is returned when search by an existing id")
    void getActiveStatusTest_success() throws Exception {
        MvcResult result = mockMvc.perform(get("/jobtypes/{id}/status", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertEquals("Y", content, "JobType status should match");
    }

    @Test
    @DisplayName("Test not found is returned when search by a non-existing id")
    void getActiveStatusTest_failure() throws Exception {
        mockMvc.perform(get("/jobtypes/{id}/status", 100L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test findByCode() endpoint when the job type exists")
    void findByCode_exist() throws Exception {
        mockMvc.perform(get("/jobtypes/codes/{code}", "type1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value("type1"))
                .andExpect(jsonPath("$.name").value("job type name 1"))
                .andExpect(jsonPath("$.activeStatus").value("Y"))
                .andExpect(jsonPath("$.clientType").value("consumer"));
    }

    @Test
    @DisplayName("Test findByCode() endpoint when the job type doesn't exists")
    void findByCode_notExist() throws Exception {
        mockMvc.perform(get("/jobtypes/codes/{code}", "non-existent"))
                .andExpect(status().isNotFound());
    }

}
