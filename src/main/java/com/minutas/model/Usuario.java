package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("rol")
    private String rol;
    @JsonProperty("activo")
    private int activo;

    public Usuario() {}

    public Usuario(int id, int idConjunto, String nombre, String username, String password, String rol, int activo) {
        this.id = id;
        this.idConjunto = idConjunto;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public int getActivo() { return activo; }
    public void setActivo(int activo) { this.activo = activo; }
}
