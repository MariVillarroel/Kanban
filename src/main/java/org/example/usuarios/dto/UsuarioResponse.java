package org.example.usuarios.dto;

public class UsuarioResponse {
  private Long id;
  private String nombre;
  private String correo;

  public UsuarioResponse(Long id, String nombre, String correo) {
    this.id = id;
    this.nombre = nombre;
    this.correo = correo;
  }

  public Long getId() { return id; }
  public String getNombre() { return nombre; }
  public String getCorreo() { return correo; }
}