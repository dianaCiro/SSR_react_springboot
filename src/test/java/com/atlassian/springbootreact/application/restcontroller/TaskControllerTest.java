package com.atlassian.springbootreact.application.restcontroller;


import com.atlassian.springbootreact.SpringbootReactApplication;
import com.atlassian.springbootreact.application.exception.ResponseError;
import com.atlassian.springbootreact.domain.enums.StatusEnum;
import com.atlassian.springbootreact.domain.filterandpagination.ElementPage;
import com.atlassian.springbootreact.domain.model.Task;
import com.atlassian.springbootreact.domain.repository.PublisherRepository;
import com.atlassian.springbootreact.domain.service.TaskService;
import com.atlassian.springbootreact.persistence.entity.TaskEntity;
import com.atlassian.springbootreact.persistence.jpa.JpaTaskRepository;
import com.atlassian.springbootreact.testdatabuilder.TaskTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(classes = {SpringbootReactApplication.class})
@Transactional
public class TaskControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaTaskRepository jpaTaskRepository;

    @MockBean
    private PublisherRepository publisherRepository;

    private TaskTestDataBuilder taskBuilder;

    @BeforeEach
    public void setUp() {
        taskBuilder = new TaskTestDataBuilder();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void retrieveTask() throws  Exception {
        ResultActions result = mockMvc.perform(get("/task/1").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        Task taskResponse = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                Task.class);

        Optional<TaskEntity> optionalTask = jpaTaskRepository.findById(taskResponse.getId());

        assertTrue(optionalTask.isPresent());
    }

    @Test
    public void retrieveTaskNotFound() throws  Exception {

        ResultActions result =   mockMvc.perform(get("/task/9").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        ResponseError responseError = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ResponseError.class);

        assertEquals(TaskService.TASK_NOT_FOUND, responseError.getMessage());

    }

    @Test
    public void retrieveTaskWithFilters() throws  Exception {

        String url = "/task?limit=2&dashboardId=1&page=0&sortColumn=description&sortDirection=ASC";
        ResultActions result = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        ElementPage<Task> TaskPage = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ElementPage.class);

        assertEquals(2, TaskPage.getElements().size());
        assertEquals(3, TaskPage.getTotalElements());
        assertEquals(2, TaskPage.getTotalPages());
    }

    @Test
    public void retrieveTaskWithoutFilters() throws  Exception {

        String url = "/task?limit=2&page=0&sortColumn=description&sortDirection=ASC";
        ResultActions result = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        ElementPage<Task> TaskPage = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ElementPage.class);

        assertEquals(2, TaskPage.getElements().size());
        assertEquals(5, TaskPage.getTotalElements());
        assertEquals(3, TaskPage.getTotalPages());
    }

    @Test
    public void retrieveTaskWithoutFiltersAndWithoutPagination() throws  Exception {

        String url = "/task";
        ResultActions result = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        ElementPage<Task> TaskPage = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ElementPage.class);

        assertEquals(5, TaskPage.getElements().size());
        assertEquals(5, TaskPage.getTotalElements());
        assertEquals(1, TaskPage.getTotalPages());
    }


    @Test
    public void save() throws  Exception {
        Task task = taskBuilder.build();

        ResultActions result = mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(task)));

        result.andExpect(status().isOk());

        Task taskResponse = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                Task.class);

        Optional<TaskEntity> optionalTask = jpaTaskRepository.findById(taskResponse.getId());

        assertTrue(optionalTask.isPresent());
    }

    @Test
    public void saveDashboardNotFound() throws  Exception {

        Task task = taskBuilder.setDashboardId(10l).build();

        ResultActions result = mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(task)));

        result.andExpect(status().isNotFound());
        ResponseError responseError = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ResponseError.class);

        assertEquals("The dashboard must exist in database", responseError.getMessage());

    }

    @Test
    public void updateStatus() throws  Exception {

        Optional<TaskEntity> optionalTask = jpaTaskRepository.findById(1l);
        assertEquals(StatusEnum.TODO, optionalTask.get().getStatus());
        ResultActions result = mockMvc.perform(put("/task/1/status/PROGRESS").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        Task taskResponse = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                Task.class);

        optionalTask = jpaTaskRepository.findById(taskResponse.getId());

        assertEquals(StatusEnum.PROGRESS, optionalTask.get().getStatus());
    }

    @Test
    public void updateStatusToDone() throws  Exception {

        Optional<TaskEntity> optionalTask = jpaTaskRepository.findById(1l);
        assertEquals(StatusEnum.TODO, optionalTask.get().getStatus());
        ResultActions result = mockMvc.perform(put("/task/1/status/DONE").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        Task taskResponse = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                Task.class);

        optionalTask = jpaTaskRepository.findById(taskResponse.getId());

        assertEquals(StatusEnum.DONE, optionalTask.get().getStatus());
        Mockito.verify(publisherRepository, Mockito.times(1)).sendMessage("Status changed to DONE");
    }

    @Test
    public void updateStatusTaskNotFound() throws  Exception {

        ResultActions result = mockMvc.perform(put("/task/9/status/PROGRESS").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        ResponseError responseError = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ResponseError.class);

        assertEquals(TaskService.TASK_NOT_FOUND, responseError.getMessage());

    }

    @Test
    public void deleteTask() throws  Exception {

        Optional<TaskEntity> optionalTask = jpaTaskRepository.findById(1l);
        assertTrue(optionalTask.isPresent());

        ResultActions result = mockMvc.perform(delete("/task/1").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        optionalTask = jpaTaskRepository.findById(1l);

        assertFalse(optionalTask.isPresent());
    }

    @Test
    public void deleteTaskNotFound() throws  Exception {

        ResultActions result =   mockMvc.perform(delete("/task/9").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        ResponseError responseError = this.objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ResponseError.class);

        assertEquals(TaskService.TASK_NOT_FOUND, responseError.getMessage());

    }
}
