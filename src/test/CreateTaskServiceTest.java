// src/test/java/org/example/tareas/CreateTaskServiceTest.java
package org.example.test;

import org.example.tareas.dto.CreateTaskRequest;
import org.example.tareas.dto.TaskResponse;
import org.example.tareas.exception.DuplicateTitleException;
import org.example.tareas.model.TaskStatus;
import org.example.tareas.repository.TaskRepository;
import org.example.tareas.service.CreateTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CreateTaskServiceTest {

  @Autowired CreateTaskService service;
  @Autowired TaskRepository taskRepository;

  @BeforeEach
  void clean() {
    taskRepository.deleteAll();
  }

  @Test
  void creaTarea_ok_persistida() {
    CreateTaskRequest req = new CreateTaskRequest();
    req.setTitulo("Configurar CI");
    req.setDescripcion("Agregar workflow Github Actions");
    req.setEstado(TaskStatus.PENDIENTE);

    TaskResponse res = service.create(req);

    assertThat(res.getId()).isNotNull();
    assertThat(res.getTitulo()).isEqualTo("Configurar CI");
    assertThat(res.isPersisted()).isTrue();
    assertThat(res.isLocalDraftSuggested()).isFalse();
    assertThat(taskRepository.existsByTituloIgnoreCase("Configurar CI")).isTrue();
  }

  @Test
  void tituloDuplicado_lanzaDuplicateTitleException() {
    CreateTaskRequest req = new CreateTaskRequest();
    req.setTitulo("Configurar CI");

    service.create(req);

    assertThrows(DuplicateTitleException.class, () -> service.create(req));
  }
}