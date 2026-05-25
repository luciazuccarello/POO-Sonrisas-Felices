package src.service;

import src.exception.DatoInvalidoException;
import src.exception.OdontologoNoEncontradoException;
import src.model.Odontologo;
import src.repository.RepositorioOdontologo;

import java.util.List;

public class ServicioOdontologo {

    private RepositorioOdontologo repositorio;

    public ServicioOdontologo(RepositorioOdontologo repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarOdontologo(Odontologo odontologo) throws DatoInvalidoException {
        if (odontologo == null || odontologo.getMatricula() == null) {
            throw new DatoInvalidoException("Odontólogo inválido.");
        }
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

    public List<Odontologo> listarOdontologos() {
        return repositorio.listarTodos();
    }
}
