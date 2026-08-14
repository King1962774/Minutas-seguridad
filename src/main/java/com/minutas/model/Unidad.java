package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Unidad {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("torre")
    private String torre;
    @JsonProperty("numero")
    private String numero;
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("coeficiente")
    private double coeficiente;

    public Unidad() {}

    public Unidad(int id, int idConjunto, String torre, String numero, String tipo, double coeficiente) {
        this.id = id;
        this.idConjunto = idConjunto;
        this.torre = torre;
        this.numero = numero;
        this.tipo = tipo;
        this.coeficiente = coeficiente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public String getTorre() { return torre; }
    public void setTorre(String torre) { this.torre = torre; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getCoeficiente() { return coeficiente; }
    public void setCoeficiente(double coeficiente) { this.coeficiente = coeficiente; }

    @Override
    public String toString() {
        return torre + " - " + numero;
    }
}
