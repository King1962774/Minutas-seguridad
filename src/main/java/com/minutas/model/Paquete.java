package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paquete {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_unidad")
    private int idUnidad;
    @JsonProperty("id_residente")
    private int idResidente;
    @JsonProperty("empresa_mensajeria")
    private String empresaMensajeria;
    @JsonProperty("guia")
    private String guia;
    @JsonProperty("descripcion")
    private String descripcion;
    @JsonProperty("recibido_por")
    private int recibidoPor;
    @JsonProperty("estado")
    private String estado;
    @JsonProperty("hora_recepcion")
    private String horaRecepcion;
    @JsonProperty("hora_entrega")
    private String horaEntrega;
    @JsonProperty("firma_entrega")
    private String firmaEntrega;

    public Paquete() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdUnidad() { return idUnidad; }
    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }
    public int getIdResidente() { return idResidente; }
    public void setIdResidente(int idResidente) { this.idResidente = idResidente; }
    public String getEmpresaMensajeria() { return empresaMensajeria; }
    public void setEmpresaMensajeria(String empresaMensajeria) { this.empresaMensajeria = empresaMensajeria; }
    public String getGuia() { return guia; }
    public void setGuia(String guia) { this.guia = guia; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getRecibidoPor() { return recibidoPor; }
    public void setRecibidoPor(int recibidoPor) { this.recibidoPor = recibidoPor; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getHoraRecepcion() { return horaRecepcion; }
    public void setHoraRecepcion(String horaRecepcion) { this.horaRecepcion = horaRecepcion; }
    public String getHoraEntrega() { return horaEntrega; }
    public void setHoraEntrega(String horaEntrega) { this.horaEntrega = horaEntrega; }
    public String getFirmaEntrega() { return firmaEntrega; }
    public void setFirmaEntrega(String firmaEntrega) { this.firmaEntrega = firmaEntrega; }
}
