package model;

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

    public Turno crearTurno(Paciente paciente, Odontologo odontologo, Date fecha, Date hora, EstadoTurno estado) {
        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(fecha);
        turno.setHora(hora);
        turno.setEstado(estado);
        return turno;
    }

    public void cancelarTurno(Turno turno) {
        turno.setEstado(EstadoTurno.CANCELADO);
    }

    public void reprogramarTurno(Turno turno, Date nuevaFecha, Date nuevaHora) {
        turno.setFecha(nuevaFecha);
        turno.setHora(nuevaHora);
        turno.setEstado(EstadoTurno.CONFIRMADO);
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Apellido: " + apellido + "\n" +
                "Mail Recepcionista: " + mail + "\n";
    }
    }


