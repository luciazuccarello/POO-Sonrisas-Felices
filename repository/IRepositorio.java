package model.repository;

import java.util.List;

public interface IRepositorio<T> {
    void guardar(T objeto);

    T buscarPorId(Long id);

    void actualizar(T objeto);

    void eliminar(Long id);

    List<T> listarTodos();
}
