package src.model;

import java.util.Date;

public class Recepcion {

    private String nombre;
    private String apellido;
    private String mail;

    public Recepcion() {}

    public Recepcion(String nombre, String apellido, String mail) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
    }

    public String getNombre() {
        return nombre; }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public src.model.Turno crearTurno(src.model.Paciente paciente, src.model.Odontologo odontologo, Date fecha, Date hora, src.model.EstadoTurno estado) {
        src.model.Turno turno = new src.model.Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(fecha);
        turno.setHora(hora);
        turno.setEstado(estado);
        return turno;
    }

    public void cancelarTurno(src.model.Turno turno) {
        turno.setEstado(src.model.EstadoTurno.CANCELADO);
    }

    public void reprogramarTurno(src.model.Turno turno, Date nuevaFecha, Date nuevaHora) {
        turno.setFecha(nuevaFecha);
        turno.setHora(nuevaHora);
        turno.setEstado(src.model.EstadoTurno.CONFIRMADO);
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido + "\n" +
                "Mail Recepcionista: " + mail + "\n";
    }
    }

