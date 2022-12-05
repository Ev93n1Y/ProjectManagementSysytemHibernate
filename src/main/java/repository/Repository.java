package repository;

import java.util.List;

public interface Repository<T> {
    T save(T entity);

    List<T> selectById(Integer id);

    List<T> selectByName(String name);

    T update(T t);

    void deleteById(Integer id);
}
