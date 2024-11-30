package Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    private User user;

    public User getUser() {
        return this.user;
    }

    public Login() {
        System.out.println("\n===============================================================================================================================================");
        System.out.println("                                                           Login to Existing Account                                                          ");
        Scanner scanner = new Scanner(System.in);
        String url = "jdbc:sqlite:articles.db";

        String loginType;
        while(true) {
            System.out.print("\n     - Enter email: ");
            loginType = scanner.nextLine().strip();
            String query = "SELECT userID, password, username, loginType FROM users WHERE email = ?";

            try {
                Connection conn = DriverManager.getConnection(url);

                label98: {
                    try {
                        label108: {
                            PreparedStatement pstmt = conn.prepareStatement(query);

                            label109: {
                                try {
                                    pstmt.setString(1, loginType);
                                    ResultSet rs = pstmt.executeQuery();
                                    if (rs.next()) {
                                        this.user = new User(rs.getString("username"), loginType, rs.getString("password"), rs.getString("loginType"));
                                        this.user.setId(rs.getInt("userID"));
                                        break label109;
                                    }

                                    System.out.println("No account registered with this email. Please try again.");
                                } catch (Throwable var11) {
                                    if (pstmt != null) {
                                        try {
                                            pstmt.close();
                                        } catch (Throwable var10) {
                                            var11.addSuppressed(var10);
                                        }
                                    }

                                    throw var11;
                                }

                                if (pstmt != null) {
                                    pstmt.close();
                                }
                                break label108;
                            }

                            if (pstmt != null) {
                                pstmt.close();
                            }
                            break label98;
                        }
                    } catch (Throwable var12) {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (Throwable var9) {
                                var12.addSuppressed(var9);
                            }
                        }

                        throw var12;
                    }

                    if (conn != null) {
                        conn.close();
                    }
                    continue;
                }

                if (conn != null) {
                    conn.close();
                }
                break;
            } catch (SQLException var13) {
                System.out.println("An error occurred while accessing the database: " + var13.getMessage());
            }
        }

        while(true) {
            System.out.print("     - Enter password: ");
            loginType = scanner.nextLine().strip();
            if (loginType.equals(this.user.getPassword())) {
                while(true) {
                    System.out.print("     - Select login type (User/Admin): ");
                    loginType = scanner.nextLine().strip();
                    if (!loginType.equalsIgnoreCase("User") && !loginType.equalsIgnoreCase("Admin")) {
                        System.out.println("Invalid login type. Please enter either 'User' or 'Admin'.");
                        break;
                    }

                    if (!loginType.equalsIgnoreCase("Admin") || !"User".equals(this.user.getLoginType())) {
                        this.user.setLoginType(loginType);
                        break;
                    }

                    System.out.println("Access denied. Admin privileges are not available for your account. Please try logging in as a User.");
                }

                System.out.println("\n=============================================================================================================================================");
                System.out.println("                                                            Welcome back " + this.user.getUsername());
                return;
            }

            System.out.println("      Password Incorrect, Try again\n");
        }
    }
}
