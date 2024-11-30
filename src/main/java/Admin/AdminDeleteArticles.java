package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Article.Article;

public class AdminDeleteArticles {
    private static final Scanner scanner;

    public AdminDeleteArticles() {
    }

    public void deleteArticle() {
        String url = "jdbc:sqlite:articles.db";
        System.out.println("\n--- Delete Article ---");
        System.out.print("Enter the Article ID you want to delete: ");

        int articleID;
        try {
            articleID = Integer.parseInt(scanner.nextLine().strip());
        } catch (NumberFormatException var15) {
            System.out.println("Invalid Article ID. Please enter a valid number.");
            return;
        }

        String selectSQL = "SELECT * FROM articles WHERE articleID = ?";
        Article article = null;

        try {
            label138: {
                Connection conn;
                label139: {
                    conn = DriverManager.getConnection(url);

                    try {
                        label140: {
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
                                    System.out.println("\nArticle found:");
                                    this.printArticleDetails(article);
                                } catch (Throwable var19) {
                                    if (pstmt != null) {
                                        try {
                                            pstmt.close();
                                        } catch (Throwable var12) {
                                            var19.addSuppressed(var12);
                                        }
                                    }

                                    throw var19;
                                }

                                if (pstmt != null) {
                                    pstmt.close();
                                }
                                break label140;
                            }

                            if (pstmt != null) {
                                pstmt.close();
                            }
                            break label139;
                        }
                    } catch (Throwable var20) {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (Throwable var11) {
                                var20.addSuppressed(var11);
                            }
                        }

                        throw var20;
                    }

                    if (conn != null) {
                        conn.close();
                    }
                    break label138;
                }

                if (conn != null) {
                    conn.close();
                }

                return;
            }
        } catch (SQLException var21) {
            System.out.println("Error fetching article: " + var21.getMessage());
            return;
        }

        System.out.print("\nAre you sure you want to delete this article? (yes/no): ");
        String confirmation = scanner.nextLine().strip().toLowerCase();
        if (confirmation.equals("yes")) {
            String deleteSQL = "DELETE FROM articles WHERE articleID = ?";

            try {
                Connection conn = DriverManager.getConnection(url);

                try {
                    PreparedStatement pstmt = conn.prepareStatement(deleteSQL);

                    try {
                        pstmt.setInt(1, articleID);
                        pstmt.executeUpdate();
                        System.out.println("\nArticle deleted successfully!");
                    } catch (Throwable var16) {
                        if (pstmt != null) {
                            try {
                                pstmt.close();
                            } catch (Throwable var14) {
                                var16.addSuppressed(var14);
                            }
                        }

                        throw var16;
                    }

                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Throwable var17) {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (Throwable var13) {
                            var17.addSuppressed(var13);
                        }
                    }

                    throw var17;
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var18) {
                System.out.println("Error deleting article: " + var18.getMessage());
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

    static {
        scanner = new Scanner(System.in);
    }
}
