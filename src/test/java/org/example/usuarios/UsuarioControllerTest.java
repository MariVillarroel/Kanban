package org.example.usuarios;

import org.example.usuarios.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UsuarioControllerTest {

  @Autowired MockMvc mockMvc;
  @Autowired UserRepository userRepository;
  @Autowired PasswordEncoder passwordEncoder;

  @BeforeEach
  void setup() {
    userRepository.deleteAll();
  }

  @Test
  void creaUsuario_201() throws Exception {
    String body = "{\"nombre\":\"Ana\",\"correo\":\"ana@example.com\",\"contrasena\":\"Secreta123\"}";

    mockMvc.perform(post("/api/usuarios/registro")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id").exists())
      .andExpect(jsonPath("$.correo").value("ana@example.com"));

    var saved = userRepository.findByCorreoIgnoreCase("ana@example.com").orElseThrow();
    assertThat(saved.getPasswordHash()).isNotBlank();
    assertThat(passwordEncoder.matches("Secreta123", saved.getPasswordHash())).isTrue();
  }

  @Test
  void correoDuplicado_409() throws Exception {
    String body = "{\"nombre\":\"Ana\",\"correo\":\"ana@example.com\",\"contrasena\":\"Secreta123\"}";

    mockMvc.perform(post("/api/usuarios/registro")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isCreated());

    mockMvc.perform(post("/api/usuarios/registro")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isConflict())
      .andExpect(jsonPath("$.message").value("El correo ya está registrado"));
  }

  @Test
  void validaciones_400() throws Exception {
    String body = "{\"nombre\":\"\",\"correo\":\"mal\",\"contrasena\":\"123\"}";

    mockMvc.perform(post("/api/usuarios/registro")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors.nombre").value("El nombre es obligatorio"))
      .andExpect(jsonPath("$.errors.correo").value("El correo no tiene formato válido"))
      .andExpect(jsonPath("$.errors.contrasena").value("La contraseña debe tener al menos 8 caracteres"));
  }
}