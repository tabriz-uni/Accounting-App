package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.advprogram.accountingApp.api.Accountant;

import javax.swing.*;

/** Controls the accountant screen */
public class AccountantController {
    @FXML
    private JFXButton btnProfilePage, VboxBtnAddEmp, btnEditEmployee,
            btnSubmitChanges, btnPrsnlInfo, btnLogout, btnExit, btnSearchID;
    @FXML
    private Label lblName, lblAccNo;
    @FXML
    private AnchorPane profilePage, PersonnelTablePage, EmpAddPage, EmployeeEditPage, confirmPage;
    @FXML
    private TableView PersonnelTable;
    @FXML
    private TableColumn clmnFirstName, clmnLastName, clmnID;
    @FXML
    private TextField txtFNameAdd, txtLNameAdd, txtIDAdd, txtOffspringAdd, txtTitleAdd, txtBaseSalaryAdd;
    @FXML
    private Button btnAddEmp;


    public void initialize() { }

    public void initSessionID(final LoginManager loginManager, Accountant accountant) {
        lblName.setText(accountant.getFirstName()+" "+accountant.getLastName());
        lblAccNo.setText(String.valueOf(accountant.getId()));

        /* Event handlers for side menu items */
        btnProfilePage.setOnAction(event -> {
            btnProfilePage.setStyle(magenta());
            VboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(true);
            EmpAddPage.setVisible(false);
            EmployeeEditPage.setVisible(false);
            PersonnelTablePage.setVisible(false);
            confirmPage.setVisible(false);
        });

        btnPrsnlInfo.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            VboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(magenta());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            EmpAddPage.setVisible(false);
            EmployeeEditPage.setVisible(false);
            PersonnelTablePage.setVisible(true);
            confirmPage.setVisible(false);
        });

        VboxBtnAddEmp.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            VboxBtnAddEmp.setStyle(magenta());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            EmpAddPage.setVisible(true);
            EmployeeEditPage.setVisible(false);
            PersonnelTablePage.setVisible(false);
            confirmPage.setVisible(false);
        });

        btnEditEmployee.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            VboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(magenta());
            btnPrsnlInfo.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            EmpAddPage.setVisible(false);
            EmployeeEditPage.setVisible(true);
            PersonnelTablePage.setVisible(false);
            confirmPage.setVisible(false);
        });


        btnLogout.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            VboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());

            btnExit.setStyle(purple());
            btnLogout.setStyle(magenta());
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) loginManager.logout();
        });

//        btnExit.setOnAction(event -> {
//            btnProfilePage.setStyle(purple());
//            VboxBtnAddEmp.setStyle(purple());
//            btnEditEmployee.setStyle(purple());
//            btnPrsnlInfo.setStyle(purple());
//
//            btnLogout.setStyle(purple());
//            btnExit.setStyle(magenta());
//            Stage stage = (Stage) btnExit.getScene().getWindow();
//            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
//                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//            if (input == 0) stage.close();;
//        });
////        End of side menu handlers

//        End
    }

    private String purple() {
        return "-fx-background-color: #673ab7; -fx-background-radius: 70";
    }
    private String magenta() {
        return "-fx-background-color: #97117A; -fx-background-radius: 70";
    }
}