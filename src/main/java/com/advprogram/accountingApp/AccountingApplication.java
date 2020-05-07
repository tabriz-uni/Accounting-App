//abc
package main.java.com.advprogram.accountingApp;

import main.java.com.advprogram.accountingApp.api.User;
import main.java.com.advprogram.accountingApp.api.NonExistentEntityException;
import main.java.com.advprogram.accountingApp.core.NonExistentCustomerException;
import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;

import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hiram K. <https://github.com/IdelsTak>
 */
public class AccountingApplication {

    private static final Logger LOGGER = Logger.getLogger(AccountingApplication.class.getName());
    private static final Dao<User, Integer> CUSTOMER_DAO = new PostgreSqlDao();

    public static void main(String[] args) {
        //Test whether an exception is thrown when
        //the database is queried for a non-existent customer
        //But, if the customer does exist, the details will be printed
        //on the console
        try {
            User customer = getCustomer(1);
        } catch (NonExistentEntityException ex) {
            LOGGER.log(Level.WARNING, ex.getMessage());
        }
        //Test whether a customer can be added to the database
        User firstCustomer = new User("Manuel", "Kelley", "ManuelMKelley@jourrapide.com");
        User secondCustomer = new User("Joshua", "Daulton", "JoshuaMDaulton@teleworm.us");
        User thirdCustomer = new User("April", "Ellis", "AprilMellis@jourrapide.com");
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

    public static User getCustomer(int id) throws NonExistentEntityException {
        Optional<User> customer = CUSTOMER_DAO.get(id);
        return customer.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<User> getAllCustomers() {
        return CUSTOMER_DAO.getAll();
    }

    public static void updateCustomer(User customer) {
        CUSTOMER_DAO.update(customer);
    }

    public static Optional<Integer> addCustomer(User customer) {
        return CUSTOMER_DAO.save(customer);
    }

    public static void deleteCustomer(User customer) {
        CUSTOMER_DAO.delete(customer);
    }
}