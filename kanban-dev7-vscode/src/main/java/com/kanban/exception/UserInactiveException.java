package com.kanban.exception;
public class UserInactiveException extends BusinessException{
  public UserInactiveException(){ super("USER_INACTIVE", "La cuenta del usuario está inactiva."); }
}
