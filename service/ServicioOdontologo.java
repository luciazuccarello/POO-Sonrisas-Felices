package model.service;

import model.Odontologo;
import model.repository.IRepositorio;

import java.util.List;

public class ServicioOdontologo {

    private final IRepositorio<Odontologo> repositorio;

    public ServicioOdontologo(IRepositorio<Odontologo> repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarOdontologo(Odontologo odontologo) {
        validarOdontologo(odontologo);
        if (repositorio.buscarPorId(odontologo.getId().longValue()) != null) {
            throw new IllegalArgumentException("Ya existe un odontologo con ese ID");
        }
        repositorio.guardar(odontologo);
    }

    public Odontologo buscarOdontologo(Long id) {
        validarId(id);
        return repositorio.buscarPorId(id);
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        validarOdontologo(odontologo);
        validarExiste(odontologo.getId().longValue());
        repositorio.actualizar(odontologo);
    }

    public void eliminarOdontologo(Long id) {
        validarId(id);
        validarExiste(id);
        repositorio.eliminar(id);
    }

    public List<Odontologo> listarOdontologos() {
        return repositorio.listarTodos();
    }

    private void validarOdontologo(Odontologo odontologo) {
        if (odontologo == null) {
            throw new IllegalArgumentException("Odontologo invalido");
        }
        if (odontologo.getId() == null || odontologo.getId() <= 0) {
            throw new IllegalArgumentException("El ID del odontologo debe ser mayor a cero");
        }
        if (estaVacio(odontologo.getNombre()) || estaVacio(odontologo.getApellido()) || estaVacio(odontologo.getMatricula())) {
            throw new IllegalArgumentException("Nombre, apellido y matricula son obligatorios");
        }
    }

    private void validarExiste(Long id) {
        if (repositorio.buscarPorId(id) == null) {
            throw new IllegalArgumentException("No existe un odontologo con ID " + id);
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
