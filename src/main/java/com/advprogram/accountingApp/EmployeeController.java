package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXButton;
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
    @FXML
    private JFXButton btnProfilePage, btnSalaryPage, btnLogout, btnExit, btnConfirmChange,btnPassChange;
    @FXML
    private Label lblFirstNameDisplay, lblLastNameDisplay, lblTitleDisplay, lblIDDisplay;
    @FXML
    private AnchorPane profilePage, salaryPage,passwordChangePage;
    @FXML
    private PasswordField txtCurruntPass, txtNewPass , txtConfirmNewPass;


    public EmployeeController(Optional<Connection> connection) {
        this.connection = connection;
    }

    public void initialize() { }

    private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());
    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();

    private final Optional<Connection> connection;

    public void initSessionID(final LoginManager loginManager, Integer sessionID) throws NonExistentEntityException {
        setProfileInfo(getEmployee(sessionID));

        /* Event handlers for the side menu items */
        btnProfilePage.setOnAction(event -> {
            btnProfilePage.setStyle(magenta());

            btnSalaryPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(true);
            salaryPage.setVisible(false);
            passwordChangePage.setVisible(false);


        });

        btnSalaryPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnSalaryPage.setStyle(magenta());

            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            salaryPage.setVisible(true);
            passwordChangePage.setVisible(false);

        });
        btnPassChange.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnSalaryPage.setStyle(purple());

            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            salaryPage.setVisible(false);
            passwordChangePage.setVisible(true);

        });


        btnLogout.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnSalaryPage.setStyle(purple());

            btnExit.setStyle(purple());
            btnLogout.setStyle(magenta());
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) loginManager.logout();
        });

        btnExit.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnSalaryPage.setStyle(purple());

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
    private void setProfileInfo(Employee employee) {
        lblFirstNameDisplay.setText(employee.getFirstName());
        lblLastNameDisplay.setText(employee.getLastName());
        lblIDDisplay.setText(String.valueOf(employee.getId()));
        lblTitleDisplay.setText(employee.getTitle());
    }

    private Employee getEmployee(int id) throws NonExistentEntityException {
        Optional<Employee> employee = DAO.get(id);
        return employee.orElseThrow(NonExistentCustomerException::new);
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