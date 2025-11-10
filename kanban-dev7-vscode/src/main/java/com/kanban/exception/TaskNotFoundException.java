package com.kanban.exception;
public class TaskNotFoundException extends BusinessException{
  public TaskNotFoundException(){ super("TASK_NOT_FOUND", "La tarea no existe."); }
}
