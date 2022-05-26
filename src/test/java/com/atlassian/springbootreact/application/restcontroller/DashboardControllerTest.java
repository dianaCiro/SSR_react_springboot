package com.atlassian.springbootreact.application.restcontroller;

import com.atlassian.springbootreact.SpringbootReactApplication;
import com.atlassian.springbootreact.application.exception.ResponseError;
import com.atlassian.springbootreact.domain.model.Dashboard;
import com.atlassian.springbootreact.persistence.entity.DashboardEntity;
import com.atlassian.springbootreact.persistence.jpa.JpaDashboardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringbootReactApplication.class)
@Transactional
public class DashboardControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaDashboardRepository jpaDashboardRepository;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void retrieveDashboardInformation() throws  Exception {
        ResultActions result = mockMvc.perform(get("/dashboard/1").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        Dashboard dashboardResponse = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                Dashboard.class);

        Optional<DashboardEntity> optionalDashboard = jpaDashboardRepository.findById(dashboardResponse.getId());

        assertTrue(optionalDashboard.isPresent());
    }

    @Test
    public void retrieveDashboardNotFound() throws  Exception {

        ResultActions result =   mockMvc.perform(get("/dashboard/4").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        ResponseError responseError = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ResponseError.class);

        assertEquals("dashboard not found", responseError.getMessage());

    }

    @Test
    public void createDashboard() throws  Exception {
        Dashboard dashboard = Dashboard.builder().name("Test").build();

        ResultActions result = mockMvc.perform(post("/dashboard").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(dashboard)));

        result.andExpect(status().isOk());

        Dashboard dashboardResponse = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                Dashboard.class);

        Optional<DashboardEntity> optionalDashboard = jpaDashboardRepository.findById(dashboardResponse.getId());

        assertTrue(optionalDashboard.isPresent());
    }
}
