// src/main/java/org/example/tareas/controller/TaskController.java
package org.example.tareas.controller;

import jakarta.validation.Valid;
import org.example.tareas.dto.CreateTaskRequest;
import org.example.tareas.dto.TaskResponse;
import org.example.tareas.service.CreateTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final CreateTaskService createTaskService;

  public TaskController(CreateTaskService createTaskService) {
    this.createTaskService = createTaskService;
  }

  @PostMapping
  public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
    TaskResponse res = createTaskService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }
}