package src.service;

import src.exception.DatoInvalidoException;
import src.exception.TurnoNoEncontradoException;
import src.exception.TurnoYaReservadoException;
import src.model.*;
import src.repository.RepositorioTurno;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioTurno {

    private RepositorioTurno repositorio;

    public ServicioTurno(RepositorioTurno repositorio) {
        this.repositorio = repositorio;
    }

    public Turno crearTurno(Paciente paciente, Odontologo odontologo, Date fecha, Date hora, EstadoTurno estado)
            throws DatoInvalidoException, TurnoYaReservadoException {

        if (paciente == null || odontologo == null || fecha == null || hora == null) {
            throw new DatoInvalidoException("Datos de paciente, odontólogo o fecha/hora inválidos.");
        }

        // Nuevo: Validación de turno ya ocupado usando Stream API
        boolean horarioOcupado = repositorio.listarTodos().stream()
                .anyMatch(t -> t.getOdontologo().getId().equals(odontologo.getId())
                        && t.getFecha().equals(fecha)
                        && t.getHora().equals(hora)
                        && t.getEstado() != EstadoTurno.CANCELADO);

        if (horarioOcupado) {
            throw new TurnoYaReservadoException("El odontólogo ya tiene un turno reservado en esa fecha y hora.");
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

    // Búsqueda por rango de fechas.
    public List<Turno> buscarTurnosPorRango(Date fechaInicio, Date fechaFin) {
        return repositorio.listarTodos().stream()
                .filter(t -> !t.getFecha().before(fechaInicio) && !t.getFecha().after(fechaFin))
                .collect(Collectors.toList());
    }

    // Filtrar por paciente
    public List<Turno> filtrarPorPaciente(Integer idPaciente) {
        return repositorio.listarTodos().stream()
                .filter(t -> t.getPaciente().getId().equals(idPaciente))
                .collect(Collectors.toList());
    }

    // Filtrar por odontólogo
    public List<Turno> filtrarPorOdontologo(Integer idOdontologo) {
        return repositorio.listarTodos().stream()
                .filter(t -> t.getOdontologo().getId().equals(idOdontologo))
                .collect(Collectors.toList());
    }

    public void cancelarTurno(Long id) throws TurnoNoEncontradoException {
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null) {
            throw new TurnoNoEncontradoException("No se encontró el turno con ID: " + id);
        }
        turno.setEstado(EstadoTurno.CANCELADO);
    }


    public void reprogramarTurno(Long id, Date nuevaFecha, Date nuevaHora) throws TurnoNoEncontradoException, DatoInvalidoException {
        if (nuevaFecha == null || nuevaHora == null) {
            throw new DatoInvalidoException("Fecha u hora nueva inválida.");
        }
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null) {
            throw new TurnoNoEncontradoException("No se encontró el turno con ID: " + id);
        }
        turno.setFecha(nuevaFecha);
        turno.setHora(nuevaHora);
        turno.setEstado(EstadoTurno.CONFIRMADO);
    }

    public List<Turno> listarTurnos() {
        return repositorio.listarTodos();
    }

    public Turno buscarTurno(Long id) throws TurnoNoEncontradoException {
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null) {
            throw new TurnoNoEncontradoException("No se encontró el turno con ID: " + id);
        }
        return turno;
    }

    public void eliminarTurno(Long id) throws TurnoNoEncontradoException {
        buscarTurno(id);
        repositorio.eliminar(id);
    }


}
