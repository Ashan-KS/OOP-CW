package RecommendationEngine;
import java.io.PrintStream;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import Article.Article;

public class RecommendationEngine {
    List<Article> articlesInput = null;
    List<Article> userHistory = null;
    List<Article> dataset = null;
    List<Article> preferences = null;

    public RecommendationEngine() {
    }

    public void turnToString() {
        Iterator var1 = this.articlesInput.iterator();

        Article preference;
        Article preferenceArticle;
        while(var1.hasNext()) {
            preference = (Article)var1.next();
            preferenceArticle = new Article(preference.details(), 0);
            this.dataset.add(preferenceArticle);
        }

        var1 = this.userHistory.iterator();

        while(var1.hasNext()) {
            preference = (Article)var1.next();
            preferenceArticle = new Article(preference.details(), preference.getRating());
            this.preferences.add(preferenceArticle);
        }

    }

    public void setArticlesInput(List<Article> articlesInput) {
        this.articlesInput = articlesInput;
    }

    public List<Article> getArticlesInput() {
        return this.articlesInput;
    }

    public void setUserHistory(List<Article> userHistory) {
        this.userHistory = userHistory;
    }

    public List<Article> getUserHistory() {
        return this.userHistory;
    }

    public void Recommend() {

    }
}
