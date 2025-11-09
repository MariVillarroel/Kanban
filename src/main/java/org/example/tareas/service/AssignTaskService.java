package org.example.tareas.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.example.tareas.dto.AssignTaskResponse;
import org.example.tareas.model.Task;
import org.example.tareas.repository.TaskRepository;
import org.example.usuarios.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignTaskService {

  private final TaskRepository taskRepository;
  private final UserRepository userRepository;
  private final NotificationService notificationService;
  private final Timer assignTimer;

  public AssignTaskService(TaskRepository taskRepository,
                           UserRepository userRepository,
                           NotificationService notificationService,
                           MeterRegistry registry) {
    this.taskRepository = taskRepository;
    this.userRepository = userRepository;
    this.notificationService = notificationService;
    this.assignTimer = Timer.builder("tasks.assignment.duration")
      .description("Tiempo de asignación de tareas en ms")
      .publishPercentiles(0.5, 0.95, 0.99)
      .register(registry);
  }

  @Transactional
  public AssignTaskResponse assign(Long taskId, Long userId, boolean forceNotifyFail) {
    return assignTimer.record(() -> {
      if (taskId == null || userId == null) {
        throw new IllegalArgumentException("Ids no pueden ser nulos");
      }
      Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
      userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

      task.setAssignedUserId(userId);
      taskRepository.save(task);

      boolean notificationError = notificationService.notifyAssignment(task.getId(), userId, forceNotifyFail);
      return new AssignTaskResponse(task.getId(), userId, notificationError);
    });
  }
}
