package service;

import exception.ClinicaException;
import exception.PacienteNoEncontradoException;
import model.Paciente;
import repository.RepositorioPaciente;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioPaciente {

    private RepositorioPaciente repositorio;

    public ServicioPaciente(RepositorioPaciente repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarPaciente(Paciente paciente) throws ClinicaException {
        if (paciente == null) {
            throw new ClinicaException("Paciente inválido o faltan datos obligatorios.");
        }
        validarTextoObligatorio(paciente.getNombre(), "nombre");
        validarTextoObligatorio(paciente.getApellido(), "apellido");
        validarTextoObligatorio(paciente.getDni(), "DNI");
        validarTextoObligatorio(paciente.getMail(), "mail");
        repositorio.guardar(paciente);
    }

    public void actualizarPaciente(Paciente paciente) throws ClinicaException {

        if (paciente == null) {
            throw new ClinicaException("Paciente inválido.");
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
    public Paciente buscarPorDni(String dni) throws PacienteNoEncontradoException {
        return repositorio.listarTodos().stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElseThrow(() -> new PacienteNoEncontradoException("No se encontró paciente con DNI: " + dni));
    }

    // Nuevo: Listado ordenado alfabéticamente (Uso avanzado de colecciones y Comparator)
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

    private void validarTextoObligatorio(String valor, String campo) throws ClinicaException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ClinicaException("El campo " + campo + " es obligatorio.");
        }
    }
}
