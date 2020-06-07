package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.advprogram.accountingApp.api.Accountant;
import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;

import javax.swing.*;
import java.util.Collection;

/** Controls the accountant screen */
public class AccountantController {
    @FXML
    private JFXButton btnProfilePage, vboxBtnAddEmp, btnEditEmployee,
            btnSubmitChanges, btnPrsnlInfo, btnLogout, btnExit, btnSearchID;
    @FXML
    private Label lblName, lblAccNo,
    emptyfeildFN,
            emptyfeildLN,
    emptyfeildNI,
            emptyfeildNOO,
    emptyfeildWE,
            emptyfeildT,
    emptyfeildBS,
            empexstswarning  ;
    @FXML
    private AnchorPane profilePage, personnelTablePage, empAddPage, employeeEditPage, confirmPage;
    @FXML
    private TableView<EmployeeT> personnelTable;
    @FXML
    private TableColumn clmnFirstName, clmnLastName, clmnID;
    @FXML
    private TextField txtFNameAdd, txtLNameAdd, txtIDAdd, txtOffspringAdd, txtTitleAdd, txtBaseSalaryAdd, txtWorkExpAdd;
    @FXML
    private Button btnAddEmp;
    final ObservableList<EmployeeT> dataET =
            FXCollections.observableArrayList();
    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();

    public void initialize() { }

    public void initSessionID(final LoginManager loginManager, Accountant accountant) {

        /* Event handlers for side menu items */
        btnProfilePage.setOnAction(event -> {
            btnProfilePage.setStyle(magenta());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(true);
            empAddPage.setVisible(false);
            employeeEditPage.setVisible(false);
            personnelTablePage.setVisible(false);

        });

        btnPrsnlInfo.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(magenta());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            empAddPage.setVisible(false);
            employeeEditPage.setVisible(false);
            personnelTablePage.setVisible(true);
        });

        vboxBtnAddEmp.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(magenta());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            empAddPage.setVisible(true);
            employeeEditPage.setVisible(false);
            personnelTablePage.setVisible(false);

        });

        btnEditEmployee.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(magenta());
            btnPrsnlInfo.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            empAddPage.setVisible(false);
            employeeEditPage.setVisible(true);
            personnelTablePage.setVisible(false);

        });


        btnLogout.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());

            btnExit.setStyle(purple());
            btnLogout.setStyle(magenta());
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) loginManager.logout();
        });

        btnExit.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());

            btnLogout.setStyle(purple());
            btnExit.setStyle(magenta());
            Stage stage = (Stage) btnExit.getScene().getWindow();
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) stage.close();;
        });
////        End of side menu handlers

//        End
    }
    public static class EmployeeT {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;
        private EmployeeT(int idT, String fNameT, String lNameT, String titleT) {
            this.name = new SimpleStringProperty(fNameT);
            this.id = new SimpleIntegerProperty(idT);
        }
        public Integer getId() {
            return id.get();
        }
        public void setId(Integer idT) {
            id.set(idT);
        }
        public String getName() {
            return name.get();
        }
        public void setName(String fNameT) {
            name.set(fNameT);
        }

    }

    public static Collection<Employee> getAllEmployees() {
        return DAO.getAll();
    }

    private String purple() {
        return "-fx-background-color: #673ab7; -fx-background-radius: 70";
    }
    private String magenta() {
        return "-fx-background-color: #97117A; -fx-background-radius: 70";
    }
}