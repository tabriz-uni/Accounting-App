package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;

/** Controls the professor's page */
public class EmployeeController {
    @FXML private JFXButton btnProfilePage, btnClassPage, btnPresentPage, btnLogout, btnExit, btnConfirm;
    @FXML private Label lblName, lblAccNo, lblCourseName;
    @FXML private AnchorPane profilePage, classPage, presentPage;

    public void initialize() { }

    public void initSessionID(final LoginManager loginManager, String sessionID) {
        lblName.setText(sessionID.split("@")[0]);
        String userId = sessionID.split("@")[1];
        lblAccNo.setText(userId);
        /* Event handlers for the side menu items */
        btnProfilePage.setOnAction(event -> {
            btnProfilePage.setStyle(magenta());
            btnClassPage.setStyle(purple());
            btnPresentPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(true);
            classPage.setVisible(false);
            presentPage.setVisible(false);
        });

        btnClassPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnClassPage.setStyle(magenta());
            btnPresentPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            classPage.setVisible(true);
            presentPage.setVisible(false);
        });

        btnPresentPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnClassPage.setStyle(purple());
            btnPresentPage.setStyle(magenta());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            classPage.setVisible(false);
            presentPage.setVisible(true);
        });
        btnLogout.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnClassPage.setStyle(purple());
            btnPresentPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(magenta());
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) loginManager.logout();
        });

        btnExit.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnClassPage.setStyle(purple());
            btnPresentPage.setStyle(purple());
            btnLogout.setStyle(purple());
            btnExit.setStyle(magenta());
            Stage stage = (Stage) btnExit.getScene().getWindow();
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) stage.close();;
        });
//        End of side menu handlers

//        End
    }

    private String purple() {
        return "-fx-background-color: #673ab7; -fx-background-radius: 70";
    }
    private String magenta() {
        return "-fx-background-color: #97117A; -fx-background-radius: 70";
    }
}