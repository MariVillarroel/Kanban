// src/main/java/org/example/tareas/controller/TaskController.java
package org.example.tareas.controller;

import jakarta.validation.Valid;
import org.example.tareas.dto.CreateTaskRequest;
import org.example.tareas.dto.TaskResponse;
import org.example.tareas.dto.AssignTaskRequest;
import org.example.tareas.dto.AssignTaskResponse;
import org.example.tareas.service.AssignTaskService;
import org.example.tareas.service.CreateTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final CreateTaskService createTaskService;
  private final AssignTaskService assignTaskService;

  public TaskController(CreateTaskService createTaskService, AssignTaskService assignTaskService) {
    this.createTaskService = createTaskService;
    this.assignTaskService = assignTaskService;
  }

  @PostMapping
  public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
    TaskResponse res = createTaskService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }

  @PostMapping("/{taskId}/assign")
  public ResponseEntity<?> assign(@PathVariable Long taskId,
                                  @Valid @RequestBody AssignTaskRequest request,
                                  @RequestHeader(value = "X-ADMIN", required = false) String adminHeader,
                                  @RequestHeader(value = "X-NOTIFY-FAIL", required = false) String notifyFailHeader) {
    boolean isAdmin = "true".equalsIgnoreCase(adminHeader);
    if (!isAdmin) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        java.util.Map.of(
          "message", "No tienes permisos para asignar tareas",
          "code", "PERMISSION_DENIED"
        )
      );
    }
    boolean forceFail = "true".equalsIgnoreCase(notifyFailHeader);
    AssignTaskResponse res = assignTaskService.assign(taskId, request.getUserId(), forceFail);
    return ResponseEntity.ok(res);
  }
}