import java.sql.*;

public class UserPreferences {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:articles.db";
        String createTableSQL = "CREATE TABLE IF NOT EXISTS preferences (" +
                "preferenceID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "interests TEXT NOT NULL," +
                "viewed_IDs TEXT," +
                "FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE)";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Create the table
            stmt.execute(createTableSQL);

        } catch (SQLException e) {
            System.out.println("Database setup failed: " + e.getMessage());
        }
    }

    public void updatePreferences(String interests, String username) {
        String url = "jdbc:sqlite:articles.db";
        String insertSQL = "INSERT INTO preferences (username, interests, viewed_IDs) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            // Set username and interests directly
            insertStmt.setString(1, username);       // Set username
            insertStmt.setString(2, interests);      // Set interests
            insertStmt.setString(3, "");             // Initialize viewed_IDs as an empty string or other default value

            insertStmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("Error: Duplicate entry for preferences.");
            } else {
                System.out.println("Error inserting preferences: " + e.getMessage());
            }
        }
    }
}
