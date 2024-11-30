package Article;

public class Article {
    private int id;
    private String headline;
    private String description;
    private String authors;
    private String category;
    private String url;
    private String date;
    private int rating;
    private String details;
    private float similarity;

    public Article(String headline, String description, String authors, String category, String url, String date) {
        this.headline = headline;
        this.description = description;
        this.authors = authors;
        this.category = category;
        this.url = url;
        this.date = date;
    }

    public Article(String details, int rating) {
        this.details = details;
        this.rating = rating;
    }

    public Article(String headline, String description, String authors, String category, String url, String date, int id) {
        this.headline = headline;
        this.description = description;
        this.authors = authors;
        this.category = category;
        this.url = url;
        this.date = date;
        this.id = id;
    }

    public Article(String headline, String description, String category, int rating) {
        this.headline = headline;
        this.description = description;
        this.category = category;
        this.rating = rating;
    }

    public Article(String headline, String description, String category) {
        this.headline = headline;
        this.description = description;
        this.category = category;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public float getSimilarity() {
        return this.similarity;
    }

    public String getCategory() {
        return this.category;
    }

    public String getAuthors() {
        return this.authors;
    }

    public String getDescription() {
        return this.description;
    }

    public String getHeadline() {
        return this.headline;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDate() {
        return this.date;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }

    public String details() {
        this.details = this.headline + ", " + this.description + ", " + this.category;
        return this.details;
    }
}
