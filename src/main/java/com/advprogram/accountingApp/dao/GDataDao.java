package main.java.com.advprogram.accountingApp.dao;

import main.java.com.advprogram.accountingApp.model.GData;

public interface GDataDao<T> extends GenericDao<GData> {
    void nextDay();
//    void nextMonth();
}
