package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehiculo {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_residente")
    private int idResidente;
    @JsonProperty("placa")
    private String placa;
    @JsonProperty("marca")
    private String marca;
    @JsonProperty("modelo")
    private String modelo;
    @JsonProperty("color")
    private String color;
    @JsonProperty("tipo")
    private String tipo;

    public Vehiculo() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdResidente() { return idResidente; }
    public void setIdResidente(int idResidente) { this.idResidente = idResidente; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
