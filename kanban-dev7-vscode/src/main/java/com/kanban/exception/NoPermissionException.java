package com.kanban.exception;
public class NoPermissionException extends BusinessException{
  public NoPermissionException(){ super("NO_PERMISSION", "No tienes permisos para esta operación."); }
}
