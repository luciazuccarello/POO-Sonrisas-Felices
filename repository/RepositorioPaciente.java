package src.repository;

import src.model.Paciente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioPaciente implements Repositorio<Paciente> {

    private HashMap<Long, Paciente> pacientes;

    public RepositorioPaciente() {
        pacientes = new HashMap<>();
    }

    @Override
    public void guardar(Paciente paciente) {
        pacientes.put(paciente.getId().longValue(), paciente);
    }

    @Override
    public Paciente buscarPorId(Long id) {
        return pacientes.get(id);
    }

    @Override
    public void eliminar(Long id) {
        pacientes.remove(id);
    }

    @Override
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes.values());
    }
}
