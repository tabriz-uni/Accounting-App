package main.java.com.advprogram.accountingApp.controller;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.java.com.advprogram.accountingApp.AccountingApplication;
import main.java.com.advprogram.accountingApp.core.LoginManager;
import main.java.com.advprogram.accountingApp.dao.UserDao;
import main.java.com.advprogram.accountingApp.dao.UserDaoImp;
import main.java.com.advprogram.accountingApp.model.Accountant;
import main.java.com.advprogram.accountingApp.model.Employee;
import main.java.com.advprogram.accountingApp.core.NonExistentEntityException;
import main.java.com.advprogram.accountingApp.core.NonExistentCustomerException;
import main.java.com.advprogram.accountingApp.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;
import java.util.logging.Logger;

/** Controls the login screen */
public class LoginController {
    @FXML private TextField userId;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Label showPasswordHover;
    @FXML private Button showPassword;
    @FXML private JFXSlider type;
    @FXML private Label InvalidCrdLbl;
    private String revealedPassword;
    private Accountant accountant = new Accountant(1, "Ali", "Shams","1");

    private static final Logger LOGGER = Logger.getLogger(AccountingApplication.class.getName());
    private static final UserDao<User> USER_DAO = new UserDaoImp();

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
                User Info;
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

    private User authorizeEmployee() {
        try {
            User user = getUser(Integer.parseInt(userId.getText()));
            boolean correct = checkPass(password.getText(), user.getPass());
            if (correct) {
                InvalidCrdLbl.setVisible(false);
                return user;
            }
        } catch (NonExistentEntityException ex) {
            InvalidCrdLbl.setVisible(true);
            return null;
        }
        return null;
    }

    private Accountant authorizeAccountant() {
        if (userId.getText().equals(String.valueOf(accountant.getId())) && password.getText().equals(accountant.getPass()))
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

    private User getUser(int id) throws NonExistentEntityException {
        Optional<User> user = USER_DAO.get(id);
        return user.orElseThrow(NonExistentCustomerException::new);
    }
}