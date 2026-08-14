package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Conjunto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("nit")
    private String nit;
    @JsonProperty("direccion")
    private String direccion;
    @JsonProperty("telefono")
    private String telefono;

    public Conjunto() {}

    public Conjunto(int id, String nombre, String nit, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
