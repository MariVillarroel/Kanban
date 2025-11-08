// src/main/java/org/example/tareas/exception/DuplicateTitleException.java
package org.example.tareas.exception;

public class DuplicateTitleException extends RuntimeException {
  public DuplicateTitleException(String message) { super(message); }
}