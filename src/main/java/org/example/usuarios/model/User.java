package org.example.usuarios.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios", indexes = {
    @Index(name = "idx_usuarios_correo", columnList = "correo", unique = true)
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false, unique = true)
  private String correo;

  @Column(nullable = false, name = "password_hash")
  private String passwordHash;

  public User() {}

  public User(String nombre, String correo, String passwordHash) {
    this.nombre = nombre;
    this.correo = correo;
    this.passwordHash = passwordHash;
  }

  public Long getId() { return id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getCorreo() { return correo; }
  public void setCorreo(String correo) { this.correo = correo; }
  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}