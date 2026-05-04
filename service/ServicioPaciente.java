package src.service;

import src.model.Paciente;
import src.repository.RepositorioPaciente;

import java.util.List;

public class ServicioPaciente {

    private RepositorioPaciente repositorio;

    public ServicioPaciente(RepositorioPaciente repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarPaciente(Paciente paciente) {

        if (paciente == null) {
            throw new IllegalArgumentException("Paciente invalido");
        }

        repositorio.guardar(paciente);
    }

    public Paciente buscarPaciente(Long id) {
        return repositorio.buscarPorId(id);
    }

    public void eliminarPaciente(Long id) {
        repositorio.eliminar(id);
    }

    public List<Paciente> listarPacientes() {
        return repositorio.listarTodos();
    }
}
