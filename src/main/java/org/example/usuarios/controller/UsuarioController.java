package org.example.usuarios.controller;

import jakarta.validation.Valid;
import org.example.usuarios.dto.RegistroUsuarioRequest;
import org.example.usuarios.dto.UsuarioResponse;
import org.example.usuarios.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping("/registro")
  public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
    UsuarioResponse res = usuarioService.registrar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
  }
}