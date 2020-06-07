package main.java.com.advprogram.accountingApp.api;

import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;

public class Calculator {
    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();

    private void nextMonth() {
        DAO.nextMonth();
        if (getmonth() == 1)
            nextYear();
    }
    private void nextYear() {
        increGlobalData();
        increExp();
        increBaseSalary();
    }
    private int calcEidi(Employee employee) {
        if (getmonth() == 12)
            return employee.getBaseSalary() * 60;
        else return 0;
    }
    private int calcHagBime(Employee employee) {
        GData gData = getGData();
        if (getmonth() <= 6)
            return ((employee.getBaseSalary() * 31) + gData.getBonNagdi() + gData.getBonMaskan()) * 7/100;
        else
            return ((employee.getBaseSalary() * 30) + gData.getBonNagdi() + gData.getBonMaskan()) * 7/100;
    }
    public int calcHagOlad(Employee employee) {
        GData gData= getGData();
        int offsprings = employee.getOffsprings();
        if (offsprings > 2)
            offsprings = 2;
        return gData.getHagOlad() * offsprings;
    }
    private int sumSalary(Employee employee) {
        GData gData= getGData();
        if (getmonth() <= 6)
            return (employee.getBaseSalary() * 31) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan();
        else if (6 <= getmonth() && getmonth() <= 11)
            return (employee.getBaseSalary() * 30) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan();
        else
            return (employee.getBaseSalary() * 30) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan() + calcEidi(employee);
    }

    private  int calcTax(Employee employee) {
        GData gData = getGData();
        Employee employee2 = new Employee();
        employee2.setBaseSalary(gData.baseSalary);
        employee2.setOffsprings(2);
        int finalSal = sumSalary(employee);
        int taxFreeAllowance = sumSalary(employee2);
        int secSal = taxFreeAllowance + taxFreeAllowance * 35/100;
        int thirdSal = secSal + secSal * 65/100;
        int fourthSal = thirdSal + thirdSal * 60/100;
        if (finalSal < taxFreeAllowance)
            return 0;
        if (taxFreeAllowance < finalSal  && finalSal < secSal)
            return finalSal - (finalSal * 10/100);
        if (secSal < finalSal && finalSal < thirdSal)
            return finalSal - (finalSal * 15/100);
        if (thirdSal < finalSal && finalSal < fourthSal)
            return finalSal - (finalSal * 20/100);
        return finalSal - (finalSal * 25/100);
    }
    public int calcPostTaxSal(Employee employee) {
        GData gData= getGData();
        if (getmonth() <= 6)
            return (employee.getBaseSalary() * 31) + calcHagOlad(employee) +
                    gData.getBonNagdi() + gData.getBonMaskan() - calcHagBime(employee) - calcTax(employee);
        else
            return (employee.getBaseSalary() * 30) + calcHagOlad(employee) + gData.getBonNagdi() +
                    gData.getBonMaskan() + calcEidi(employee) - calcHagBime(employee) - calcTax(employee);
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
        GData gData = getGData();
        return gData.getDate().toLocalDate().getMonthValue();
    }
}