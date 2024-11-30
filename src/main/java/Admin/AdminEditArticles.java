package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Article.Article;

public class AdminEditArticles {
    private static final Scanner scanner;

    public AdminEditArticles() {
    }

    public void editArticle() {
        String url = "jdbc:sqlite:articles.db";
        System.out.println("\n--- Edit Article ---");
        System.out.print("Enter the Article ID you want to edit: ");

        int articleID;
        try {
            articleID = Integer.parseInt(scanner.nextLine().strip());
        } catch (NumberFormatException var14) {
            System.out.println("Invalid Article ID. Please enter a valid number.");
            return;
        }

        String selectSQL = "SELECT * FROM articles WHERE articleID = ?";
        Article article = null;

        try {
            Connection conn;
            label139: {
                conn = DriverManager.getConnection(url);

                try {
                    PreparedStatement pstmt = conn.prepareStatement(selectSQL);

                    label141: {
                        try {
                            pstmt.setInt(1, articleID);
                            ResultSet rs = pstmt.executeQuery();
                            if (!rs.next()) {
                                System.out.println("No article found with the given ID.");
                                break label141;
                            }

                            article = new Article(rs.getString("headline"), rs.getString("description"), rs.getString("authors"), rs.getString("category"), rs.getString("url"), rs.getString("date"));
                            System.out.println("\nArticle found! Ready to edit.\n");
                        } catch (Throwable var18) {
                            if (pstmt != null) {
                                try {
                                    pstmt.close();
                                } catch (Throwable var11) {
                                    var18.addSuppressed(var11);
                                }
                            }

                            throw var18;
                        }

                        if (pstmt != null) {
                            pstmt.close();
                        }
                        break label139;
                    }

                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Throwable var19) {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (Throwable var10) {
                            var19.addSuppressed(var10);
                        }
                    }

                    throw var19;
                }

                if (conn != null) {
                    conn.close();
                }

                return;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var20) {
            System.out.println("Error fetching article: " + var20.getMessage());
            return;
        }

        if (article != null) {
            this.editArticleFields(article);
        }

        String updateSQL = "UPDATE articles SET headline = ?, description = ?, authors = ?, category = ?, url = ?, date = ? WHERE articleID = ?";

        try {
            Connection conn = DriverManager.getConnection(url);

            try {
                PreparedStatement pstmt = conn.prepareStatement(updateSQL);

                try {
                    pstmt.setString(1, article.getHeadline());
                    pstmt.setString(2, article.getDescription());
                    pstmt.setString(3, article.getAuthors());
                    pstmt.setString(4, article.getCategory());
                    pstmt.setString(5, article.getUrl());
                    pstmt.setString(6, article.getDate());
                    pstmt.setInt(7, articleID);
                    pstmt.executeUpdate();
                    System.out.println("\nArticle updated successfully!");
                } catch (Throwable var15) {
                    if (pstmt != null) {
                        try {
                            pstmt.close();
                        } catch (Throwable var13) {
                            var15.addSuppressed(var13);
                        }
                    }

                    throw var15;
                }

                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Throwable var16) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var12) {
                        var16.addSuppressed(var12);
                    }
                }

                throw var16;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var17) {
            System.out.println("Error updating article: " + var17.getMessage());
        }

    }

    private void editArticleFields(Article article) {
        while(true) {
            System.out.println("\nCurrent Article Details:");
            System.out.println("1. Headline: " + article.getHeadline());
            System.out.println("2. Description: " + article.getDescription());
            System.out.println("3. Authors: " + article.getAuthors());
            System.out.println("4. Category: " + article.getCategory());
            System.out.println("5. URL: " + article.getUrl());
            System.out.println("6. Date: " + article.getDate());
            System.out.println("7. Save and Exit");
            System.out.print("\nEnter the number of the field you want to edit: ");
            switch (scanner.nextLine().strip()) {
                case "1":
                    article.setHeadline(this.getInput("Enter new headline: "));
                    break;
                case "2":
                    article.setDescription(this.getInput("Enter new description: "));
                    break;
                case "3":
                    article.setAuthors(this.getInput("Enter new authors: "));
                    break;
                case "4":
                    article.setCategory(this.getInput("Enter new category: "));
                    break;
                case "5":
                    article.setUrl(this.getInput("Enter new URL: "));
                    break;
                case "6":
                    article.setDate(this.getInput("Enter new date (YYYY-MM-DD): "));
                    break;
                case "7":
                    System.out.println("\nSaving changes...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().strip();
    }

    static {
        scanner = new Scanner(System.in);
    }
}
