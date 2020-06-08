package main.java.com.advprogram.accountingApp.api;

import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;

public class Calculator {
    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();
    GData gData= getGData();
    private void nextMonth() {
        gData = getGData();
        DAO.nextMonth();
        if (getmonth() == 1)
            nextYear();
    }
    private void nextYear() {
        increGlobalData();
        increExp();
        increBaseSalary();
        gData = getGData();
    }
    private int calcEidi(Employee employee) {
        if (getmonth() == 12)
            return employee.getBaseSalary() * 60;
        else return 0;
    }
    public int calcHagBime(Employee employee) {
        if (getmonth() <= 6)
            return ((employee.getBaseSalary() * 31) +
                    gData.getBonNagdi() + gData.getBonMaskan()) * 7/100;
        else
            return ((employee.getBaseSalary() * 30) +
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
            return (employee.getBaseSalary() * 31) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan();
        else
            return (employee.getBaseSalary() * 30) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan() +
                    calcEidi(employee);
    }

    public int calcTax(Employee employee) {
        Employee employee2 = new Employee();
        employee2.setBaseSalary(gData.baseSalary);
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

    private GData getGData() { return DAO.getGData(); }
    private void increExp() {
        DAO.increExp();
    }
    private void increBaseSalary() {
        DAO.increBaseSalary();
    }
    private void increGlobalData() {
        DAO.increGlobalData();
    }
    private int getmonth() {
        return gData.getDate().toLocalDate().getMonthValue();
    }
}