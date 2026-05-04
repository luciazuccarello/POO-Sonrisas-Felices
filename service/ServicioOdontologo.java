package src.service;

import src.model.Odontologo;
import src.repository.RepositorioOdontologo;

import java.util.List;

public class ServicioOdontologo {

    private RepositorioOdontologo repositorio;

    public ServicioOdontologo(RepositorioOdontologo repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarOdontologo(Odontologo odontologo) {

        if (odontologo == null) {
            throw new IllegalArgumentException("Odontologo invalido");
        }

        repositorio.guardar(odontologo);
    }

    public Odontologo buscarOdontologo(Long id) {
        return repositorio.buscarPorId(id);
    }

    public void eliminarOdontologo(Long id) {
        repositorio.eliminar(id);
    }

    public List<Odontologo> listarOdontologos() {
        return repositorio.listarTodos();
    }
}
