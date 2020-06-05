package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.api.NonExistentEntityException;
import main.java.com.advprogram.accountingApp.core.NonExistentCustomerException;
import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;

import javax.swing.*;
import java.sql.Connection;
import java.util.Optional;
import java.util.logging.Logger;

/** Controls the professor's page */
public class EmployeeController {
    @FXML private JFXButton btnProfilePage, btnClassPage, btnPresentPage, btnLogout, btnExit, btnConfirm;
    @FXML private Label lblName, lblAccNo, lblCourseName;
    @FXML private AnchorPane profilePage, classPage, presentPage;

    public EmployeeController(Optional<Connection> connection) {
        this.connection = connection;
    }

    public void initialize() { }

    private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());
    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();

    private final Optional<Connection> connection;

    public void initSessionID(final LoginManager loginManager, Integer sessionID) {
        String userId = sessionID.toString();
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
            if (input == 0) stage.close();
        });
//        End of side menu handlers

//        End
    }

    private Employee getEmployee(int id) throws NonExistentEntityException {
        Optional<Employee> customer = DAO.get(id);
        return customer.orElseThrow(NonExistentCustomerException::new);
    }

    private void updatePass(Employee employee) {
        DAO.updatePass(employee);
    }

    private String purple() {
        return "-fx-background-color: #673ab7; -fx-background-radius: 70";
    }
    private String magenta() {
        return "-fx-background-color: #97117A; -fx-background-radius: 70";
    }
}