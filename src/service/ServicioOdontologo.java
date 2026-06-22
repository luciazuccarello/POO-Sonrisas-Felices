package service;

import exception.ClinicaException;
import exception.OdontologoNoEncontradoException;
import model.Odontologo;
import repository.RepositorioOdontologo;

import java.util.List;

public class ServicioOdontologo {

    private RepositorioOdontologo repositorio;

    public ServicioOdontologo(RepositorioOdontologo repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarOdontologo(Odontologo odontologo) throws ClinicaException {
        if (odontologo == null) {
            throw new ClinicaException("Odontólogo inválido.");
        }
        validarTextoObligatorio(odontologo.getNombre(), "nombre");
        validarTextoObligatorio(odontologo.getApellido(), "apellido");
        validarTextoObligatorio(odontologo.getMail(), "mail");
        validarTextoObligatorio(odontologo.getMatricula(), "matrícula");
        repositorio.guardar(odontologo);
    }

    // Se agrega el throws y la validación
    public Odontologo buscarOdontologo(Long id) throws OdontologoNoEncontradoException {
        Odontologo odontologo = repositorio.buscarPorId(id);
        if (odontologo == null) {
            throw new OdontologoNoEncontradoException("No se encontró el odontólogo con ID: " + id);
        }
        return odontologo;
    }

    public void eliminarOdontologo(Long id) throws OdontologoNoEncontradoException {
        buscarOdontologo(id); // Llama al metodo de arriba para validar si existe
        repositorio.eliminar(id);
    }

    public void actualizarOdontologo(Odontologo odontologo) throws ClinicaException {

        if (odontologo == null) {
            throw new ClinicaException("Odontólogo inválido.");
        }

        repositorio.guardar(odontologo);
    }

    public List<Odontologo> listarOdontologos() {
        return repositorio.listarTodos();
    }

    private void validarTextoObligatorio(String valor, String campo) throws ClinicaException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ClinicaException("El campo " + campo + " es obligatorio.");
        }
    }
}
