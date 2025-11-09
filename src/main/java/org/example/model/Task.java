package org.example.model;

public class Task {
    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaLimite; // yyyy-MM-dd
    private String estado; // Por defecto "Por hacer"
    private String creador; // Identificador del usuario
    private String createdAt; // yyyy-MM-ddTHH:mm
    private String updatedAt; // yyyy-MM-ddTHH:mm

    public Task() {}

    public Task(Long id, String titulo, String descripcion, String fechaLimite, String estado, String creador, String createdAt, String updatedAt) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        this.creador = creador;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(String fechaLimite) { this.fechaLimite = fechaLimite; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCreador() { return creador; }
    public void setCreador(String creador) { this.creador = creador; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    // Serialización para CSV
    public String toCsvString() {
        return id + "," + escape(titulo) + "," + escape(descripcion) + "," + fechaLimite + "," + estado + "," + creador + "," + createdAt + "," + updatedAt;
    }
    private String escape(String s) {
        return s == null ? "" : '"' + s.replace("\"", "'") + '"';
    }
}
