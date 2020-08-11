package productService.repository;

import java.util.List;

public interface ProductRepository<ID, T> {

    List<T> findAll();

    T findByID(ID id);

    void save(T entity);

    void delete(ID id);

}
