package service;

import java.util.List;

public interface Crud<T> {
    T create(T t);

    List<T> read(Integer id);

    List<T> read(String name);

    T update(T t);

    void delete(Integer id) ;
}
