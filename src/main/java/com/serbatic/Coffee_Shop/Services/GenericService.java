package com.serbatic.Coffee_Shop.Services;

import java.util.List;

public interface GenericService<T> {
    List<T> findAll();
    T findById(Long id);
    T save (T item);
    void delete(Long id);
}
