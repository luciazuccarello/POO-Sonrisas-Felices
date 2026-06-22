package service;

import exception.ClinicaException;
import exception.TurnoYaReservadoException;
import model.*;
import model.Turno;
import repository.RepositorioTurno;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioTurno {

    private RepositorioTurno repositorio;

    public ServicioTurno(RepositorioTurno repositorio) {
        this.repositorio = repositorio;
    }

    public Turno crearTurno(Paciente paciente, Odontologo odontologo, Date fecha, Date hora, EstadoTurno estado)
            throws ClinicaException, TurnoYaReservadoException {

        if (paciente == null || odontologo == null || fecha == null || hora == null || estado == null) {
            throw new ClinicaException("Datos de paciente u odontólogo inválidos.");
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

    // Nuevo: Búsqueda por rango de fechas (Uso avanzado de colecciones)
    public List<Turno> buscarTurnosPorRango(Date fechaInicio, Date fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return List.of();
        }
        return repositorio.listarTodos().stream()
                .filter(t -> !t.getFecha().before(fechaInicio) && !t.getFecha().after(fechaFin))
                .collect(Collectors.toList());
    }

    // Nuevo: Filtrar por paciente
    public List<Turno> filtrarPorPaciente(Integer idPaciente) {
        if (idPaciente == null) {
            return List.of();
        }
        return repositorio.listarTodos().stream()
                .filter(t -> t.getPaciente().getId().equals(idPaciente))
                .collect(Collectors.toList());
    }

    // Nuevo: Filtrar por odontólogo
    public List<Turno> filtrarPorOdontologo(Integer idOdontologo) {
        if (idOdontologo == null) {
            return List.of();
        }
        return repositorio.listarTodos().stream()
                .filter(t -> t.getOdontologo().getId().equals(idOdontologo))
                .collect(Collectors.toList());
    }

    public void cancelarTurno(Long id) throws ClinicaException {
        Turno turno = buscarTurno(id);
        turno.setEstado(EstadoTurno.CANCELADO);
        repositorio.guardar(turno);
    }

    public void reprogramarTurno(Long id, Date nuevaFecha, Date nuevaHora) throws ClinicaException {
        Turno turno = buscarTurno(id);
        turno.setFecha(nuevaFecha);
        turno.setHora(nuevaHora);
        turno.setEstado(EstadoTurno.CONFIRMADO);
        repositorio.guardar(turno);
    }

    public List<Turno> listarTurnos() {
        return repositorio.listarTodos();
    }

    public Turno buscarTurno(Long id) throws ClinicaException {
        if (id == null) {
            throw new ClinicaException("El ID del turno no puede ser nulo.");
        }
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null) {
            throw new ClinicaException("No se encontró el turno con ID: " + id);
        }
        return turno;
    }

    public void eliminarTurno(Long id) throws ClinicaException {
        buscarTurno(id);
        repositorio.eliminar(id);
    }
}
