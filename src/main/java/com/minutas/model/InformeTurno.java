package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InformeTurno {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_turno")
    private int idTurno;
    @JsonProperty("resumen_visitantes")
    private int resumenVisitantes;
    @JsonProperty("resumen_vehiculos")
    private int resumenVehiculos;
    @JsonProperty("resumen_paquetes")
    private int resumenPaquetes;
    @JsonProperty("pendientes")
    private String pendientes;
    @JsonProperty("firma_entrega")
    private String firmaEntrega;
    @JsonProperty("firma_recibo")
    private String firmaRecibo;
    @JsonProperty("created_at")
    private String createdAt;

    public InformeTurno() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }
    public int getResumenVisitantes() { return resumenVisitantes; }
    public void setResumenVisitantes(int resumenVisitantes) { this.resumenVisitantes = resumenVisitantes; }
    public int getResumenVehiculos() { return resumenVehiculos; }
    public void setResumenVehiculos(int resumenVehiculos) { this.resumenVehiculos = resumenVehiculos; }
    public int getResumenPaquetes() { return resumenPaquetes; }
    public void setResumenPaquetes(int resumenPaquetes) { this.resumenPaquetes = resumenPaquetes; }
    public String getPendientes() { return pendientes; }
    public void setPendientes(String pendientes) { this.pendientes = pendientes; }
    public String getFirmaEntrega() { return firmaEntrega; }
    public void setFirmaEntrega(String firmaEntrega) { this.firmaEntrega = firmaEntrega; }
    public String getFirmaRecibo() { return firmaRecibo; }
    public void setFirmaRecibo(String firmaRecibo) { this.firmaRecibo = firmaRecibo; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
