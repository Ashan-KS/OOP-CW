package Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateProfile {
    User user;

    public UpdateProfile() {
    }

    public void viewProfile(User user) {
        this.user = user;
        Scanner scanner = new Scanner(System.in);

        while(true) {
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
            System.out.println("Choose an option:");
            System.out.println("1. Update Profile");
            System.out.println("2. Go Back to Menu");
            System.out.print("Enter choice: ");
            switch (scanner.nextLine().strip()) {
                case "1":
                    this.updateUserProfile();
                    break;
                case "2":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void updateUserProfile() {
        Scanner scanner = new Scanner(System.in);
        String prevEmail = this.user.getEmail();

        while(true) {
            System.out.println("\n==================================================");
            System.out.println("               Update Profile Options             ");
            System.out.println("==================================================");
            System.out.println("1. Update Username");
            System.out.println("2. Update Email");
            System.out.println("3. Update Password");
            System.out.println("4. Exit");
            System.out.println("==================================================");
            System.out.print("Enter your choice: ");
            switch (scanner.nextLine().strip()) {
                case "1":
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine().strip();
                    this.user.setUsername(newUsername);
                    System.out.println("Username updated successfully!");
                    this.UpdateUser(prevEmail);
                    break;
                case "2":
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine().strip();
                    if (this.isEmailUnique(newEmail)) {
                        this.user.setEmail(newEmail);
                        System.out.println("Email updated successfully!");
                    } else {
                        System.out.println("Error: This email is already in use. Please try again.");
                    }

                    this.UpdateUser(prevEmail);
                    break;
                case "3":
                    System.out.print("Enter your current password: ");
                    String currentPassword = scanner.nextLine();
                    if (this.user.getPassword().equals(currentPassword)) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        this.user.setPassword(newPassword);
                        System.out.println("Password updated successfully!");
                    } else {
                        System.out.println("Error: Incorrect current password. Please try again.");
                    }

                    this.UpdateUser(prevEmail);
                    break;
                case "4":
                    this.UpdateUser(prevEmail);
                    System.out.println("Profile changes saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public boolean isEmailUnique(String newEmail) {
        String query = "SELECT email FROM users WHERE email = ?";
        String url = "jdbc:sqlite:articles.db";

        try {
            Connection conn = DriverManager.getConnection(url);

            boolean var7;
            try {
                PreparedStatement pstmt = conn.prepareStatement(query);

                try {
                    pstmt.setString(1, newEmail);
                    ResultSet rs = pstmt.executeQuery();
                    var7 = !rs.next();
                } catch (Throwable var10) {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        } catch (Throwable var9) {
                            var10.addSuppressed(var9);
                        }
                    }

                    throw var10;
                }

                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Throwable var11) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var8) {
                        var11.addSuppressed(var8);
                    }
                }

                throw var11;
            }

            if (conn != null) {
                conn.close();
            }

            return var7;
        } catch (SQLException var12) {
            System.out.println("Error checking email: " + var12.getMessage());
            return false;
        }
    }

    public void UpdateUser(String prevEmail) {
        String url = "jdbc:sqlite:articles.db";
        String updateQuery = "UPDATE users SET username = ?, email = ?, password = ? WHERE email = ?";

        try {
            Connection conn = DriverManager.getConnection(url);

            try {
                PreparedStatement stmt = conn.prepareStatement(updateQuery);

                try {
                    stmt.setString(1, this.user.getUsername());
                    stmt.setString(2, this.user.getEmail());
                    stmt.setString(3, this.user.getPassword());
                    stmt.setString(4, prevEmail);
                    stmt.executeUpdate();
                } catch (Throwable var10) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var9) {
                            var10.addSuppressed(var9);
                        }
                    }

                    throw var10;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var11) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var8) {
                        var11.addSuppressed(var8);
                    }
                }

                throw var11;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var12) {
            System.out.println("Error updating user: " + var12.getMessage());
        }

    }
}
