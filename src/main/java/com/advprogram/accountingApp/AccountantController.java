package main.java.com.advprogram.accountingApp;

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
import main.java.com.advprogram.accountingApp.api.Accountant;
import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;

import javax.swing.*;
import java.util.Collection;

/** Controls the accountant screen */
public class AccountantController {
    @FXML private JFXButton btnProfilePage, btnProfessorsPage, btnStudentsPage,
            btnCoursesPage, btnConfirmPage, btnLogout, btnExit, btnConfirm;
    @FXML private Label lblName, lblAccNo;
    @FXML private AnchorPane profilePage, professorsPage, studentsPage, coursesPage, confirmPage;
    @FXML private TableView<EmployeeT> studentsTable;
    @FXML private TableColumn clmnIdET, clmnNameET, clmnFNameT, clmnTitleT;
    final ObservableList<EmployeeT> dataET =
            FXCollections.observableArrayList();
    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();

    public void initialize() { }

    public void initSessionID(final LoginManager loginManager, Accountant accountant) {
        lblName.setText(accountant.getFirstName()+" "+accountant.getLastName());
        lblAccNo.setText(String.valueOf(accountant.getId()));

        /* Event handlers for side menu items */
        btnProfilePage.setOnAction(event -> {
            btnProfilePage.setStyle(magenta());
            btnProfessorsPage.setStyle(purple());
            btnStudentsPage.setStyle(purple());
            btnCoursesPage.setStyle(purple());
            btnConfirmPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(true);
            professorsPage.setVisible(false);
            studentsPage.setVisible(false);
            coursesPage.setVisible(false);
            confirmPage.setVisible(false);
        });

        btnProfessorsPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnProfessorsPage.setStyle(magenta());
            btnStudentsPage.setStyle(purple());
            btnCoursesPage.setStyle(purple());
            btnConfirmPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            professorsPage.setVisible(true);
            studentsPage.setVisible(false);
            coursesPage.setVisible(false);
            confirmPage.setVisible(false);
        });

        btnStudentsPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnProfessorsPage.setStyle(purple());
            btnStudentsPage.setStyle(magenta());
            btnCoursesPage.setStyle(purple());
            btnConfirmPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            professorsPage.setVisible(false);
            studentsPage.setVisible(true);
            coursesPage.setVisible(false);
            confirmPage.setVisible(false);
        });

        btnCoursesPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnProfessorsPage.setStyle(purple());
            btnStudentsPage.setStyle(purple());
            btnCoursesPage.setStyle(magenta());
            btnConfirmPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            professorsPage.setVisible(false);
            studentsPage.setVisible(false);
            coursesPage.setVisible(true);
            confirmPage.setVisible(false);
        });
        btnConfirmPage.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnProfessorsPage.setStyle(purple());
            btnStudentsPage.setStyle(purple());
            btnCoursesPage.setStyle(purple());
            btnConfirmPage.setStyle(magenta());
            btnExit.setStyle(purple());
            btnLogout.setStyle(purple());
            profilePage.setVisible(false);
            professorsPage.setVisible(false);
            studentsPage.setVisible(false);
            coursesPage.setVisible(false);
            confirmPage.setVisible(true);
        });
        btnLogout.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnProfessorsPage.setStyle(purple());
            btnStudentsPage.setStyle(purple());
            btnCoursesPage.setStyle(purple());
            btnConfirmPage.setStyle(purple());
            btnExit.setStyle(purple());
            btnLogout.setStyle(magenta());
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) loginManager.logout();
        });

        btnExit.setOnAction(event -> {
            btnProfilePage.setStyle(purple());
            btnProfessorsPage.setStyle(purple());
            btnStudentsPage.setStyle(purple());
            btnCoursesPage.setStyle(purple());
            btnConfirmPage.setStyle(purple());
            btnLogout.setStyle(purple());
            btnExit.setStyle(magenta());
            Stage stage = (Stage) btnExit.getScene().getWindow();
            int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Log Out",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (input == 0) stage.close();;
        });
//        End of side menu handlers

        clmnNameET.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        clmnIdET.setCellValueFactory(
                new PropertyValueFactory<>("id"));
        studentsTable.setItems(dataET);

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