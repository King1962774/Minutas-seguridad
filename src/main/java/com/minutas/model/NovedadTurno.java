package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NovedadTurno {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_turno")
    private int idTurno;
    @JsonProperty("categoria")
    private String categoria;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("created_at")
    private String createdAt;

    public NovedadTurno() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
