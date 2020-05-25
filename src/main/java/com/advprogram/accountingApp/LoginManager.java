package main.java.com.advprogram.accountingApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import main.java.com.advprogram.accountingApp.api.Employee;

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
    public void authenticatedAccountant(String sessionID) {
        showAccountantView(sessionID);
    }

    public void authenticatedEmployee(Integer sessionID) { showEmployeeView(sessionID); }

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

    private void showAccountantView(String sessionID) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("accountantView.fxml")
            );
            scene.setRoot(loader.load());
            AccountantController controller =
                    loader.getController();
            controller.initSessionID(this, sessionID);
        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showEmployeeView(Integer sessionID) {
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