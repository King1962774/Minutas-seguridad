package com.minutas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistroVisita {
    @JsonProperty("id")
    private int id;
    @JsonProperty("id_conjunto")
    private int idConjunto;
    @JsonProperty("id_visitante")
    private int idVisitante;
    @JsonProperty("id_unidad")
    private int idUnidad;
    @JsonProperty("id_residente")
    private int idResidente;
    @JsonProperty("id_turno")
    private int idTurno;
    @JsonProperty("vehiculo_placa")
    private String vehiculoPlaca;
    @JsonProperty("observacion")
    private String observacion;
    @JsonProperty("hora_entrada")
    private String horaEntrada;
    @JsonProperty("hora_salida")
    private String horaSalida;
    @JsonProperty("estado")
    private String estado;

    public RegistroVisita() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdConjunto() { return idConjunto; }
    public void setIdConjunto(int idConjunto) { this.idConjunto = idConjunto; }
    public int getIdVisitante() { return idVisitante; }
    public void setIdVisitante(int idVisitante) { this.idVisitante = idVisitante; }
    public int getIdUnidad() { return idUnidad; }
    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }
    public int getIdResidente() { return idResidente; }
    public void setIdResidente(int idResidente) { this.idResidente = idResidente; }
    public int getIdTurno() { return idTurno; }
    public void setIdTurno(int idTurno) { this.idTurno = idTurno; }
    public String getVehiculoPlaca() { return vehiculoPlaca; }
    public void setVehiculoPlaca(String vehiculoPlaca) { this.vehiculoPlaca = vehiculoPlaca; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }
    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
