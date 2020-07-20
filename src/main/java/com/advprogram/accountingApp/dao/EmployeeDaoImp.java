package main.java.com.advprogram.accountingApp.dao;

import main.java.com.advprogram.accountingApp.core.JdbcConnection;
import main.java.com.advprogram.accountingApp.model.Employee;
import main.java.com.advprogram.accountingApp.model.GData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDaoImp implements EmployeeDao<Employee> {

    private static final Logger LOGGER = Logger.getLogger(EmployeeDaoImp.class.getName());
    private final Optional<Connection> connection;

    public EmployeeDaoImp() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Employee> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Employee> employee = Optional.empty();
            String sql = "SELECT * FROM employee WHERE user_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String pass = resultSet.getString("pass");
                    int offsprings = resultSet.getInt("offsprings");
                    String title = resultSet.getString("title");
                    int workExp = resultSet.getInt("work_exp");
                    int workExpHere = resultSet.getInt("work_exp_here");
                    int baseSalary = resultSet.getInt("base_salary");
                    int workedDays = resultSet.getInt("days_worked");
                    boolean workedToday = resultSet.getBoolean("worked_today");

                    employee = Optional.of(new Employee(id, firstName, lastName, pass,
                            offsprings, title, workExp, workExpHere, baseSalary, workedDays, workedToday));

                    LOGGER.log(Level.INFO, "Found {0} in database", employee.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return employee;
        });
    }

    @Override
    public Collection<Employee> getAll() {
        Collection<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("user_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String pass = resultSet.getString("pass");
                    int offsprings = resultSet.getInt("offsprings");
                    String title = resultSet.getString("title");
                    int workExp = resultSet.getInt("work_exp");
                    int workExpHere = resultSet.getInt("work_exp_here");
                    int baseSalary = resultSet.getInt("base_salary");
                    int workedDays = resultSet.getInt("days_worked");
                    boolean workedToday = resultSet.getBoolean("worked_today");

                    Employee employee = new Employee(id, firstName, lastName, pass,
                            offsprings, title, workExp, workExpHere, baseSalary, workedDays, workedToday);

                    employees.add(employee);

                    LOGGER.log(Level.INFO, "Found {0} in database", employee);
                }

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

        return employees;
    }

    @Override
    public void save(Employee employee) {
        String message = "The employee to be added should not be null";
        Employee nonNullEmployee = Objects.requireNonNull(employee, message);
        String sql = "INSERT INTO "
                + "employee(user_id, first_name, last_name, pass, " +
                "offsprings, title, work_exp, work_exp_here, base_salary) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullEmployee.getId());
                statement.setString(2, nonNullEmployee.getFirstName());
                statement.setString(3, nonNullEmployee.getLastName());
                statement.setString(4, nonNullEmployee.getPass());
                statement.setInt(5, nonNullEmployee.getOffsprings());
                statement.setString(6, nonNullEmployee.getTitle());
                statement.setInt(7, nonNullEmployee.getWorkExp());
                statement.setInt(8, nonNullEmployee.getWorkExpHere());
                statement.setInt(9, nonNullEmployee.getBaseSalary());

                int numberOfInsertedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullEmployee, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void update(Employee employee) {
        String message = "The employee to be updated should not be null";
        Employee nonNullEmployee = Objects.requireNonNull(employee, message);
        String sql = "UPDATE employee "
                + "SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "pass = ?, "
                + "days_worked = ?, "
                + "worked_today = ?"
                + "WHERE "
                + "user_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullEmployee.getFirstName());
                statement.setString(2, nonNullEmployee.getLastName());
                statement.setString(3, nonNullEmployee.getPass());
                statement.setInt(4, nonNullEmployee.getWorkedDays());
                statement.setBoolean(5, nonNullEmployee.isWorkedToday());
                statement.setInt(6, nonNullEmployee.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the employee updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void delete(Employee employee) {
        String message = "The employee to be deleted should not be null";
        Employee nonNullEmployee = Objects.requireNonNull(employee, message);
        String sql = "DELETE FROM employee WHERE user_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullEmployee.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the employee deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void nextDay() {
        String sql = "UPDATE employee " +
                "SET " +
                "worked_today = false;";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement()){
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void nextMonth() {
        String sql = "UPDATE employee " +
                "SET " +
                "days_worked = 0;";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement()){
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void increEmployeeData(Optional<GData> ref) {
        String sql = "UPDATE employee " +
                "SET " +
                "work_exp = work_exp +1," +
                "work_exp_here = work_exp_here + 1;";

        String sqlH = "UPDATE employee " +
                "SET " +
                "base_salary = (base_salary * 1.15) +" + ref.get().getSabetHogug() + ref.get().getPayeSanavat() +
                " WHERE work_exp_here >= 1";
        String sqlNH = "UPDATE employee " +
                "SET " +
                "base_salary = (base_salary * 1.15) +" + ref.get().getSabetHogug() +
                " WHERE work_exp_here < 1";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement()){
                statement.executeUpdate(sql);
                statement.executeUpdate(sqlH);
                statement.executeUpdate(sqlNH);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
}
