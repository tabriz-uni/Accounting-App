package main.java.com.advprogram.accountingApp;

import main.java.com.advprogram.accountingApp.core.JdbcConnection;

import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {
    private static final Logger LOGGER = Logger.getLogger(Calculator.class.getName());

    private final Optional<Connection> connection;

    public Calculator() { this.connection = JdbcConnection.getConnection(); }

    public void nextYear() {
        IncreGlobalData();
        var r = getGData();
        Object object = getGData();
        String sqlT = "SELECT * FROM global_data LIMIT 1";
        var ref = new Object() {
            int sabet_hogug;
        };
        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery(sqlT)) {
                ref.sabet_hogug = rs.getInt("sabet_hogug");
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });


    }
    private void IncBaseSalary() {
        String sql = "update title set base_salary = (base_salary * 1.5) +" + ref.sabet_hogug + + 4 + " where  IN" +
                " (select fid from Worker  group by fid  having count(wid) > )";
        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {


            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
    private void IncreGlobalData() {
        String sql = "UPDATE global_data "
                + "SET "
                + "bon_maskan = bon_maskan * 1.15, "
                + "bon_nagdi = bon_nagdi * 1.15, "
                + "hag_olad = hag_olad * 1.15, "
                + "paye_sanavat = paye_sanavat * 1.15,"
                + "sabet_hogug = (sabet_hogug * 1.15),"
                + "base_salary = (base_salary * 20)"
                + "WHERE "
                + "(SELECT * FROM global_data LIMIT 1)";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
    }
    private Object getGData() {
        String sqlT = "SELECT * FROM global_data LIMIT 1";
        var ref = new Object() {
            int baseSalary;
            int sabetHogug;
        };
        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery(sqlT)) {
                ref.baseSalary = rs.getInt("base_salary");
                ref.sabetHogug = rs.getInt("sabet_hogug");
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });
        return ref;
    }
}

class GData {
    int baseSalary;
    int bonMaskan;
    int bonNagdi;
    int hagOlad;
    int payeSanavat;
    int sabetHogug;
}
