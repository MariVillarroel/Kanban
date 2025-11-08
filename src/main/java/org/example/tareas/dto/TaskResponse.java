// src/main/java/org/example/tareas/dto/TaskResponse.java
package org.example.tareas.dto;

import org.example.tareas.model.TaskStatus;

public class TaskResponse {
  private Long id;
  private String titulo;
  private String descripcion;
  private TaskStatus estado;

  // Flags de “hook borrador local”
  private boolean persisted;           // true si se guardó en BD
  private boolean localDraftSuggested; // true si conviene guardar local por error infra

  public TaskResponse() {}

  public TaskResponse(Long id, String titulo, String descripcion, TaskStatus estado,
                      boolean persisted, boolean localDraftSuggested) {
    this.id = id;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.estado = estado;
    this.persisted = persisted;
    this.localDraftSuggested = localDraftSuggested;
  }

  public Long getId() { return id; }
  public String getTitulo() { return titulo; }
  public String getDescripcion() { return descripcion; }
  public TaskStatus getEstado() { return estado; }
  public boolean isPersisted() { return persisted; }
  public boolean isLocalDraftSuggested() { return localDraftSuggested; }

  public void setId(Long id) { this.id = id; }
  public void setTitulo(String titulo) { this.titulo = titulo; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  public void setEstado(TaskStatus estado) { this.estado = estado; }
  public void setPersisted(boolean persisted) { this.persisted = persisted; }
  public void setLocalDraftSuggested(boolean localDraftSuggested) { this.localDraftSuggested = localDraftSuggested; }
}