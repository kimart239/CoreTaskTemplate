package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String dbUrl = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
    private static final String dbUser = "root";
    private static final String dbPassword = "mysqlpass080577";

    public static Connection getConnection() throws SQLException {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
