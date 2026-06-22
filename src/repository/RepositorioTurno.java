package repository;

import model.Turno;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioTurno implements Repositorio<Turno> {

    private HashMap<Long, Turno> turnos;
    private static final String ARCHIVO = "turnos.dat";

    public RepositorioTurno() {
        turnos = new HashMap<>();
        cargarDesdeArchivo(); // Carga automática al prender el sistema
    }

    @Override
    public synchronized void guardar(Turno turno) {
        turnos.put(turno.getId().longValue(), turno);
        guardarEnArchivo(); // Persistencia transparente inmediata
    }

    @Override
    public Turno buscarPorId(Long id) {
        return turnos.get(id);
    }

    @Override
    public synchronized void eliminar(Long id) {
        turnos.remove(id);
        guardarEnArchivo(); // Persistencia transparente inmediata
    }

    @Override
    public List<Turno> listarTodos() {
        return new ArrayList<>(turnos.values());
    }

    // --- MÉTODOS DE LA ENTREGA 3 EXIGIDOS POR LA GUI ---
    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            turnos = (HashMap<Long, Turno>) ois.readObject();
            System.out.println("[Persistencia] Turnos recuperados desde " + ARCHIVO);
        } catch (Exception e) {
            System.err.println("[Error Persistencia] No se pudo leer turnos.dat: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(turnos);
            oos.flush();
        } catch (Exception e) {
            System.err.println("[Error Persistencia] No se pudo guardar en turnos.dat: " + e.getMessage());
        }
    }
}
