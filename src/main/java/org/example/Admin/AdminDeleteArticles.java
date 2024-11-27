package org.example.Admin;
import org.example.Article.Article;

import java.sql.*;
import java.util.Scanner;

public class AdminDeleteArticles {

    private static final Scanner scanner = new Scanner(System.in);

    // Method to delete an article by ID
    public void deleteArticle() {
        String url = "jdbc:sqlite:articles.db";
        System.out.println("\n--- Delete Article ---");

        // Ask the user for the Article ID
        System.out.print("Enter the Article ID you want to delete: ");
        int articleID;
        try {
            articleID = Integer.parseInt(scanner.nextLine().strip());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Article ID. Please enter a valid number.");
            return;
        }

        // Fetch the article from the database
        String selectSQL = "SELECT * FROM articles WHERE articleID = ?";
        Article article = null;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setInt(1, articleID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                article = new Article(
                        rs.getString("headline"),
                        rs.getString("description"),
                        rs.getString("authors"),
                        rs.getString("category"),
                        rs.getString("url"),
                        rs.getString("date")
                );
                System.out.println("\nArticle found:");
                printArticleDetails(article);
            } else {
                System.out.println("No article found with the given ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching article: " + e.getMessage());
            return;
        }

        // Confirm deletion
        System.out.print("\nAre you sure you want to delete this article? (yes/no): ");
        String confirmation = scanner.nextLine().strip().toLowerCase();

        if (confirmation.equals("yes")) {
            String deleteSQL = "DELETE FROM articles WHERE articleID = ?";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {

                pstmt.setInt(1, articleID);
                pstmt.executeUpdate();
                System.out.println("\nArticle deleted successfully!");

            } catch (SQLException e) {
                System.out.println("Error deleting article: " + e.getMessage());
            }
        } else {
            System.out.println("\nArticle deletion cancelled.");
        }
    }

    private void printArticleDetails(Article article) {
        System.out.println("\nCurrent Article Details:");
        System.out.println("1. Headline: " + article.getHeadline());
        System.out.println("2. Description: " + article.getDescription());
        System.out.println("3. Authors: " + article.getAuthors());
        System.out.println("4. Category: " + article.getCategory());
        System.out.println("5. URL: " + article.getUrl());
        System.out.println("6. Date: " + article.getDate());
    }
}
