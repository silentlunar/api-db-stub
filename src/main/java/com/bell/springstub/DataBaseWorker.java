package com.bell.springstub;

import com.bell.springstub.model.User;

import java.sql.*;

public class DataBaseWorker {
    private final String url = "jdbc:postgresql://postgres:5432/mydb";
    private final String user = "admin";
    private final String password = "secret";

    public User getUserByLogin(String login) {
        User userObj = null;
        String query = "SELECT u.login, u.password, e.email, u.date " +
                "FROM users u JOIN user_emails e ON u.login = e.login " +
                "WHERE u.login = ?";
        try (
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    userObj = new User(
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getTimestamp("date")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB connection error", e);
        }
        return userObj;
    }

    public int insertUser(User user) {
        String query = "INSERT INTO users (login, password, date) VALUES (?, ?, ?);\n" +
                "INSERT INTO user_emails (login, email) VALUES (?, ?);";
        int updatedRows = 0;
        try (Connection conn = DriverManager.getConnection(url, this.user, this.password);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getEmail());
            ps.setTimestamp(3, new java.sql.Timestamp(user.getDate().getTime()));
            updatedRows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedRows;
    }
}
