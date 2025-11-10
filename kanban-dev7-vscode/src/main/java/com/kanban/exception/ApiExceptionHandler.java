package com.kanban.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {

  record ErrorBody(Map<String,Object> error){}

  private ResponseEntity<ErrorBody> body(String code, String msg, HttpStatus status){
    Map<String,Object> map = new HashMap<>();
    map.put("code", code);
    map.put("message", msg);
    return ResponseEntity.status(status).body(new ErrorBody(map));
  }

  @ExceptionHandler(NoPermissionException.class)
  public ResponseEntity<ErrorBody> handle(NoPermissionException ex){
    return body(ex.getCode(), ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UserInactiveException.class)
  public ResponseEntity<ErrorBody> handle(UserInactiveException ex){
    return body(ex.getCode(), ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<ErrorBody> handle(TaskNotFoundException ex){
    return body(ex.getCode(), ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorBody> handle(BusinessException ex){
    return body(ex.getCode(), ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorBody> handle(Exception ex){
    return body("INTERNAL_ERROR", "Ocurrió un error inesperado.", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
