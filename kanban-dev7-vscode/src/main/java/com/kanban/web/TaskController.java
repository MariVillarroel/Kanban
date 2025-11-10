package com.kanban.web;

import com.kanban.domain.Task;
import com.kanban.notifications.*;
import com.kanban.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/tasks")
public class TaskController {
  private final TaskRepository repo;
  private final NotificationService notifier;

  public TaskController(TaskRepository r, NotificationService n){
    this.repo=r; this.notifier=n;
  }

  @PostMapping
  public Task create(@RequestBody Task t){
    Task saved = repo.save(t);
    notifier.onTaskCreated(new TaskCreatedEvent(saved.getId(), 0L, saved.getProjectId(), saved.getTitle()),
        List.of(1L)); // demo: notifica al userId 1
    return saved;
  }
}
