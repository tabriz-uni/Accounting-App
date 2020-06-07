package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.advprogram.accountingApp.api.Calculator;
import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.api.GData;
import main.java.com.advprogram.accountingApp.api.NonExistentEntityException;
import main.java.com.advprogram.accountingApp.core.NonExistentCustomerException;
import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.sql.Connection;
import java.util.Optional;
import java.util.logging.Logger;

/** Controls the professor's page */
public class EmployeeController {
    @FXML
    private JFXButton btnProfilePage, btnSalaryPage, btnLogout, btnExit, btnConfirmChange,btnPassChange;
    @FXML
    private Label lblFirstNameDisplay, lblLastNameDisplay, lblTitleDisplay, lblIDDisplay,
            lblRentrebate,
    lblmonetaryrebate,
            lblChildbenefit,
    lblTax, lblInsurance,
            lblRegularpay,
    lblBasesalary,
            lblSalarySum;
    @FXML
    private AnchorPane profilePage, salaryPage,passChangePage;
    @FXML
    private PasswordField txtCurruntPass, txtNewPass , txtConfirmNewPass;

    public void initialize() { }

    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();

    public void initSessionID(final LoginManager loginManager, Employee employee) {
        setProfileInfo(employee);
        setSalaryInfo(employee);
        btnConfirmChange.setOnAction(event -> {
            if (!txtNewPass.getText().equals(txtConfirmNewPass.getText())) {
                //TODO
                //new pass n match
                return;
            }
            if (!checkPass(txtCurruntPass.getText(), employee.getPass())) {
                //TODO
                return;
            }
            if (txtCurruntPass.getText().equals(txtNewPass.getText())) {
                //TODO
                return;
            }
            updatePass(employee, txtNewPass.getText());

        });
        /* Event handlers for the side menu items */
        btnProfilePage.setOnAction(event -> {
            btnProfilePage.setStyle(magenta());

            btnSalaryPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(true);
            salaryPage.setVisible(false);
            passChangePage.setVisible(false);

        });

        btnSalaryPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnSalaryPage.setStyle(magenta());

            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            salaryPage.setVisible(true);
            passChangePage.setVisible(false);

        });
        btnPassChange.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnSalaryPage.setStyle(purple());

            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            salaryPage.setVisible(false);
            passChangePage.setVisible(true);

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
    private void setSalaryInfo(Employee employee) {
        Calculator calc = new Calculator();
        GData gData = getGData();
        lblRentrebate.setText(String.valueOf(gData.getBonMaskan()));
        lblmonetaryrebate.setText(String.valueOf(gData.getBonNagdi()));
        lblChildbenefit.setText(String.valueOf(calc.calcHagOlad(employee)));
        lblBasesalary.setText(String.valueOf(employee.getBaseSalary()));
        lblSalarySum.setText(String.valueOf(calc.calcPostTaxSal(employee)));
    }

    private Employee getEmployee(int id) throws NonExistentEntityException {
        Optional<Employee> employee = DAO.get(id);
        return employee.orElseThrow(NonExistentCustomerException::new);
    }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    private boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    private void updatePass(Employee employee, String plainPassword) {
        employee.setPass(hashPassword(plainPassword));
        DAO.updatePass(employee);
    }
    private GData getGData() { return DAO.getGData(); }

    private String purple() {
        return "-fx-background-color: #673ab7; -fx-background-radius: 70";
    }
    private String magenta() {
        return "-fx-background-color: #97117A; -fx-background-radius: 70";
    }
}