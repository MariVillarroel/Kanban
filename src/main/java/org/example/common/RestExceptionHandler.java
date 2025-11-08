// src/main/java/org/example/common/RestExceptionHandler.java
package org.example.common;

import org.example.tareas.exception.DuplicateTitleException;
import org.example.usuarios.exception.DuplicateEmailException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

  private Map<String, Object> error(String code, String message, String reason, boolean draftSuggested) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", message);
    body.put("code", code);
    if (reason != null) body.put("reason", reason);
    body.put("draftSuggested", draftSuggested);
    return body;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Error de validación");
    body.put("code", "VALIDATION_ERROR");
    Map<String, String> errors = new HashMap<>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errors.put(fe.getField(), fe.getDefaultMessage());
    }
    body.put("errors", errors);
    body.put("draftSuggested", false);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(DuplicateEmailException.class)
  public ResponseEntity<Map<String, Object>> handleDuplicateEmail(DuplicateEmailException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(error("DUPLICATE_EMAIL", ex.getMessage(), "El correo ya está registrado", false));
  }

  @ExceptionHandler(DuplicateTitleException.class)
  public ResponseEntity<Map<String, Object>> handleDuplicateTitle(DuplicateTitleException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(error("DUPLICATE_TITLE", ex.getMessage(), "El título de la tarea ya existe", false));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleIntegrity(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
      .body(error("DATA_INTEGRITY", "Violación de integridad de datos", null, false));
  }

  // Timeout de consultas
  @ExceptionHandler(QueryTimeoutException.class)
  public ResponseEntity<Map<String, Object>> handleDbTimeout(QueryTimeoutException ex) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(error(
        "DB_TIMEOUT",
        "La base de datos tardó demasiado en responder",
        "Reintente más tarde",
        true // sugerir guardar borrador local
      ));
  }

  // BD caída / no disponible / no se puede abrir transacción
  @ExceptionHandler({ DataAccessResourceFailureException.class, CannotCreateTransactionException.class })
  public ResponseEntity<Map<String, Object>> handleDbUnavailable(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
      .body(error(
        "DB_UNAVAILABLE",
        "La base de datos no está disponible",
        "Servicio temporalmente no disponible",
        true // sugerir guardar borrador local
      ));
  }

  // Fallback
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleUnknown(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(error("INTERNAL_ERROR", "Error interno", null, false));
  }
}