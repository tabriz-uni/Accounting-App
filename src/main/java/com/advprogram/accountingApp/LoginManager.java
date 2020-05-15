package main.java.com.advprogram.accountingApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

/** Manages control flow for logins */
public class LoginManager extends Node {

    protected ArrayList<Employee> employeeList = new ArrayList<>();

    private final Scene scene;

    public ArrayList<Employee> getEmployeeList() { return employeeList; }

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    public void createArrayLists() throws FileNotFoundException {
        Scanner employeeSc = new Scanner(new File(urls[0]));

        String line;
        while (employeeSc.hasNextLine()) {
            line = employeeSc.nextLine();
            Employee employee = new Employee();
            employee.setName(line.split("@")[0]);
            employee.setId(line.split("@")[1]);
            employee.setPass(line.split("@")[2]);
            employeeList.add(employee);
        }
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticatedAccountant(String sessionID) {
        showAccountantView(sessionID);
    }

    public void authenticatedEmployee(String sessionID) { showEmployeeView(sessionID); }

    /**
     * Callback method invoked to notify that a user has logged out of the main application.
     * Will show the login application screen.
     */
    public void logout() {
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("loginView.fxml")
            );
            scene.setRoot(loader.load());
            LoginController controller =
                    loader.<LoginController>getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAccountantView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("accountantView.fxml")
            );
            scene.setRoot(loader.load());
            AccountantController controller =
                    loader.<AccountantController>getController();
            controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showEmployeeView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("employeeView.fxml")
            );
            scene.setRoot(loader.load());
            EmployeeController controller =
                    loader.getController();
            controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}