package org.example.Admin;

import org.example.Article.Article;

import java.sql.*;
import java.util.Scanner;

public class AdminEditArticles {

    private static final Scanner scanner = new Scanner(System.in);

    // Method to edit an article
    public void editArticle() {
        String url = "jdbc:sqlite:articles.db";
        System.out.println("\n--- Edit Article ---");

        // Ask for the article ID
        System.out.print("Enter the Article ID you want to edit: ");
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
                System.out.println("\nArticle found! Ready to edit.\n");
            } else {
                System.out.println("No article found with the given ID.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching article: " + e.getMessage());
            return;
        }

        // Allow user to edit the article fields
        if (article != null) {
            editArticleFields(article);
        }

        // Update the article in the database
        String updateSQL = "UPDATE articles SET headline = ?, description = ?, authors = ?, category = ?, url = ?, date = ? WHERE articleID = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, article.getHeadline());
            pstmt.setString(2, article.getDescription());
            pstmt.setString(3, article.getAuthors());
            pstmt.setString(4, article.getCategory());
            pstmt.setString(5, article.getUrl());
            pstmt.setString(6, article.getDate());
            pstmt.setInt(7, articleID);

            pstmt.executeUpdate();
            System.out.println("\nArticle updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating article: " + e.getMessage());
        }
    }

    // Method to allow the user to edit fields of the article
    private void editArticleFields(Article article) {
        while (true) {
            System.out.println("\nCurrent Article Details:");
            System.out.println("1. Headline: " + article.getHeadline());
            System.out.println("2. Description: " + article.getDescription());
            System.out.println("3. Authors: " + article.getAuthors());
            System.out.println("4. Category: " + article.getCategory());
            System.out.println("5. URL: " + article.getUrl());
            System.out.println("6. Date: " + article.getDate());
            System.out.println("7. Save and Exit");
            System.out.print("\nEnter the number of the field you want to edit: ");

            String choice = scanner.nextLine().strip();

            switch (choice) {
                case "1":
                    article.setHeadline(getInput("Enter new headline: "));
                    break;
                case "2":
                    article.setDescription(getInput("Enter new description: "));
                    break;
                case "3":
                    article.setAuthors(getInput("Enter new authors: "));
                    break;
                case "4":
                    article.setCategory(getInput("Enter new category: "));
                    break;
                case "5":
                    article.setUrl(getInput("Enter new URL: "));
                    break;
                case "6":
                    article.setDate(getInput("Enter new date (YYYY-MM-DD): "));
                    break;
                case "7":
                    System.out.println("\nSaving changes...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    // Helper method to handle user input
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().strip();
    }
}
