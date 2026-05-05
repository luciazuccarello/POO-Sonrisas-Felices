package model.service;

import model.EstadoTurno;
import model.Odontologo;
import model.Paciente;
import model.Turno;
import model.repository.IRepositorio;

import java.util.Date;
import java.util.List;

public class ServicioTurno {

    private final IRepositorio<Turno> repositorio;
    private final ServicioPaciente servicioPaciente;
    private final ServicioOdontologo servicioOdontologo;

    public ServicioTurno(IRepositorio<Turno> repositorio,
                         ServicioPaciente servicioPaciente,
                         ServicioOdontologo servicioOdontologo) {
        this.repositorio = repositorio;
        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;
    }

    public Turno crearTurno(Long idPaciente,
                            Long idOdontologo,
                            Date fecha,
                            Date hora,
                            EstadoTurno estado) {
        if (fecha == null || hora == null) {
            throw new IllegalArgumentException("Fecha y hora son obligatorias");
        }

        Paciente paciente = servicioPaciente.buscarPaciente(idPaciente);
        Odontologo odontologo = servicioOdontologo.buscarOdontologo(idOdontologo);

        if (paciente == null) {
            throw new IllegalArgumentException("No existe el paciente con ID " + idPaciente);
        }
        if (odontologo == null) {
            throw new IllegalArgumentException("No existe el odontologo con ID " + idOdontologo);
        }

        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(fecha);
        turno.setHora(hora);
        turno.setEstado(estado == null ? EstadoTurno.PENDIENTE : estado);

        turno.actualizarMonto();
        repositorio.guardar(turno);
        return turno;
    }

    public Turno buscarTurno(Long id) {
        validarId(id);
        return repositorio.buscarPorId(id);
    }

    public void actualizarTurno(Long id,
                                Long idPaciente,
                                Long idOdontologo,
                                Date fecha,
                                Date hora,
                                EstadoTurno estado) {
        Turno turno = obtenerTurnoExistente(id);
        Paciente paciente = servicioPaciente.buscarPaciente(idPaciente);
        Odontologo odontologo = servicioOdontologo.buscarOdontologo(idOdontologo);

        if (paciente == null) {
            throw new IllegalArgumentException("No existe el paciente con ID " + idPaciente);
        }
        if (odontologo == null) {
            throw new IllegalArgumentException("No existe el odontologo con ID " + idOdontologo);
        }
        if (fecha == null || hora == null || estado == null) {
            throw new IllegalArgumentException("Fecha, hora y estado son obligatorios");
        }

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(fecha);
        turno.setHora(hora);
        turno.setEstado(estado);
        turno.actualizarMonto();
        repositorio.actualizar(turno);
    }

    public void cancelarTurno(Long id) {
        Turno turno = obtenerTurnoExistente(id);
        turno.setEstado(EstadoTurno.CANCELADO);
        repositorio.actualizar(turno);
    }

    public void reprogramarTurno(Long id,
                                 Date nuevaFecha,
                                 Date nuevaHora) {
        if (nuevaFecha == null || nuevaHora == null) {
            throw new IllegalArgumentException("La nueva fecha y hora son obligatorias");
        }

        Turno turno = obtenerTurnoExistente(id);
        turno.setFecha(nuevaFecha);
        turno.setHora(nuevaHora);
        turno.setEstado(EstadoTurno.CONFIRMADO);
        repositorio.actualizar(turno);
    }

    public void eliminarTurno(Long id) {
        obtenerTurnoExistente(id);
        repositorio.eliminar(id);
    }

    public List<Turno> listarTurnos() {
        return repositorio.listarTodos();
    }

    private Turno obtenerTurnoExistente(Long id) {
        validarId(id);
        Turno turno = repositorio.buscarPorId(id);
        if (turno == null) {
            throw new IllegalArgumentException("No existe un turno con ID " + id);
        }
        return turno;
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
    }
}
