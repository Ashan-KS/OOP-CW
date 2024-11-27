package org.example.Admin;

import org.example.Article.Article;
import org.example.Article.ArticleCategorizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminAddArticles {

    private static final Scanner scanner = new Scanner(System.in);

    // Method to manage articles with repeat prompts
    public void addArticles() {
        boolean addMore = true;

        while (addMore) {
            addArticle();

            // Prompt to add another article
            System.out.println("\n------------------------------------------");
            System.out.print("Do you want to add another article? (yes/no): ");
            String response = scanner.nextLine().strip().toLowerCase();

            if (!response.equals("yes")) {
                addMore = false;
                System.out.println("\nReturning to the main menu...");
            }
        }
    }

    // Method to add a new article
    public void addArticle() {
        System.out.println("\n------------------------------------------");
        System.out.println("           Add New Article");
        System.out.println("------------------------------------------");

        String headline;
        String description;

        while (true) {
            headline = getInput("Enter headline: ");
            if (headline.isEmpty()) {
                System.out.println("Error: Headline cannot be empty!");
            } else {
                break;
            }
        }

        while (true) {
            description = getInput("Enter description: ");
            if (description.isEmpty()) {
                System.out.println("Error: Description cannot be empty!");
            } else {
                break;
            }
        }

        String authors = getInput("Enter authors: ");
        String url = getInput("Enter URL: ");
        String date = getInput("Enter date (YYYY-MM-DD): ");
        String category = "";

        // Create a new Article object
        Article newArticle = new Article(headline, description, authors, category, url, date);

        // Insert the article into the database
        insertArticleToDatabase(newArticle);
    }

    // Helper method to handle user input with validation
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().strip();
    }

    // Method to insert an article into the database
    private void insertArticleToDatabase(Article article) {
        String url = "jdbc:sqlite:articles.db";
        String insertSQL = "INSERT INTO articles (headline, description, authors, category, predicted, url, date) VALUES (?, ?, ? , ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Set values for the prepared statement
            pstmt.setString(1, article.getHeadline());
            pstmt.setString(2, article.getDescription());
            pstmt.setString(3, article.getAuthors());
            pstmt.setString(4, article.getCategory());
            pstmt.setString(5, article.getCategory());
            pstmt.setString(6, article.getUrl());
            pstmt.setString(7, article.getDate());

            // Execute the insert query
            pstmt.executeUpdate();
            System.out.println("\nArticle added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding article: " + e.getMessage());
        }
        ArticleCategorizer categorizer = new ArticleCategorizer();
        categorizer.Categorize();
    }
}
