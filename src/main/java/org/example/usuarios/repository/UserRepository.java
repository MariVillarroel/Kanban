package org.example.usuarios.repository;

import org.example.usuarios.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByCorreoIgnoreCase(String correo);
  Optional<User> findByCorreoIgnoreCase(String correo);
}