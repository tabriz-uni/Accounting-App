package main.java.com.advprogram.accountingApp.dao;

import main.java.com.advprogram.accountingApp.core.JdbcConnection;
import main.java.com.advprogram.accountingApp.core.PostgreSqlGenericDao;
import main.java.com.advprogram.accountingApp.model.Employee;
import main.java.com.advprogram.accountingApp.model.GData;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GDataDaoImp implements GDataDao<GData> {

    private static final Logger LOGGER = Logger.getLogger(PostgreSqlGenericDao.class.getName());
    private final Optional<Connection> connection;

    public GDataDaoImp() {
        this.connection = JdbcConnection.getConnection();
    }
    
    @Override
    public Optional<GData> get(int i) {
        return connection.flatMap(conn -> {
            Optional<GData> ref = Optional.empty();
            String sql = "SELECT * FROM global_data LIMIT 1";

            try (Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {

                if (rs.next()) {
                    int baseSalary = rs.getInt("base_salary");
                    int bonMaskan = rs.getInt("bon_maskan");
                    int bonNagdi = rs.getInt("bon_nagdi");
                    int hagOlad = rs.getInt("hag_olad");
                    int payeSanavat = rs.getInt("paye_sanavat");
                    int sabetHogug = rs.getInt("sabet_hogug");
                    Date date = rs.getDate("app_date");

                    ref = Optional.of(new GData(baseSalary, bonMaskan, bonNagdi, hagOlad, payeSanavat, sabetHogug, date));

                    LOGGER.log(Level.INFO, "Found {0} in database", ref.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return ref;
        });
    }

    @Override
    public Collection<GData> getAll() {
        return null;
    }

    @Override
    public void save(GData gData) {

    }

    @Override
    public void update(GData gData) {
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
    public void delete(GData gData) {

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
        Optional<GData> ref = get(0);
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
                statement.executeUpdate(sqlH);
                statement.executeUpdate(sqlNH);
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
