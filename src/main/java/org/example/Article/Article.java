package org.example.Article;

public class Article {
    private String headline;
    private String description;
    private String authors;
    private String category;
    private String url;
    private String date;

    public Article(String headline, String description, String authors, String category, String url, String date) {
        this.headline = headline;
        this.description = description;
        this.authors = authors;
        this.category = category;
        this.url = url;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
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
}

