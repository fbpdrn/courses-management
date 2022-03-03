package it.unibg.studenti.data.service;

import java.util.List;

public interface DatabaseDAO<T> {
    T getOne(int id);
    List<T> getAll();
    Integer insert(T t);
    Integer update(T t);
    void delete(T t);
    void delete(int id);
}
