package main.java.com.advprogram.accountingApp;

import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.api.PasswordGenerator;
import main.java.com.advprogram.accountingApp.core.JdbcConnection;
import main.java.com.advprogram.accountingApp.core.PostgreSqlDao;
import main.java.com.advprogram.accountingApp.spi.Dao;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.EnumMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {
    private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());
    private static final Dao<Employee, Integer> DAO = new PostgreSqlDao();

    private final Optional<Connection> connection;

    public Calculator() { this.connection = JdbcConnection.getConnection(); }

    public void nextYear() {
        increGlobalData();
        increExp();
        increBaseSalary();
    }
    private void nextMonth() {
        DAO.nextMonth();
        DAO.getGData();
    }
    private void increExp() {
        DAO.increExp();
    }
    private void increBaseSalary() {
        DAO.increBaseSalary();
    }
    private void increGlobalData() {
        DAO.increGlobalData();
    }
    private String GeneratePass () {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        return passwordGenerator.generate(8);
    }
    private String HashPass (String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
}

