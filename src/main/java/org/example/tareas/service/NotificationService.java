package org.example.tareas.service;

public interface NotificationService {
  /**
   * Envía una notificación de asignación. Retorna true si tuvo error.
   */
  boolean notifyAssignment(Long taskId, Long userId, boolean forceFail);
}
