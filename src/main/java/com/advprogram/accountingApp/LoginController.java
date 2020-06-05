package main.java.com.advprogram.accountingApp;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.java.com.advprogram.accountingApp.api.Accountant;
import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.api.NonExistentEntityException;
import main.java.com.advprogram.accountingApp.api.User;
import main.java.com.advprogram.accountingApp.core.NonExistentCustomerException;
import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Accountant accountant = new Accountant(1, "Ali", "Shams","1");


    private static final Logger LOGGER = Logger.getLogger(AccountingApplication.class.getName());
    private static final Dao<Employee, Integer> CUSTOMER_DAO = new PostgreSqlDao();

    public void initialize() { }
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
                Accountant Info;
                Info = authorizeAccountant();
                if (Info == null)
                    InvalidCrdLbl.setVisible(true);
                if (Info != null) {
                    InvalidCrdLbl.setVisible(false);
                    loginManager.authenticatedAccountant(Info);
                }
            }

            else if (type.getValue() == 1)
            {
                Integer Info;
                Info = authorizeEmployee();
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

    private Integer authorizeEmployee() {
        try {
            Employee customer = getCustomer(Integer.parseInt(user.getText()));
            boolean correct = checkPass(password.getText(), customer.getPass());
            if (correct) {
                InvalidCrdLbl.setVisible(true);
                return (customer.getId());
            }
        } catch (NonExistentEntityException ex) {
            InvalidCrdLbl.setVisible(true);
            return null;
        }
        return null;
    }

    private Accountant authorizeAccountant() {
        if (user.getText().equals(String.valueOf(accountant.getId())) && password.getText().equals(accountant.getPass()))
            return accountant;
        else
            return null;
    }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    private boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    private Employee getCustomer(int id) throws NonExistentEntityException {
        Optional<Employee> customer = CUSTOMER_DAO.get(id);
        return customer.orElseThrow(NonExistentCustomerException::new);
    }
}