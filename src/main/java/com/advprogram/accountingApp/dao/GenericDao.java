package main.java.com.advprogram.accountingApp.dao;

import main.java.com.advprogram.accountingApp.model.GData;

import java.util.Collection;
import java.util.Optional;

public interface GenericDao<T> {

    Optional<T> get(int id);

    Collection<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}