package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import Article.Article;
import Article.ArticleCategorizer;

public class AdminAddArticles {
    private static final Scanner scanner;

    public AdminAddArticles() {
    }

    public void addArticles() {
        boolean addMore = true;

        while(addMore) {
            this.addArticle();
            System.out.println("\n------------------------------------------");
            System.out.print("Do you want to add another article? (yes/no): ");
            String response = scanner.nextLine().strip().toLowerCase();
            if (!response.equals("yes")) {
                addMore = false;
                System.out.println("\nReturning to the main menu...");
            }
        }

    }

    public void addArticle() {
        System.out.println("\n------------------------------------------");
        System.out.println("           Add New Article");
        System.out.println("------------------------------------------");

        while(true) {
            String headline = this.getInput("Enter headline: ");
            if (!headline.isEmpty()) {
                while(true) {
                    String description = this.getInput("Enter description: ");
                    if (!description.isEmpty()) {
                        String authors = this.getInput("Enter authors: ");
                        String url = this.getInput("Enter URL: ");
                        String date = this.getInput("Enter date (YYYY-MM-DD): ");
                        String category = "";
                        Article newArticle = new Article(headline, description, authors, category, url, date);
                        this.insertArticleToDatabase(newArticle);
                        return;
                    }

                    System.out.println("Error: Description cannot be empty!");
                }
            }

            System.out.println("Error: Headline cannot be empty!");
        }
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().strip();
    }

    private void insertArticleToDatabase(Article article) {
        String url = "jdbc:sqlite:articles.db";
        String insertSQL = "INSERT INTO articles (headline, description, authors, category, predicted, url, date) VALUES (?, ?, ? , ?, ?, ?, ?)";

        try {
            Connection conn = DriverManager.getConnection(url);

            try {
                PreparedStatement pstmt = conn.prepareStatement(insertSQL);

                try {
                    pstmt.setString(1, article.getHeadline());
                    pstmt.setString(2, article.getDescription());
                    pstmt.setString(3, article.getAuthors());
                    pstmt.setString(4, article.getCategory());
                    pstmt.setString(5, article.getCategory());
                    pstmt.setString(6, article.getUrl());
                    pstmt.setString(7, article.getDate());
                    pstmt.executeUpdate();
                    System.out.println("\nArticle added successfully!");
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
        } catch (SQLException var12) {
            System.out.println("Error adding article: " + var12.getMessage());
        }

        ArticleCategorizer categorizer = new ArticleCategorizer();
        categorizer.Categorize();
    }

    static {
        scanner = new Scanner(System.in);
    }
}