package Article;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FetchArticles {
    private List<Article> articles = new ArrayList();
    private List<Article> articlesInput = new ArrayList();

    public FetchArticles() {
    }

    public List<Article> getArticlesInput() {
        return this.articlesInput;
    }

    public List<Article> fetchAllArticles() {
        String url = "jdbc:sqlite:articles.db";
        String selectSQL = "SELECT * FROM articles";

        try {
            Connection conn = DriverManager.getConnection(url);

            try {
                Statement stmt = conn.createStatement();

                try {
                    ResultSet rs = stmt.executeQuery(selectSQL);

                    try {
                        while(rs.next()) {
                            String headline = rs.getString("headline");
                            String desc = rs.getString("description");
                            String authors = rs.getString("authors");
                            String category = rs.getString("category");
                            String link = rs.getString("url");
                            String date = rs.getString("date");
                            int id = rs.getInt("articleID");
                            Article article = new Article(headline, desc, authors, category, link, date, id);
                            Article articleInput = new Article(headline, desc, category);
                            this.articles.add(article);
                            this.articlesInput.add(articleInput);
                        }
                    } catch (Throwable var18) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var17) {
                                var18.addSuppressed(var17);
                            }
                        }

                        throw var18;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var19) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var16) {
                            var19.addSuppressed(var16);
                        }
                    }

                    throw var19;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var20) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var15) {
                        var20.addSuppressed(var15);
                    }
                }

                throw var20;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var21) {
            System.out.println("Error fetching articles: " + var21.getMessage());
        }

        return this.articles;
    }
}
