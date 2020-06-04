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
        GData refOld = getGData();
        IncreGlobalData();
        GData ref = getGData();
        IncreBaseSalary(ref);
    }
    private void IncreExp() {
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
    private void IncreBaseSalary(GData ref) {
        String sqlH = "UPDATE employee " +
                "SET " +
                "base_salary = (base_salary * 1.5) +" + ref.sabetHogug + ref.payeSanavat +
                " WHERE work_exp_here >= 1";
        String sqlNH = "UPDATE employee " +
                "SET " +
                "base_salary = (base_salary * 1.5) +" + ref.sabetHogug +
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
    private void IncreGlobalData() {
        String sql = "UPDATE global_data "
                + "SET "
                + "bon_maskan = bon_maskan * 1.20, "
                + "bon_nagdi = bon_nagdi * 1.30, "
                + "hag_olad = hag_olad * 1.20, "
                + "paye_sanavat = paye_sanavat * 1.20,"
                + "sabet_hogug = (sabet_hogug * 1.20),"
                + "base_salary = (base_salary * 1.20)"
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
    private GData getGData() {
        String sqlT = "SELECT * FROM global_data LIMIT 1";
        GData ref = new GData();
        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery(sqlT)) {
                ref.baseSalary = rs.getInt("base_salary");
                ref.bonMaskan = rs.getInt("bon_maskan");
                ref.bonNagdi = rs.getInt("bon_nagdi");
                ref.hagOlad = rs.getInt("hag_olad");
                ref.payeSanavat = rs.getInt("paye_sanavat");
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