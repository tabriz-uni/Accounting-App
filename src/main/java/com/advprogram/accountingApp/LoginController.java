package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.java.com.advprogram.accountingApp.api.Employee;

import java.util.ArrayList;

/** Controls the login screen */
public class LoginController {
    @FXML private TextField user;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Label showPasswordHover;
    @FXML private Button showPassword;
    @FXML private JFXSlider type;
    @FXML private Label InvalidCrdLbl;
    private String revealedPassword;
    private final String accountant = "Ali Shams@1@1";

    public void initialize() {

    }
    public void initManager(final LoginManager loginManager){
        showPassword.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            revealedPassword = password.getText();
            password.clear();
            password.setStyle("-fx-prompt-text-fill: white;");
            password.setPromptText(revealedPassword);
        });
        showPassword.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            password.setText(revealedPassword);
            password.setStyle("-fx-prompt-text-fill: grey");
            password.setPromptText("Password");
        });
        showPassword.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, e -> {
            showPasswordHover.setVisible(true);
        });
        showPassword.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, e -> {
            showPasswordHover.setVisible(false);
        });

        loginButton.setOnAction(event -> {
            if (type.getValue() == 0)
            {
                String Info;
                Info = authorizeAccountant();
                if (Info == null)
                    InvalidCrdLbl.setVisible(true);
                if (Info != null) {
                    InvalidCrdLbl.setVisible(false);
                    loginManager.authenticatedAccountant(Info);
                }
            }

            else if (type.getValue() == 2)
            {
                String Info;
                Info = authorizeEmployee(loginManager.getEmployeeList());
                if (Info == null)
                    InvalidCrdLbl.setVisible(true);
                if (Info != null) {
                    InvalidCrdLbl.setVisible(false);
                    loginManager.authenticatedEmployee(Info);
                }
            }
        });
    }

    /**
     * Check authorization credentials.
     *
     * If accepted, return a sessionID for the authorized session
     * otherwise, return null.
     */

    private String authorizeEmployee(ArrayList<Employee> professorList) {
        for (Employee professor : professorList) {
            if (professor.getId().equals(user.getText()) && professor.getPass().equals(password.getText()))
                return (professor.getName() + "@" + professor.getId());
        }
        return
                null;
    }

    private String authorizeAccountant() {
        if (user.getText().equals(accountant.split("@")[1]) && password.getText().equals(accountant.split("@")[2]))
            return accountant;
        else
            return null;
    }
}