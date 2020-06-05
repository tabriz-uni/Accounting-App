package main.java.com.advprogram.accountingApp.core;

import main.java.com.advprogram.accountingApp.api.GData;
import main.java.com.advprogram.accountingApp.api.Employee;
import main.java.com.advprogram.accountingApp.spi.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSqlDao implements Dao<Employee, Integer> {

    private static final Logger LOGGER = Logger.getLogger(PostgreSqlDao.class.getName());
    private final Optional<Connection> connection;

    public PostgreSqlDao() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Employee> get(int id) {
        return connection.flatMap(conn -> {
            Optional<Employee> employee = Optional.empty();
            String sql = "SELECT * FROM employee WHERE employee_id = " + id;

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

                    employee = Optional.of(new Employee(id, firstName, lastName, pass,
                                offsprings, title, workExp, workExpHere, baseSalary));

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
                    int id = resultSet.getInt("employee_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String pass = resultSet.getString("pass");
                    int offsprings = resultSet.getInt("offsprings");
                    String title = resultSet.getString("title");
                    int workExp = resultSet.getInt("work_exp");
                    int workExpHere = resultSet.getInt("work_exp_here");
                    int baseSalary = resultSet.getInt("base_salary");

                    Employee employee = new Employee(id, firstName, lastName, pass,
                              offsprings, title, workExp, workExpHere, baseSalary);

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
        Employee nonNullCustomer = Objects.requireNonNull(employee, message);
        String sql = "INSERT INTO "
                + "employee(employee_id, first_name, last_name, pass, " +
                "offsprings, title, work_exp, work_exp_here, base_salary) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullCustomer.getId());
                statement.setString(2, nonNullCustomer.getFirstName());
                statement.setString(3, nonNullCustomer.getLastName());
                statement.setString(4, nonNullCustomer.getPass());
                statement.setInt(5, nonNullCustomer.getOffsprings());
                statement.setString(6, nonNullCustomer.getTitle());
                statement.setInt(7, nonNullCustomer.getWorkExp());
                statement.setInt(8, nonNullCustomer.getWorkExpHere());
                statement.setInt(9, nonNullCustomer.getBaseSalary());

                int numberOfInsertedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "{0} created successfully? {1}",
                        new Object[]{nonNullCustomer, numberOfInsertedRows > 0});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void update(Employee employee) {
        String message = "The employee to be updated should not be null";
        Employee nonNullCustomer = Objects.requireNonNull(employee, message);
        String sql = "UPDATE employee "
                + "SET "
                + "first_name = ?, "
                + "last_name = ?, "
                + "pass = ? "
                + "WHERE "
                + "employee_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullCustomer.getFirstName());
                statement.setString(2, nonNullCustomer.getLastName());
                statement.setString(3, nonNullCustomer.getPass());
                statement.setInt(4, nonNullCustomer.getId());

                int numberOfUpdatedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the employee updated successfully? {0}",
                        numberOfUpdatedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void updatePass(Employee employee) {
        Employee nonNullCustomer = Objects.requireNonNull(employee);
        String sql = "UPDATE employee "
                + "SET "
                + "pass = ? "
                + "WHERE "
                + "employee_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, nonNullCustomer.getPass());
                statement.setInt(2, nonNullCustomer.getId());

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
        Employee nonNullCustomer = Objects.requireNonNull(employee, message);
        String sql = "DELETE FROM employee WHERE employee_id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, nonNullCustomer.getId());

                int numberOfDeletedRows = statement.executeUpdate();

                LOGGER.log(Level.INFO, "Was the employee deleted successfully? {0}",
                        numberOfDeletedRows > 0);

            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public GData getGData() {
        String sqlT = "SELECT * FROM global_data LIMIT 1";
        GData ref = new GData();
        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery(sqlT)) {
                ref.setBaseSalary(rs.getInt("base_salary"));
                ref.setBonMaskan(rs.getInt("bon_maskan"));
                ref.setBonNagdi(rs.getInt("bon_nagdi"));
                ref.setHagOlad(rs.getInt("hag_olad"));
                ref.setPayeSanavat(rs.getInt("paye_sanavat"));
                ref.setSabetHogug(rs.getInt("sabet_hogug"));
                ref.setDate(rs.getDate("app_date"));
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
        return ref;
    }
    @Override
    public void increExp() {
        String sql = "UPDATE employee " +
                "SET " +
                "work_exp = work_exp +1," +
                "work_exp_here = work_exp_here + 1;";
        connection.ifPresent(conn -> {
            try(Statement statement = conn.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
    @Override
    public void increBaseSalary() {
        GData ref = getGData();
        String sqlH = "UPDATE employee " +
                "SET " +
                "base_salary = (base_salary * 1.15) +" + ref.getSabetHogug() + ref.getPayeSanavat() +
                " WHERE work_exp_here >= 1";
        String sqlNH = "UPDATE employee " +
                "SET " +
                "base_salary = (base_salary * 1.15) +" + ref.getSabetHogug() +
                " WHERE work_exp_here < 1";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement()){
                statement.executeUpdate(sqlH);
                statement.executeUpdate(sqlNH);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
    @Override
    public void increGlobalData() {
        String sql = "UPDATE global_data "
                + "SET "
                + "bon_maskan = bon_maskan * 1.20, "
                + "bon_nagdi = bon_nagdi * 1.30, "
                + "hag_olad = hag_olad * 1.20, "
                + "paye_sanavat = paye_sanavat * 1.20,"
                + "sabet_hogug = (sabet_hogug * 1.20),"
                + "base_salary = (base_salary * 1.20)";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
    @Override
    public void nextMonth() {
        String sql = "UPDATE global_data "
                + "SET "
                + "app_date = app_date + INTERVAL '1 month'";
        connection.ifPresent(conn -> {
            try(Statement statement = conn.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}