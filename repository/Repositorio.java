package src.repository;
import java.util.List;
public interface Repositorio <T>{
    void guardar (T objeto);
    T buscarPorId (Long id);
    void eliminar (Long id);
    List <T> listarTodos();
}
