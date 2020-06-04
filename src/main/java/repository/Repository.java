package repository;

import java.util.List;

public interface Repository<ID, T> {

    List<T> findAll();

    T findByID(ID id);

    void save(T entity);

    void delete(ID id);

}
