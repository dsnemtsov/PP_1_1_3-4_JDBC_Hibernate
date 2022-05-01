package jm.task.core.jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    private static UserDaoJDBCImpl instance;

    private final PreparedStatement addUser;
    private final PreparedStatement removeUser;
    private final PreparedStatement getAllUsers;
    private final PreparedStatement cleanUsersTable;

    private static final String SQL_TO_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS user(" +
            "id BIGINT PRIMARY KEY  AUTO_INCREMENT, " +
            "name VARCHAR(50), " +
            "lastName VARCHAR(50), " +
            "age TINYINT);";
    private static final String SQL_TO_DROP_TABLE = "DROP TABLE IF EXISTS user";
    private static final String SQL_TO_ADD_USER = "INSERT INTO user(name, lastName, age) VALUES (?, ?, ?);";
    private static final String SQL_TO_REMOVE_USER = "DELETE FROM user where id = ?";
    private static final String SQL_TO_GET_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_TO_CLEAN_USERS_TABLE = "DELETE FROM user";

    private UserDaoJDBCImpl() {
        try {
            addUser = Util.getConnection().prepareStatement(SQL_TO_ADD_USER);
            removeUser = Util.getConnection().prepareStatement(SQL_TO_REMOVE_USER);
            getAllUsers = Util.getConnection().prepareStatement(SQL_TO_GET_ALL_USERS);
            cleanUsersTable = Util.getConnection().prepareStatement(SQL_TO_CLEAN_USERS_TABLE);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static synchronized UserDaoJDBCImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoJDBCImpl();
        }
        return instance;
    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQL_TO_CREATE_USER_TABLE);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQL_TO_DROP_TABLE);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            addUser.setString(1, name);
            addUser.setString(2, lastName);
            addUser.setByte(3, age);

            addUser.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            removeUser.setLong(1, id);

            removeUser.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new LinkedList<>();
        try {
            ResultSet rs = getAllUsers.executeQuery();
            while (rs.next()) {
                result.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try {
            cleanUsersTable.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
