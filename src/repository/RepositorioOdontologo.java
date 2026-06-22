package repository;

import model.Odontologo;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioOdontologo implements repository.Repositorio<Odontologo> {

    private HashMap<Long, Odontologo> odontologos;
    private static final String ARCHIVO = "odontologos.dat";

    public RepositorioOdontologo() {
        odontologos = new HashMap<>();
        cargarDesdeArchivo(); // Carga automática al prender el sistema
    }

    @Override
    public synchronized void guardar(Odontologo odontologo) {
        odontologos.put(odontologo.getId().longValue(), odontologo);
        guardarEnArchivo(); // Persistencia transparente inmediata
    }

    @Override
    public Odontologo buscarPorId(Long id) {
        return odontologos.get(id);
    }

    @Override
    public synchronized void eliminar(Long id) {
        odontologos.remove(id);
        guardarEnArchivo(); // Persistencia transparente inmediata
    }

    @Override
    public List<Odontologo> listarTodos() {
        return new ArrayList<>(odontologos.values());
    }

    // --- MÉTODOS DE LA ENTREGA 3 EXIGIDOS POR LA GUI ---
    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            odontologos = (HashMap<Long, Odontologo>) ois.readObject();
            System.out.println("[Persistencia] Odontólogos recuperados desde " + ARCHIVO);
        } catch (Exception e) {
            System.err.println("[Error Persistencia] No se pudo leer odontologos.dat: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(odontologos);
            oos.flush();
        } catch (Exception e) {
            System.err.println("[Error Persistencia] No se pudo guardar en odontologos.dat: " + e.getMessage());
        }
    }
}