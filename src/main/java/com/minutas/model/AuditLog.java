package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuditLog {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_usuario")
    private int idUsuario;
    @JsonProperty("accion")
    private String accion;
    @JsonProperty("entidad")
    private String entidad;
    @JsonProperty("detalles")
    private String detalles;
    @JsonProperty("created_at")
    private String createdAt;

    public AuditLog() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }
    public String getDetalles() { return detalles; }
    public void setDetalles(String detalles) { this.detalles = detalles; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
