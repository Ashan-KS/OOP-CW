package org.example.Account;

import java.sql.*;
import java.util.Scanner;

public class Signup {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Signup() {
        System.out.println("\n=============================================================================================================================================");
        System.out.println("                                                     New User Registration                                                        ");

        String email;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String url = "jdbc:sqlite:articles.db";
            System.out.print("     - Enter email: ");
            email = scanner.nextLine();

            // Check if the email is already taken by a User
            String query = "SELECT 1 FROM users WHERE email = ?";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) { // If a row exists, the email is already taken
                    System.out.println("This email is already taken by a user. Please enter a different email.");
                } else {
                    // Email is unique for "User" login type, exit the loop
                    break;
                }
            } catch (SQLException e) {
                System.out.println("Error checking email: " + e.getMessage());
            }
        }

        System.out.print("     - Enter password: ");
        String password = scanner.nextLine();
        System.out.print("     - Enter username: ");
        String username = scanner.nextLine();

        User user = new User(username, email, password, "User");
        this.user = user;
        insertUser();

        System.out.println("\n                                                     Account Registered Successfully                                                         ");
        System.out.println("=============================================================================================================================================");
        System.out.println("=============================================================================================================================================");
        System.out.println("                                                            Welcome back " + user.getUsername());
    }

    public void insertUser(){
        String url = "jdbc:sqlite:articles.db";
        String insertSQL = "INSERT INTO users (email, password, username, loginType) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getLoginType());
            pstmt.executeUpdate();

        } catch (SQLException e) {
                System.out.println("Error inserting user: " + e.getMessage());
            }
        }
}
