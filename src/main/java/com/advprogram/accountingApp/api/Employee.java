package main.java.com.advprogram.accountingApp.api;

public class Employee extends User {

    private int offsprings;
    private int workExp;
    private int workExpHere;
    private int baseSalary;
    private String title;

    public Employee(int id, String firstName, String lastName, String pass, int offsprings, String title, int workExp, int workExpHere, int baseSalary) {
        super(id, firstName, lastName, pass);
        this.offsprings = offsprings;
        this.title = title;
        this.workExp = workExp;
        this.workExpHere = workExpHere;
        this.baseSalary = baseSalary;
    }

    public Employee() {
        super();
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