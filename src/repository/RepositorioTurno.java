package src.repository;

import src.model.Turno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioTurno implements Repositorio<Turno> {

    private HashMap<Long, Turno> turnos;

    public RepositorioTurno() {
        turnos = new HashMap<>();
    }

    @Override
    public void guardar(Turno turno) {
        turnos.put(turno.getId().longValue(), turno);
    }

    @Override
    public Turno buscarPorId(Long id) {
        return turnos.get(id);
    }

    @Override
    public void eliminar(Long id) {
        turnos.remove(id);
    }

    @Override
    public List<Turno> listarTodos() {
        return new ArrayList<>(turnos.values());
    }
}
