package model.service;

import model.Paciente;
import model.repository.IRepositorio;

import java.util.List;

public class ServicioPaciente {

    private final IRepositorio<Paciente> repositorio;

    public ServicioPaciente(IRepositorio<Paciente> repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarPaciente(Paciente paciente) {
        validarPaciente(paciente);
        if (repositorio.buscarPorId(paciente.getId().longValue()) != null) {
            throw new IllegalArgumentException("Ya existe un paciente con ese ID");
        }
        repositorio.guardar(paciente);
    }

    public Paciente buscarPaciente(Long id) {
        validarId(id);
        return repositorio.buscarPorId(id);
    }

    public void actualizarPaciente(Paciente paciente) {
        validarPaciente(paciente);
        validarExiste(paciente.getId().longValue());
        repositorio.actualizar(paciente);
    }

    public void eliminarPaciente(Long id) {
        validarId(id);
        validarExiste(id);
        repositorio.eliminar(id);
    }

    public List<Paciente> listarPacientes() {
        return repositorio.listarTodos();
    }

    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente invalido");
        }
        if (paciente.getId() == null || paciente.getId() <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser mayor a cero");
        }
        if (estaVacio(paciente.getNombre()) || estaVacio(paciente.getApellido()) || estaVacio(paciente.getDni())) {
            throw new IllegalArgumentException("Nombre, apellido y DNI son obligatorios");
        }
    }

    private void validarExiste(Long id) {
        if (repositorio.buscarPorId(id) == null) {
            throw new IllegalArgumentException("No existe un paciente con ID " + id);
        }
    }

    private void validarId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
