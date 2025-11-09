package org.example.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tareas.dto.CreateTaskRequest;
import org.example.tareas.model.TaskStatus;
import org.example.usuarios.dto.RegistroUsuarioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AssignmentE2ETest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;

  private Long createUser(String nombre, String correo) throws Exception {
    RegistroUsuarioRequest req = new RegistroUsuarioRequest();
    req.setNombre(nombre);
    req.setCorreo(correo);
    req.setContrasena("Secreta123");
  MvcResult res = mockMvc.perform(post("/api/usuarios/registro")
    .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
    .content(Objects.requireNonNull(objectMapper.writeValueAsString(req))))
      .andExpect(status().isCreated())
      .andReturn();
  JsonNode json = objectMapper.readTree(res.getResponse().getContentAsString());
  return json.get("id").asLong();
  }

  private Long createTask(String titulo) throws Exception {
    CreateTaskRequest req = new CreateTaskRequest();
    req.setTitulo(titulo);
    req.setEstado(TaskStatus.PENDIENTE);
  MvcResult res = mockMvc.perform(post("/api/tasks")
    .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
    .content(Objects.requireNonNull(objectMapper.writeValueAsString(req))))
      .andExpect(status().isCreated())
      .andReturn();
  JsonNode json = objectMapper.readTree(res.getResponse().getContentAsString());
  return json.get("id").asLong();
  }

  @BeforeEach
  void warmup() { }

  @Test
  void e2e_asignacion_ok_y_metricas() throws Exception {
    Long userId = createUser("Ana", "ana@ex.com");
    Long taskId = createTask("Asignar reviewer");

    String body = "{\"userId\":" + userId + "}";
    mockMvc.perform(post("/api/tasks/"+taskId+"/assign")
        .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
        .header("X-ADMIN", "true")
        .content(Objects.requireNonNull(body)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.taskId").value(taskId))
      .andExpect(jsonPath("$.assignedUserId").value(userId))
      .andExpect(jsonPath("$.notificationError").value(false))
      .andReturn();

    // Dashboard
    MvcResult dash = mockMvc.perform(get("/api/metrics/dashboard"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.assignment.count").value(1))
      .andReturn();
  JsonNode json = objectMapper.readTree(dash.getResponse().getContentAsString());
  assertThat(json.get("notification.total").asDouble()).isGreaterThanOrEqualTo(1.0);
  }

  @Test
  void e2e_permiso_denegado_403() throws Exception {
    Long userId = createUser("Bob", "bob@ex.com");
    Long taskId = createTask("Asignar QA");
    String body = "{\"userId\":" + userId + "}";
  mockMvc.perform(post("/api/tasks/"+taskId+"/assign")
    .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
    .content(Objects.requireNonNull(body)))
      .andExpect(status().isForbidden())
      .andExpect(jsonPath("$.code").value("PERMISSION_DENIED"));
  }

  @Test
  void e2e_fallo_notificacion_ok200_con_flag_y_metricas() throws Exception {
    Long userId = createUser("Carla", "carla@ex.com");
    Long taskId = createTask("Asignar diseño");
    String body = "{\"userId\":" + userId + "}";

  mockMvc.perform(post("/api/tasks/"+taskId+"/assign")
    .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
        .header("X-ADMIN", "true")
        .header("X-NOTIFY-FAIL", "true")
    .content(Objects.requireNonNull(body)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.notificationError").value(true));

    // Dashboard debe reflejar errorRate > 0
    MvcResult dash = mockMvc.perform(get("/api/metrics/dashboard"))
      .andExpect(status().isOk())
      .andReturn();
  JsonNode json = objectMapper.readTree(dash.getResponse().getContentAsString());
    double total = json.get("notification.total").asDouble();
    double errors = json.get("notification.errors").asDouble();
    assertThat(total).isGreaterThanOrEqualTo(1.0);
    assertThat(errors).isGreaterThanOrEqualTo(1.0);
  }
}
