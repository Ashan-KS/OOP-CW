package org.example.Account;

import java.sql.*;
import java.util.Scanner;

public class Login {
    private User user;

    public User getUser() {
        return user;
    }

    // Constructor
    public Login() {
        System.out.println("\n===============================================================================================================================================");
        System.out.println("                                                           Login to Existing Account                                                          ");

        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:sqlite:articles.db";

        // Check if the email exists
        while (true) {
            System.out.print("\n     - Enter email: ");
            String email = scanner.nextLine().strip();

            String query = "SELECT password, username, loginType FROM users WHERE email = ?";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();

                // Check if a result exists
                if (rs.next()) {
                    // Populate user object with details from the database
                    user = new User(
                            rs.getString("username"),
                            email,
                            rs.getString("password"),
                            rs.getString("loginType")
                    );
                    break; // Exit the loop if the email exists
                } else {
                    System.out.println("No account registered with this email. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("Error checking email: " + e.getMessage());
            }
        }

        while (true){
            // Prompt for the password
            System.out.print("     - Enter password: ");
            String password = scanner.nextLine().strip();
            if (password.equals(user.getPassword())) {
                break;
            }
            else {
                System.out.println("      Password Incorrect, Try again\n");
            }
        }

        // Prompt for the login type
        while (true) {
            System.out.print("     - Select login type (User/Admin): ");
            String loginType = scanner.nextLine().strip();

            if (!(loginType.equalsIgnoreCase("User") || loginType.equalsIgnoreCase("Admin"))) {
                System.out.println("Invalid login type. Please enter either 'User' or 'Admin'.");
                break; // Valid login type
            } else if (loginType.equalsIgnoreCase("Admin") && "User".equals(user.getLoginType())) {
                System.out.println("Access denied. Admin privileges are not available for your account. Please try logging in as a User.");
            } else {
                user.setLoginType(loginType);
                break;
            }
        }

        System.out.println("\n=============================================================================================================================================");
        System.out.println("                                                            Welcome back " + user.getUsername());
    }
}
