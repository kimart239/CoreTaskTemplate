package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS user (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                    "name VARCHAR(45)," +
                                    "lastName VARCHAR (80), age INT) ";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS user";
    private static final String INSERT_USER = "INSERT INTO user(name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM user";
    private static final String CLEAR_TABLE = "TRUNCATE TABLE user";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable()  {
        try (Connection connection = Util.getConnection();
             Statement statement = (Statement) connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = (Statement) connection.createStatement()) {
            statement.execute(DROP_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        int i = 0;
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = rs.getByte("age");

                userList.add(new User(id, name, lastName, age));
                System.out.println(userList.get(i++));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = (Statement) connection.createStatement()) {
            statement.execute(CLEAR_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
