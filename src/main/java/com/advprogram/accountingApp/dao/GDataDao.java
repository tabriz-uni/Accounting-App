package main.java.com.advprogram.accountingApp.dao;

import main.java.com.advprogram.accountingApp.model.GData;

import java.util.Optional;

public interface GDataDao<T> extends GenericDao<GData> {

    void increExp();

    void increBaseSalary();

    void nextMonth();
}
