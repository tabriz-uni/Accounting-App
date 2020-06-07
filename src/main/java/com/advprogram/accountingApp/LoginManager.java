package main.java.com.advprogram.accountingApp;

import java.io.*;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import main.java.com.advprogram.accountingApp.api.Accountant;
import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.api.NonExistentEntityException;

/** Manages control flow for logins */
public class LoginManager extends Node {

    private final Scene scene;

    public LoginManager(Scene scene) {
        this.scene = scene;
    }

    /**
     * Callback method invoked to notify that a user has been authenticated.
     * Will show the main application screen.
     */
    public void authenticatedAccountant(Accountant accountant) {
        showAccountantView(accountant);
    }

    public void authenticatedEmployee(Employee sessionID) { showEmployeeView(sessionID); }

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
                    loader.getController();
            controller.initManager(this);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showAccountantView(Accountant accountant) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("accountantView.fxml")
            );
            scene.setRoot(loader.load());
            AccountantController controller =
                    loader.getController();
            controller.initSessionID(this, accountant);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showEmployeeView(Employee employee) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("employeeView.fxml")
            );
            scene.setRoot(loader.load());
            EmployeeController controller =
                    loader.getController();
            controller.initSessionID(this, employee);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}