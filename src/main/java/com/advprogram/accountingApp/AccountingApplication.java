package main.java.com.advprogram.accountingApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.com.advprogram.accountingApp.core.LoginManager;

public class AccountingApplication extends Application {
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new AnchorPane());
        LoginManager loginManager = new LoginManager(scene);
        loginManager.showLoginScreen();
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) { launch(args); }

    /*
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    private void checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword))
            System.out.println("The password matches.");
        else
            System.out.println("The password does not match.");
    }
     */

    /*
    private static final Logger LOGGER = Logger.getLogger(AccountingApplication.class.getName());

    public static void main(String[] args) {
        //Test whether an exception is thrown when
        //the database is queried for a non-existent customer
        //But, if the customer does exist, the details will be printed
        //on the console
        try {
            Employee customer = getCustomer(1);
        } catch (NonExistentEntityException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
        //Test whether a customer can be added to the database
        Employee firstCustomer = new Employee("Manuel", "Kelley", "ManuelMKelley@jourrapide.com");
        Employee secondCustomer = new Employee("Joshua", "Daulton"K, "JoshuaMDaulton@teleworm.us");
        Employee thirdCustomer = new Employee("April", "Ellis", "AprilMellis@jourrapide.com");
        addCustomer(firstCustomer).ifPresent(firstCustomer::setId);
        addCustomer(secondCustomer).ifPresent(secondCustomer::setId);
        addCustomer(thirdCustomer).ifPresent(thirdCustomer::setId);
        //Test whether the new customer's details can be edited
        firstCustomer.setFirstName("Franklin");
        firstCustomer.setLastName("Hudson");
        firstCustomer.setEmail("FranklinLHudson@jourrapide.com");
        updateCustomer(firstCustomer);
        //Test whether all customers can be read from database
        getAllCustomers().forEach(System.out::println);
        //Test whether a customer can be deleted
        deleteCustomer(secondCustomer);
    }

    public static Employee getCustomer(int id) throws NonExistentEntityException {
        Optional<Employee> customer = CUSTOMER_DAO.get(id);
        return customer.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<Employee> getAllCustomers() {
        return CUSTOMER_DAO.getAll();
    }

    public static void updateCustomer(Employee customer) {
        CUSTOMER_DAO.update(customer);
    }

    public static Optional<Integer> addCustomer(Employee customer) {
        return CUSTOMER_DAO.save(customer);
    }

    public static void deleteCustomer(Employee customer) {
        CUSTOMER_DAO.delete(customer);
    }

     */
}