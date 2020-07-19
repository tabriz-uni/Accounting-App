package main.java.com.advprogram.accountingApp.core;

import main.java.com.advprogram.accountingApp.dao.*;
import main.java.com.advprogram.accountingApp.model.Employee;
import main.java.com.advprogram.accountingApp.model.GData;

import java.util.Optional;

public class Calculator {
    private static final EmployeeDao<Employee> EMPLOYEE_DAO = new EmployeeDaoImp();
    private static final GDataDao<GData> GDATA_DAO = new GDataDaoImp();
    Optional<GData> optionalGData = getGData();
    GData gData= optionalGData.orElseThrow();

    public void nextDay() {
        GDATA_DAO.nextDay();
        EMPLOYEE_DAO.nextDay();
        if (getDay() == 1)
            nextMonth();
    }
    public void nextMonth() {
        EMPLOYEE_DAO.nextMonth();
        gData = getGData().get();
        if (getmonth() == 1)
            nextYear();
    }
    private void nextYear() {
        increGlobalData();
        increEmployeeData();
        gData = getGData().get();
    }
    private int calcEidi(Employee employee) {
        if (getmonth() == 12)
            return employee.getBaseSalary() * 60;
        else return 0;
    }
    public int calcHagBime(Employee employee) {
        if (getmonth() <= 6)
            return ((employee.getBaseSalary() * employee.getWorkedDays()) +
                    gData.getBonNagdi() + gData.getBonMaskan()) * 7/100;
        else
            return ((employee.getBaseSalary() * employee.getWorkedDays()) +
                    gData.getBonNagdi() + gData.getBonMaskan()) * 7/100;
    }
    public int calcHagOlad(Employee employee) {
        int offsprings = employee.getOffsprings();
        if (offsprings > 2)
            offsprings = 2;
        return gData.getHagOlad() * offsprings;
    }
    public int sumSalary(Employee employee) {
        if (getmonth() <= 6)
            return (employee.getBaseSalary() * employee.getWorkedDays()) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan();
        else
            return (employee.getBaseSalary() * employee.getWorkedDays()) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan() +
                    calcEidi(employee);
    }

    public int calcTax(Employee employee) {
        Employee employee2 = new Employee();
        employee2.setBaseSalary(gData.getBaseSalary());
        employee2.setOffsprings(2);
        int finalSal = sumSalary(employee) - calcHagBime(employee);
        int taxFreeAllowance = sumSalary(employee2) - calcHagBime(employee2);
        int secSal = taxFreeAllowance + taxFreeAllowance * 35/100;
        int thirdSal = secSal + secSal * 65/100;
        int fourthSal = thirdSal + thirdSal * 60/100;
        if (finalSal < taxFreeAllowance)
            return 0;
        if (taxFreeAllowance < finalSal  && finalSal < secSal)
            return finalSal * 10/100;
        if (secSal < finalSal && finalSal < thirdSal)
            return finalSal * 15/100;
        if (thirdSal < finalSal && finalSal < fourthSal)
            return finalSal * 20/100;
        return finalSal * 25/100;
    }
    public int calcPostTaxSal(Employee employee) {
        return sumSalary(employee) -
                calcHagBime(employee) - calcTax(employee);
    }

    private Optional<GData> getGData() { return GDATA_DAO.get(1); }
    private void increEmployeeData() {
        EMPLOYEE_DAO.increEmployeeData(getGData());
    }
    private void increGlobalData() {
        GDATA_DAO.update(gData);
    }
    private int getmonth() {
        return gData.getDate().toLocalDate().getMonthValue();
    }
    private int getDay() { return gData.getDate().toLocalDate().getDayOfMonth(); }
}