package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class extra {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:articles.db";
        String insertSQL = "INSERT INTO users (email, password, username, loginType) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, "ashan");
            pstmt.setString(2, "ashan");
            pstmt.setString(3, "ashan");
            pstmt.setString(4, "User");
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }
}
