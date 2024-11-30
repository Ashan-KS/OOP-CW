package View;

import java.util.List;
import java.util.Scanner;
import Account.User;
import Article.Article;
import Database.Database;

public class View {
    User user;
    private static final int PAGE_SIZE = 10;

    public View() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void displayArticles(List<Article> articles) {
        Scanner scanner = new Scanner(System.in);
        if (articles.isEmpty()) {
            System.out.println("\nNo articles available to display.");
        } else {
            int totalArticles = articles.size();
            int currentPage = 0;

            while(true) {
                while(true) {
                    int start = currentPage * 10;
                    int end = Math.min(start + 10, totalArticles);
                    System.out.println("\n--- Articles (Page " + (currentPage + 1) + ") ---");

                    for(int i = start; i < end; ++i) {
                        System.out.printf("%d. %s\n", i + 1, ((Article)articles.get(i)).getHeadline());
                    }

                    if (end < totalArticles && currentPage > 0) {
                        System.out.println("\nEnter the number of the article to view, 'n' to view the next page, 'p' to view the previous page, or 'q' to quit:");
                    } else if (end < totalArticles) {
                        System.out.println("\nEnter the number of the article to view, 'n' to view the next page, or 'q' to quit:");
                    } else if (currentPage > 0) {
                        System.out.println("\nEnter the number of the article to view, 'p' to view the previous page, or 'q' to quit:");
                    } else {
                        System.out.println("\nEnter the number of the article to view or 'q' to quit:");
                    }

                    String input = scanner.nextLine().strip();
                    if (input.equalsIgnoreCase("q")) {
                        System.out.println("Exiting article view...");
                        return;
                    }

                    if (input.equalsIgnoreCase("n") && end < totalArticles) {
                        ++currentPage;
                    } else if (input.equalsIgnoreCase("p") && currentPage > 0) {
                        --currentPage;
                    } else {
                        try {
                            int selectedIndex = Integer.parseInt(input) - 1;
                            if (selectedIndex >= start && selectedIndex < end) {
                                Article selectedArticle = (Article)articles.get(selectedIndex);
                                this.displayArticleDetails(selectedArticle);
                            } else {
                                System.out.println("Invalid article number. Please select a valid article.");
                            }
                        } catch (NumberFormatException var10) {
                            System.out.println("Invalid input. Please enter a valid number or command.");
                        }
                    }
                }
            }
        }
    }

    private void displayArticleDetails(Article article) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Article Details ---");
        System.out.printf("Headline   : %s\n", article.getHeadline());
        System.out.printf("Description: %s\n", article.getDescription());
        System.out.printf("Date       : %s\n", article.getDate());
        System.out.printf("Authors    : %s\n", article.getAuthors());
        System.out.printf("URL        : %s\n", article.getUrl());
        System.out.println("\nHow would you rate this article?");
        System.out.println("1. Like");
        System.out.println("2. Neutral");
        System.out.println("3. Dislike");
        System.out.print("Enter your rating: ");

        while(true) {
            try {
                int rating;
                switch (scanner.nextLine().strip()) {
                    case "1":
                        rating = 1;
                        break;
                    case "2":
                        rating = 0;
                        break;
                    case "3":
                        rating = -1;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid option. Please enter 1, 2, or 3.");
                }

                article.setRating(rating);
                Database database = new Database();
                database.updateUserPreferences(this.user.getId(), article);
                return;
            } catch (IllegalArgumentException var7) {
                System.out.println(var7.getMessage());
            }
        }
    }
}
