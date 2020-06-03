package main.java.com.advprogram.accountingApp.api;

public class Employee extends User {

    private String title;
    private int offsprings;
    private int workExp;
    private int workExpHere;
    private int baseSalary;

    public Employee(Integer id, String firstName, String lastName, String pass) {
        super(id, firstName, lastName, pass);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOffsprings() {
        return offsprings;
    }

    public void setOffsprings(int offsprings) {
        this.offsprings = offsprings;
    }

    public int getWorkExp() {
        return workExp;
    }

    public void setWorkExp(int workExp) {
        this.workExp = workExp;
    }

    public int getWorkExpHere() {
        return workExpHere;
    }

    public void setWorkExpHere(int workExpHere) {
        this.workExpHere = workExpHere;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }
}