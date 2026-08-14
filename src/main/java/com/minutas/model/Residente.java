package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Residente {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_unidad")
    private int idUnidad;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("documento")
    private String documento;
    @JsonProperty("telefono")
    private String telefono;
    @JsonProperty("email")
    private String email;
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("activo")
    private int activo;

    public Residente() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdUnidad() { return idUnidad; }
    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getActivo() { return activo; }
    public void setActivo(int activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + " (" + documento + ")";
    }
}
