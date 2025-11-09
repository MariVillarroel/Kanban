package org.example.tareas.dto;

public class AssignTaskResponse {
  private Long taskId;
  private Long assignedUserId;
  private boolean notificationError;

  public AssignTaskResponse(Long taskId, Long assignedUserId, boolean notificationError) {
    this.taskId = taskId;
    this.assignedUserId = assignedUserId;
    this.notificationError = notificationError;
  }

  public Long getTaskId() { return taskId; }
  public Long getAssignedUserId() { return assignedUserId; }
  public boolean isNotificationError() { return notificationError; }
}
