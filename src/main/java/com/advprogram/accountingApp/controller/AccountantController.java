package main.java.com.advprogram.accountingApp.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.advprogram.accountingApp.core.*;
import main.java.com.advprogram.accountingApp.dao.*;
import main.java.com.advprogram.accountingApp.model.Accountant;
import main.java.com.advprogram.accountingApp.model.Employee;
import main.java.com.advprogram.accountingApp.model.GData;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/** Controls the accountant screen */
public class AccountantController {
    @FXML
    private JFXButton btnProfilePage, vboxBtnAddEmp, btnEditEmployee,
            btnSubmitChanges, btnPrsnlInfo, btnLogout, btnExit, btnSearchID, btnNextMonth, btnNextMonthPage, btnAddEmp;
    @FXML
    private Label lblProfName, lblAccNo, lblNameDisplay,
            lblDate,
            emptyfeildFN,
            emptyfeildLN,
            emptyfeildNI,
            emptyfeildNOO,
            emptyfeildWE,
            emptyfeildT,
            emptyfeildBS,
            empexstswarning;
    @FXML
    private AnchorPane profilePage, personnelTablePage, empAddPage, employeeEditPage, datePage;
    @FXML
    private TableView<EmployeeT> personnelTable;
    @FXML
    private TableColumn clmnFirstName, clmnLastName, clmnID, clmnTitle;
    @FXML
    private TextField txtFNameAdd, txtLNameAdd, txtIDAdd, txtOffspringAdd, txtTitleAdd, txtBaseSalaryAdd, txtWorkExpAdd,
            txtIdSearch, txtTitleEdit, txtSalaryEdit, txtOffspringsEdit;

    final ObservableList<EmployeeT> dataET =
            FXCollections.observableArrayList();

    private static final EmployeeDao<Employee> EMPLOYEE_DAO = new EmployeeDaoImp();
    private static final GDataDao<GData> GDATA_DAO = new GDataDaoImp();

    private final Calculator calculator = new Calculator();

    public void initialize() { }

    public void initSessionID(final LoginManager loginManager, Accountant accountant) {
        setProfileInfo(accountant);
        lblDate.setText(getDate().toString());

        btnSearchID.setOnAction(event -> {
            Employee employee = new Employee();
            try {
                employee = getEmployee(Integer.parseInt(txtIdSearch.getText()));
                lblNameDisplay.setText(employee.getFirstName() + " " + employee.getLastName());
                txtTitleEdit.setText(employee.getTitle());
                txtSalaryEdit.setText(String.valueOf(employee.getBaseSalary()));
                txtOffspringsEdit.setText(String.valueOf(employee.getOffsprings()));
            } catch (NonExistentEntityException e) {
                //TODO
            }
            Employee finalEmployee = employee;
            btnSubmitChanges.setOnAction(eventSec -> {
                if (txtTitleEdit.getText() == null || txtSalaryEdit.getText() == null ||
                        txtOffspringsEdit == null) {
                    //TODO
                    return;
                }
                finalEmployee.setTitle(txtTitleEdit.getText());
                finalEmployee.setOffsprings(Integer.parseInt(txtOffspringsEdit.getText()));
                finalEmployee.setBaseSalary(Integer.parseInt(txtSalaryEdit.getText()));
                updateEmployee(finalEmployee);
            });
        });


        btnAddEmp.setOnAction(event -> {
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(txtIDAdd.getText()));
            employee.setFirstName(txtFNameAdd.getText());
            employee.setLastName(txtLNameAdd.getText());
            employee.setOffsprings(Integer.parseInt(txtOffspringAdd.getText()));
            employee.setTitle(txtTitleAdd.getText());
            employee.setBaseSalary(Integer.parseInt(txtBaseSalaryAdd.getText()));
            employee.setWorkExp(Integer.parseInt(txtWorkExpAdd.getText()));
            PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                    .useDigits(true)
                    .useLower(true)
                    .useUpper(true)
                    .build();
            String password = passwordGenerator.generate(8);
            employee.setPass(hashPassword(password));
            addEmployee(employee);
        });

        btnNextMonth.setOnAction(event -> {
            int input = JOptionPane.showConfirmDialog(null,
                    "Are you sure? \n You cannot revert this action.", "Next Month",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) {
                nextMonth();
                lblDate.setText(getDate().toString());
            }
        });

        /* Event handlers for side menu items */
        btnProfilePage.setOnAction(event -> {
            btnProfilePage.setStyle(magenta());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());
            btnNextMonthPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(true);
            empAddPage.setVisible(false);
            employeeEditPage.setVisible(false);
            personnelTablePage.setVisible(false);
            datePage.setVisible(false);
        });

        btnPrsnlInfo.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(magenta());
            btnNextMonthPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            empAddPage.setVisible(false);
            employeeEditPage.setVisible(false);
            personnelTablePage.setVisible(true);
            datePage.setVisible(false);
        });

        vboxBtnAddEmp.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(magenta());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());
            btnNextMonthPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            empAddPage.setVisible(true);
            employeeEditPage.setVisible(false);
            personnelTablePage.setVisible(false);
            datePage.setVisible(false);
        });

        btnEditEmployee.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(magenta());
            btnPrsnlInfo.setStyle(purple());
            btnNextMonthPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            empAddPage.setVisible(false);
            employeeEditPage.setVisible(true);
            personnelTablePage.setVisible(false);
            datePage.setVisible(false);
        });

        btnNextMonthPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            vboxBtnAddEmp.setStyle(purple());
            btnEditEmployee.setStyle(purple());
            btnPrsnlInfo.setStyle(purple());
            btnNextMonthPage.setStyle(magenta());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            empAddPage.setVisible(false);
            employeeEditPage.setVisible(false);
            personnelTablePage.setVisible(false);
            datePage.setVisible(true);
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

        getAllEmployees().forEach(
                i -> dataET.add(new EmployeeT(i.getFirstName(), i.getLastName(), i.getId(),i.getTitle())));
        clmnFirstName.setCellValueFactory(
                new PropertyValueFactory<>("fName"));
        clmnLastName.setCellValueFactory(
                new PropertyValueFactory<>("lName"));
        clmnID.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        clmnTitle.setCellValueFactory(
                new PropertyValueFactory<>("title"));
        personnelTable.setItems(dataET);
//        End
    }
    private void setProfileInfo(Accountant accountant) {
        lblProfName.setText(accountant.getFirstName()+" "+accountant.getLastName());
        lblAccNo.setText(String.valueOf(accountant.getId()));
    }

    private Employee getEmployee(int id) throws NonExistentEntityException {
        Optional<Employee> employee = EMPLOYEE_DAO.get(id);
        return employee.orElseThrow(NonExistentEmployeeException::new);
    }
    private void updateEmployee(Employee employee) {
        EMPLOYEE_DAO.update(employee);
    }

    public static Collection<Employee> getAllEmployees() {
        return EMPLOYEE_DAO.getAll();
    }
    private void addEmployee(Employee employee) {
        EMPLOYEE_DAO.save(employee);
    }

    private void nextMonth() {
        calculator.nextMonth();
    }
    private LocalDate getDate() {
        Optional<GData> gData = getGData();
        return gData.get().getDate().toLocalDate();
    }

    private Optional<GData> getGData() { return GDATA_DAO.get(0); }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static class EmployeeT {
        private final SimpleStringProperty fName;
        private final SimpleStringProperty lName;
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty title;
        private EmployeeT(String fNameT, String lNameT, int idT, String titleT) {
            this.fName = new SimpleStringProperty(fNameT);
            this.lName = new SimpleStringProperty(lNameT);
            this.id = new SimpleIntegerProperty(idT);
            this.title = new SimpleStringProperty(titleT);
        }
        public Integer getId() {
            return id.get();
        }
        public void setId(Integer idT) {
            id.set(idT);
        }
        public String getFName() { return fName.get(); }
        public void setFName(String fNameT) {
            fName.set(fNameT);
        }
        public String getLName() {
            return lName.get();
        }
        public void setLName(String lNameT) {
            lName.set(lNameT);
        }
        public String getTitle() {
            return title.get();
        }
        public void setTitle(String titleT) {
            title.set(titleT);
        }
    }

    private String purple() {
        return "-fx-background-color: #673ab7; -fx-background-radius: 70";
    }
    private String magenta() {
        return "-fx-background-color: #97117A; -fx-background-radius: 70";
    }
}