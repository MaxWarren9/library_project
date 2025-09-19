package org.example.repository;

import java.util.Map;

public interface Repository<T> {
    void add(String str);
    Map<Integer, T> getAll();
    T getById(int id);
}
