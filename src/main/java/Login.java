import java.sql.*;

class SignUp {
    private String email;
    private String password;
    private String username;

    // Constructor to initialize email, password, and username
    public SignUp(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // Method to set up the database and create the users table if it doesn't exist
    public static void initializeDatabase() {
        String url = "jdbc:sqlite:articles.db";
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "userID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "username TEXT NOT NULL UNIQUE)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Create the table
            stmt.execute(createTableSQL);

        } catch (SQLException e) {
            System.out.println("Database setup failed: " + e.getMessage());
        }
    }

    // Method to save the user data into the database
    public void save() {
        String url = "jdbc:sqlite:articles.db";
        String insertSQL = "INSERT INTO users (email, password, username) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("Error: The email or username is already in use.");
            } else {
                System.out.println("Error inserting user: " + e.getMessage());
            }
        }
    }
}

// Login Class for handling user login
class Login {
    private String email;
    private String password;
    private String username;

    // Constructor
    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Method to authenticate user credentials and fetch the username
    public boolean authenticate() {
        String url = "jdbc:sqlite:articles.db";
        String query = "SELECT password, username FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                this.username = rs.getString("username");

                if (storedPassword.equals(password)) {
                    System.out.println("\n===============================================================================================================================================");
                    System.out.println("                                                            Welcome back " + username);
                    return true;
                } else {
                    System.out.println("Invalid password.");
                    return false;
                }
            } else {
                System.out.println("No user found with the provided email.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }

    // Getter for username
    public String getUsername() {
        return username;
    }
}