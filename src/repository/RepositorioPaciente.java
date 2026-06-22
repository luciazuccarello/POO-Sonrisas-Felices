package repository;

import model.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioPaciente implements Repositorio<Paciente> {

    private HashMap<Long, Paciente> pacientes;
    private static final String ARCHIVO = "pacientes.dat";

    public RepositorioPaciente() {
        pacientes = new HashMap<>();
        cargarDesdeArchivo(); // Carga automática al prender el sistema
    }

    @Override
    public synchronized void guardar(Paciente paciente) {
        pacientes.put(paciente.getId().longValue(), paciente);
        guardarEnArchivo(); // Persistencia transparente inmediata
    }

    @Override
    public Paciente buscarPorId(Long id) {
        return pacientes.get(id);
    }

    @Override
    public synchronized void eliminar(Long id) {
        pacientes.remove(id);
        guardarEnArchivo(); // Persistencia transparente inmediata
    }

    @Override
    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes.values());
    }

    // --- MÉTODOS DE LA ENTREGA 3 EXIGIDOS POR LA GUI ---
    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            pacientes = (HashMap<Long, Paciente>) ois.readObject();
            System.out.println("[Persistencia] Pacientes recuperados desde " + ARCHIVO);
        } catch (Exception e) {
            System.err.println("[Error Persistencia] No se pudo leer pacientes.dat: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(pacientes);
            oos.flush();
        } catch (Exception e) {
            System.err.println("[Error Persistencia] No se pudo guardar en pacientes.dat: " + e.getMessage());
        }
    }
}