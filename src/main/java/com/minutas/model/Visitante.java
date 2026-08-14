package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Visitante {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("documento")
    private String documento;
    @JsonProperty("telefono")
    private String telefono;
    @JsonProperty("observaciones")
    private String observaciones;
    @JsonProperty("lista_negra")
    private int listaNegra;

    public Visitante() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public int getListaNegra() { return listaNegra; }
    public void setListaNegra(int listaNegra) { this.listaNegra = listaNegra; }

    @Override
    public String toString() {
        return nombre + " (" + documento + ")";
    }
}
