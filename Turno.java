package model;

import java.util.Date;

public class Turno {
    private static int contador = 1;
    private Integer id;
    private Paciente paciente;
    private Odontologo odontologo;
    private Date fecha;
    private Date hora;
    private EstadoTurno estado;

    public Turno() {
        this.id = contador++;
    }

    public Turno(Integer id, Paciente paciente, Odontologo odontologo, Date fecha, Date hora, EstadoTurno estado) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public Integer getId() {
        return id; }
    public void setId(Integer id) {
        this.id = id; }

    public Paciente getPaciente() {
        return paciente; }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente; }

    public Odontologo getOdontologo() {
        return odontologo; }
    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo; }

    public Date getFecha() {
        return fecha; }
    public void setFecha(Date fecha) {
        this.fecha = fecha; }

    public Date getHora() {
        return hora; }
    public void setHora(Date hora) {
        this.hora = hora; }

    public EstadoTurno getEstado() {
        return estado;
    }
    public void setEstado(EstadoTurno estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ID turno: " + id + "\n" +
                "Paciente: " + paciente.getNombre() + " " + paciente.getApellido() + " (ID Paciente: " + paciente.getId() + " )\n" +
                "Odontólogo: " + odontologo.getNombre() + " " + odontologo.getApellido() + " (ID Odontólogo: " + odontologo.getId() + ")\n" +
                "Fecha: " + fecha + "\n" +
                "Estado: " + estado;
    }
}
