package src.service;

import src.exception.DatoInvalidoException;
import src.exception.PacienteNoEncontradoException;
import src.model.Paciente;
import src.repository.RepositorioPaciente;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioPaciente {

    private RepositorioPaciente repositorio;

    public ServicioPaciente(RepositorioPaciente repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarPaciente(Paciente paciente) throws DatoInvalidoException {
        if (paciente == null || paciente.getDni() == null) {
            throw new DatoInvalidoException("Paciente inválido o faltan datos obligatorios.");
        }
        repositorio.guardar(paciente);
    }

    // Se agrega el throws y la validación
    public Paciente buscarPaciente(Long id) throws PacienteNoEncontradoException {
        Paciente paciente = repositorio.buscarPorId(id);
        if (paciente == null) {
            throw new PacienteNoEncontradoException("No se encontró el paciente con ID: " + id);
        }
        return paciente;
    }

    // Nuevo: Requisito para el diagrama de secuencia (búsqueda + stream)
    public Paciente buscarPorDni(String dni) throws PacienteNoEncontradoException, DatoInvalidoException {
        if (dni == null || dni.trim().isEmpty()) {
            throw new DatoInvalidoException("DNI inválido. Ingrese un DNI no vacío.");
        }
        return repositorio.listarTodos().stream()
                .filter(p -> p.getDni().equals(dni.trim()))
                .findFirst()
                .orElseThrow(() -> new PacienteNoEncontradoException("No se encontró paciente con DNI: " + dni));
    }

    // Listado ordenado alfabéticamente (Uso de colecciones y comparator)
    public List<Paciente> listarPacientesOrdenadosPorApellido() {
        return repositorio.listarTodos().stream()
                .sorted(Comparator.comparing(Paciente::getApellido, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public void eliminarPaciente(Long id) throws PacienteNoEncontradoException {
        buscarPaciente(id); // Llama al método de arriba para validar si existe
        repositorio.eliminar(id);
    }

    public List<Paciente> listarPacientes() {
        return repositorio.listarTodos();
    }

}
