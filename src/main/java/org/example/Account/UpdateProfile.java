package org.example.Account;

import java.sql.*;
import java.util.Scanner;

public class UpdateProfile {
    User user;
    public void viewProfile(User user) {
        this.user = user;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int pw = user.getPassword().length();
            String pwstars = "*".repeat(pw);
            System.out.println("\n==================================================");
            System.out.println("                  User Profile                   ");
            System.out.println("==================================================");
            System.out.printf("%-15s: %s%n", "1. Username", user.getUsername());
            System.out.printf("%-15s: %s%n", "2. Email", user.getEmail());
            System.out.printf("%-15s: %s%n", "3. Password", pwstars);
            System.out.printf("%-15s: %s%n", "4. Login Type", user.getLoginType());
            System.out.println("==================================================\n");

            // Display options to the user
            System.out.println("Choose an option:");
            System.out.println("1. Update Profile");
            System.out.println("2. Go Back to Menu");

            // Handle user input for the options
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().strip();

            switch (choice) {
                case "1":
                    updateUserProfile();  // Call method to update the profile
                    break;
                case "2":
                    return;  // Go back to the menu (exit the current method)
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void updateUserProfile() {
        Scanner scanner = new Scanner(System.in);
        String prevEmail = user.getEmail(); // Save the current email for database updates

        while (true) {
            System.out.println("\n==================================================");
            System.out.println("               Update Profile Options             ");
            System.out.println("==================================================");
            System.out.println("1. Update Username");
            System.out.println("2. Update Email");
            System.out.println("3. Update Password");
            System.out.println("4. Exit");
            System.out.println("==================================================");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().strip();

            switch (choice) {
                case "1": // Update username
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine().strip();
                    user.setUsername(newUsername);
                    System.out.println("Username updated successfully!");
                    this.UpdateUser(prevEmail);
                    break;

                case "2": // Update email
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine().strip();

                    if (isEmailUnique(newEmail)) {
                        user.setEmail(newEmail);
                        System.out.println("Email updated successfully!");
                    } else {
                        System.out.println("Error: This email is already in use. Please try again.");
                    }
                    this.UpdateUser(prevEmail);
                    break;

                case "3": // Update password
                    System.out.print("Enter your current password: ");
                    String currentPassword = scanner.nextLine();

                    if (user.getPassword().equals(currentPassword)) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        user.setPassword(newPassword);
                        System.out.println("Password updated successfully!");
                    } else {
                        System.out.println("Error: Incorrect current password. Please try again.");
                    }
                    this.UpdateUser(prevEmail);
                    break;

                case "4": // Save changes and exit
                    this.UpdateUser(prevEmail); // Save changes to the database
                    System.out.println("Profile changes saved. Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Check if the email is unique in the database
    public boolean isEmailUnique(String newEmail) {
        String query = "SELECT email FROM users WHERE email = ?";
        String url = "jdbc:sqlite:articles.db";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newEmail);
            ResultSet rs = pstmt.executeQuery();

            return !rs.next(); // True if email does not exist in the database
        } catch (SQLException e) {
            System.out.println("Error checking email: " + e.getMessage());
            return false; // Default to false on error to prevent incorrect updates
        }
    }

    // Update the user details in the database
    public void UpdateUser(String prevEmail) {
        String url = "jdbc:sqlite:articles.db"; // Replace with your database file path
        String updateQuery = "UPDATE users SET username = ?, email = ?, password = ? WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            // Set parameters for the query
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, prevEmail); // Use prevEmail to identify the record

            // Execute the update
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }
}
