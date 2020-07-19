package main.java.com.advprogram.accountingApp.dao;

import main.java.com.advprogram.accountingApp.model.Employee;
import main.java.com.advprogram.accountingApp.model.GData;

import java.util.Optional;

public interface EmployeeDao<T> extends GenericDao<Employee> {

    void increEmployeeData(Optional<GData> ref);
}
