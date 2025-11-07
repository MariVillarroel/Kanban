package org.example.usuarios.service;

import org.example.usuarios.dto.RegistroUsuarioRequest;
import org.example.usuarios.dto.UsuarioResponse;
import org.example.usuarios.exception.DuplicateEmailException;
import org.example.usuarios.model.User;
import org.example.usuarios.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UsuarioService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public UsuarioResponse registrar(RegistroUsuarioRequest req) {
    if (userRepository.existsByCorreoIgnoreCase(req.getCorreo())) {
      throw new DuplicateEmailException("El correo ya está registrado");
    }
    String hash = passwordEncoder.encode(req.getContrasena());
    User user = new User(req.getNombre(), req.getCorreo(), hash);
    User saved = userRepository.save(user);
    return new UsuarioResponse(saved.getId(), saved.getNombre(), saved.getCorreo());
  }
}