package Account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Signup {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public Signup() {
        System.out.println("\n=============================================================================================================================================");
        System.out.println("                                                     New User Registration                                                        ");
        Scanner scanner = new Scanner(System.in);

        String email;
        String password;
        String username;
        while(true) {
            password = "jdbc:sqlite:articles.db";
            System.out.print("     - Enter email: ");
            email = scanner.nextLine();
            username = "SELECT 1 FROM users WHERE email = ?";

            try {
                Connection conn = DriverManager.getConnection(password);

                label79: {
                    try {
                        PreparedStatement pstmt = conn.prepareStatement(username);

                        label81: {
                            try {
                                pstmt.setString(1, email);
                                ResultSet rs = pstmt.executeQuery();
                                if (!rs.next()) {
                                    break label81;
                                }

                                System.out.println("This email is already taken by a user. Please enter a different email.");
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
                            break label79;
                        }

                        if (pstmt != null) {
                            pstmt.close();
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
                    break;
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var13) {
                System.out.println("Error checking email: " + var13.getMessage());
            }
        }

        System.out.print("     - Enter password: ");
        password = scanner.nextLine();
        System.out.print("     - Enter username: ");
        username = scanner.nextLine();
        User user = new User(username, email, password, "User");
        this.user = user;
        this.insertUser();
        System.out.println("\n                                                     Account Registered Successfully                                                         ");
        System.out.println("=============================================================================================================================================");
        System.out.println("=============================================================================================================================================");
        System.out.println("                                                            Welcome back " + user.getUsername());
    }

    public void insertUser() {
        String url = "jdbc:sqlite:articles.db";
        String insertSQL = "INSERT INTO users (email, password, username, loginType) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = DriverManager.getConnection(url);

            try {
                conn.setAutoCommit(false);

                try {
                    PreparedStatement pstmt = conn.prepareStatement(insertSQL, 1);

                    try {
                        pstmt.setString(1, this.user.getEmail());
                        pstmt.setString(2, this.user.getPassword());
                        pstmt.setString(3, this.user.getUsername());
                        pstmt.setString(4, this.user.getLoginType());
                        pstmt.executeUpdate();
                        ResultSet rs = pstmt.getGeneratedKeys();

                        try {
                            if (rs.next()) {
                                this.user.setId(rs.getInt(1));
                            }
                        } catch (Throwable var11) {
                            if (rs != null) {
                                try {
                                    rs.close();
                                } catch (Throwable var10) {
                                    var11.addSuppressed(var10);
                                }
                            }

                            throw var11;
                        }

                        if (rs != null) {
                            rs.close();
                        }

                        conn.commit();
                    } catch (Throwable var12) {
                        if (pstmt != null) {
                            try {
                                pstmt.close();
                            } catch (Throwable var9) {
                                var12.addSuppressed(var9);
                            }
                        }

                        throw var12;
                    }

                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (SQLException var13) {
                    conn.rollback();
                    System.out.println("Transaction failed, rolling back: " + var13.getMessage());
                }
            } catch (Throwable var14) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var8) {
                        var14.addSuppressed(var8);
                    }
                }

                throw var14;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var15) {
            System.out.println("Database connection failed: " + var15.getMessage());
        }

    }
}
