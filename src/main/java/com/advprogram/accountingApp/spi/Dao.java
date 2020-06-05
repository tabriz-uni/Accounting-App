package main.java.com.advprogram.accountingApp.spi;

import main.java.com.advprogram.accountingApp.api.GData;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

public interface Dao<T, I> {

    Optional<T> get(int id);

    Collection<T> getAll();

    GData getGData();

    Optional<I> save(T t);

    void update(T t);

    void delete(T t);

    void increExp();

    void increBaseSalary();

    void increGlobalData();

    void nextMonth();

}