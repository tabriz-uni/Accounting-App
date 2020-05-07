package main.java.com.advprogram.accountingApp.api;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public User(String firstName, String lastName) {
        this(firstName, lastName, null);
    }

    public User(String firstName, String lastName, String email) {
        this(null, firstName, lastName, email);
    }

    public User(Integer id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User["
                + "id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ']';
    }

}