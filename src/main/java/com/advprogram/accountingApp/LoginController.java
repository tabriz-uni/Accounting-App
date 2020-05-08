package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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
    private final String manager = "Ali Shams@1@1";

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
                Info = authorizeManager();
                if (Info == null)
                    InvalidCrdLbl.setVisible(true);
                if (Info != null) {
                    InvalidCrdLbl.setVisible(false);
                    loginManager.authenticatedManager(Info);
                }
            }

            else if (type.getValue() == 1)
            {
                String Info;
                Info = authorizeStudent(loginManager.getStudentList());
                if (Info == null)
                    InvalidCrdLbl.setVisible(true);
                if (Info != null) {
                    InvalidCrdLbl.setVisible(false);
                    loginManager.authenticatedStudent(Info);
                }
            }

            else if (type.getValue() == 2)
            {
                String Info;
                Info = authorizeProfessor(loginManager.getProfessorList());
                if (Info == null)
                    InvalidCrdLbl.setVisible(true);
                if (Info != null) {
                    InvalidCrdLbl.setVisible(false);
                    loginManager.authenticatedProfessor(Info);
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

    private String authorizeProfessor(ArrayList<Professor> professorList) {
        for (Professor professor : professorList) {
            if (professor.getId().equals(user.getText()) && professor.getPass().equals(password.getText()))
                return (professor.getName() + "@" + professor.getId());
        }
        return
                null;
    }

    private String authorizeManager() {
        if (user.getText().equals(manager.split("@")[1]) && password.getText().equals(manager.split("@")[2]))
            return manager;
        else
            return null;
    }

    private String authorizeStudent(ArrayList<Student> studentList) {
        for (Student student : studentList) {
            if (student.getId().equals(user.getText()) && student.getPass().equals(password.getText()))
                return (student.getName() + "@" + student.getId());
        }
        return
                null;
    }
}