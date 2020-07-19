package main.java.com.advprogram.accountingApp.dao;

import main.java.com.advprogram.accountingApp.core.JdbcConnection;
import main.java.com.advprogram.accountingApp.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImp implements UserDao<User> {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImp.class.getName());
    private final Optional<Connection> connection;

    public UserDaoImp() {
        this.connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<User> get(int id) {
        return connection.flatMap(conn -> {
            Optional<User> user = Optional.empty();
            String sql = "SELECT * FROM \"user\" WHERE user_id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String pass = resultSet.getString("pass");

                    user = Optional.of(new User(id, pass));

                    LOGGER.log(Level.INFO, "Found {0} in database", user.get());
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return user;
        });
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
