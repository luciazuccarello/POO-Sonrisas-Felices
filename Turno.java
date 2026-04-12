package model;

import java.util.Date;

public class Turno {

    private Integer id;
    private Paciente paciente;
    private Odontologo odontologo;
    private Date fecha;
    private Date hora;
    private boolean activo;

    public Turno() {}

    public Turno(Integer id, Paciente paciente, Odontologo odontologo, Date fecha, Date hora, boolean activo) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
        this.hora = hora;
        this.activo = activo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Odontologo getOdontologo() { return odontologo; }
    public void setOdontologo(Odontologo odontologo) { this.odontologo = odontologo; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Date getHora() { return hora; }
    public void setHora(Date hora) { this.hora = hora; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", odontologo=" + odontologo +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", activo=" + activo +
                '}';
    }
}