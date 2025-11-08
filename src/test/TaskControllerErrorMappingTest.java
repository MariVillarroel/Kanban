// src/test/java/org/example/tareas/TaskControllerErrorMappingTest.java
package org.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tareas.dto.CreateTaskRequest;
import org.example.tareas.model.Task;
import org.example.tareas.model.TaskStatus;
import org.example.tareas.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerErrorMappingTest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  // Simulamos errores de infraestructura en la capa de repositorio
  @MockBean TaskRepository taskRepository;

  @Test
  void timeoutDB_devuelve503_y_draftSuggestedTrue() throws Exception {
    when(taskRepository.existsByTituloIgnoreCase(any())).thenReturn(false);
    when(taskRepository.save(any(Task.class))).thenThrow(new QueryTimeoutException("timeout"));

    CreateTaskRequest req = new CreateTaskRequest();
    req.setTitulo("Task A");
    req.setEstado(TaskStatus.PENDIENTE);

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isServiceUnavailable())
      .andExpect(jsonPath("$.code").value("DB_TIMEOUT"))
      .andExpect(jsonPath("$.draftSuggested").value(true));
  }

  @Test
  void bdNoDisponible_devuelve503_y_draftSuggestedTrue() throws Exception {
    when(taskRepository.existsByTituloIgnoreCase(any())).thenReturn(false);
    when(taskRepository.save(any(Task.class))).thenThrow(new DataAccessResourceFailureException("down"));

    CreateTaskRequest req = new CreateTaskRequest();
    req.setTitulo("Task B");

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(req)))
      .andExpect(status().isServiceUnavailable())
      .andExpect(jsonPath("$.code").value("DB_UNAVAILABLE"))
      .andExpect(jsonPath("$.draftSuggested").value(true));
  }
}