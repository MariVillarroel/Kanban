// src/main/java/org/example/tareas/dto/CreateTaskRequest.java
package org.example.tareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.tareas.model.TaskStatus;

public class CreateTaskRequest {

  @NotBlank(message = "El título es obligatorio")
  @Size(max = 200, message = "El título no debe superar 200 caracteres")
  private String titulo;

  @Size(max = 2000, message = "La descripción no debe superar 2000 caracteres")
  private String descripcion;

  // Opcional: permitir setear estado inicial
  private TaskStatus estado;

  public CreateTaskRequest() {}

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }
  public String getDescripcion() { return descripcion; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  public TaskStatus getEstado() { return estado; }
  public void setEstado(TaskStatus estado) { this.estado = estado; }
}