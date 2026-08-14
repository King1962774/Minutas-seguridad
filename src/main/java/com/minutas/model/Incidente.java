package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Incidente {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_turno")
    private int idTurno;
    @JsonProperty("id_usuario")
    private int idUsuario;
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("atendido")
    private int atendido;
    @JsonProperty("created_at")
    private String createdAt;

    public Incidente() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getAtendido() { return atendido; }
    public void setAtendido(int atendido) { this.atendido = atendido; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
