package src.service;

import src.model.*;
import src.repository.RepositorioTurno;

import java.util.Date;
import java.util.List;

public class ServicioTurno {

    private RepositorioTurno repositorio;

    public ServicioTurno(RepositorioTurno repositorio) {
        this.repositorio = repositorio;
    }

    public Turno crearTurno(Paciente paciente,
                            Odontologo odontologo,
                            Date fecha,
                            Date hora,
                            EstadoTurno estado) {

        if (paciente == null || odontologo == null) {
            throw new IllegalArgumentException("Datos invalidos");
        }

        Turno turno = new Turno();

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(fecha);
        turno.setHora(hora);
        turno.setEstado(estado);

        turno.actualizarMonto();

        repositorio.guardar(turno);

        return turno;
    }

    public void cancelarTurno(Long id) {

        Turno turno = repositorio.buscarPorId(id);

        if (turno != null) {
            turno.setEstado(EstadoTurno.CANCELADO);
        }
    }

    public void reprogramarTurno(Long id,
                                 Date nuevaFecha,
                                 Date nuevaHora) {

        Turno turno = repositorio.buscarPorId(id);

        if (turno != null) {
            turno.setFecha(nuevaFecha);
            turno.setHora(nuevaHora);
            turno.setEstado(EstadoTurno.CONFIRMADO);
        }
    }

    public List<Turno> listarTurnos() {
        return repositorio.listarTodos();
    }
}
