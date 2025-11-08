// src/main/java/org/example/tareas/repository/TaskRepository.java
package org.example.tareas.repository;

import org.example.tareas.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
  boolean existsByTituloIgnoreCase(String titulo);
  Optional<Task> findByTituloIgnoreCase(String titulo);
}