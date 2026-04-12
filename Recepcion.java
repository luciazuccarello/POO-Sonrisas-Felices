package model;

import java.util.Date;

public class Recepcion {

    private String nombre;

    public Recepcion() {}

    public Recepcion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Turno crearTurno(Paciente paciente, Odontologo odontologo, Date fecha, Date hora) {
        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(fecha);
        turno.setHora(hora);
        turno.setActivo(true);
        return turno;
    }

    public void cancelarTurno(Turno turno) {
        turno.setActivo(false);
    }

    public void reprogramarTurno(Turno turno, Date nuevaFecha, Date nuevaHora) {
        turno.setFecha(nuevaFecha);
        turno.setHora(nuevaHora);
    }

    @Override
    public String toString() {
        return "Recepcionista{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
