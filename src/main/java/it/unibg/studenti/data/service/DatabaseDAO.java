package it.unibg.studenti.data.service;

import java.util.List;

public interface DatabaseDAO<T> {
    T getOne(int id);
    List<T> getAll();
    int insert(T t);
    void update(T t);
    void delete(T t);
    void delete(int id);
}
