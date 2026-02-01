package com.simon.repo;

import java.util.List;
import java.util.Optional;

public interface Repo<T, ID> {
    void save(T entity);
    void update(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void delete(T entity);
    void deleteById(ID id);
}
