// src/main/java/org/example/tareas/service/CreateTaskService.java
package org.example.tareas.service;

import org.example.tareas.dto.CreateTaskRequest;
import org.example.tareas.dto.TaskResponse;
import org.example.tareas.exception.DuplicateTitleException;
import org.example.tareas.model.Task;
import org.example.tareas.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTaskService {

  private final TaskRepository taskRepository;

  public CreateTaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  // Transaccional: si algo falla, se hace rollback
  @Transactional
  public TaskResponse create(CreateTaskRequest req) {
    // Regla de negocio: título único (case-insensitive)
    if (taskRepository.existsByTituloIgnoreCase(req.getTitulo())) {
      throw new DuplicateTitleException("El título de la tarea ya existe");
    }

    Task toSave = new Task(req.getTitulo(), req.getDescripcion(), req.getEstado());
    Task saved = taskRepository.save(toSave);

    return new TaskResponse(
      saved.getId(),
      saved.getTitulo(),
      saved.getDescripcion(),
      saved.getEstado(),
      true,   // persisted
      false   // localDraftSuggested
    );
  }
}