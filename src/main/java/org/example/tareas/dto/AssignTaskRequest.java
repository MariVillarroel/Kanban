package org.example.tareas.dto;

import jakarta.validation.constraints.NotNull;

public class AssignTaskRequest {
  @NotNull(message = "El usuario asignado es obligatorio")
  private Long userId;

  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
}
