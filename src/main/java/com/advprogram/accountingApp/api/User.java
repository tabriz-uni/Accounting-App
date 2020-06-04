package main.java.com.advprogram.accountingApp.api;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String pass;

    public User(int id, String firstName, String lastName, String pass) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
