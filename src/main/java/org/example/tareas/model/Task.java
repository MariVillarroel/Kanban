// src/main/java/org/example/tareas/model/Task.java
package org.example.tareas.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(
  name = "tasks",
  indexes = {
    @Index(name = "idx_tasks_titulo", columnList = "titulo", unique = true)
  }
)
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String titulo;

  @Column(length = 2000)
  private String descripcion;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TaskStatus estado = TaskStatus.PENDIENTE;

  // Usuario asignado (simple: almacenamos solo el ID para evitar dependencia circular)
  @Column(name = "assigned_user_id")
  private Long assignedUserId;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private OffsetDateTime creadoEn;

  @UpdateTimestamp
  @Column(nullable = false)
  private OffsetDateTime actualizadoEn;

  public Task() {}

  public Task(String titulo, String descripcion, TaskStatus estado) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.estado = (estado != null) ? estado : TaskStatus.PENDIENTE;
  }

  public Long getId() { return id; }
  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }
  public String getDescripcion() { return descripcion; }
  public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
  public TaskStatus getEstado() { return estado; }
  public void setEstado(TaskStatus estado) { this.estado = estado; }
  public OffsetDateTime getCreadoEn() { return creadoEn; }
  public OffsetDateTime getActualizadoEn() { return actualizadoEn; }

  public Long getAssignedUserId() { return assignedUserId; }
  public void setAssignedUserId(Long assignedUserId) { this.assignedUserId = assignedUserId; }
}