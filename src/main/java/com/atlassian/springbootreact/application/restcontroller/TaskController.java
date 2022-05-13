package com.atlassian.springbootreact.application.restcontroller;

import com.atlassian.springbootreact.domain.enums.StatusEnum;
import com.atlassian.springbootreact.domain.filterandpagination.ElementPage;
import com.atlassian.springbootreact.domain.filterandpagination.TaskFilter;
import com.atlassian.springbootreact.domain.model.Task;
import com.atlassian.springbootreact.domain.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> retrieveTask(@PathVariable("id") Long taskId){
        return ResponseEntity.ok(taskService.retrieveTask(taskId));
    }

    @GetMapping
    public ResponseEntity<ElementPage<Task>> retrieveTasks(TaskFilter taskFilter){
        return ResponseEntity.ok(taskService.retrieveTasks(taskFilter));
    }

    @PostMapping
    public ResponseEntity<Task> save(@RequestBody @Valid Task task){
        return ResponseEntity.ok(taskService.save(task));
    }
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Task> updateStatus(@PathVariable("id") Long taskId, @PathVariable("status") StatusEnum status){
        return ResponseEntity.ok(
                taskService.updateStatus(taskId, status));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long taskId){
        taskService.deleteTask(taskId);
    }
}
