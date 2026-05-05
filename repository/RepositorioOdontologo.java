package model.repository;

import model.Odontologo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioOdontologo implements IRepositorio<Odontologo> {

    private final HashMap<Long, Odontologo> odontologos;

    public RepositorioOdontologo() {
        odontologos = new HashMap<>();
    }

    @Override
    public void guardar(Odontologo odontologo) {
        odontologos.put(odontologo.getId().longValue(), odontologo);
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        odontologos.put(odontologo.getId().longValue(), odontologo);
    }

    @Override
    public Odontologo buscarPorId(Long id) {
        return odontologos.get(id);
    }

    @Override
    public void eliminar(Long id) {
        odontologos.remove(id);
    }

    @Override
    public List<Odontologo> listarTodos() {
        return new ArrayList<>(odontologos.values());
    }
}
