package Main;

import View.View;
import java.util.List;
import java.util.Scanner;
import RecommendationEngine.RecommendationEngine;
import Account.Login;
import Account.Signup;
import Account.UpdateProfile;
import Account.User;
import Admin.AdminAddArticles;
import Admin.AdminDeleteArticles;
import Admin.AdminEditArticles;
import Article.Article;
import Article.ArticleCategorizer;
import Article.FetchArticles;
import Database.Database;

public class Driver {
    public static void main(String[] args) {
        User user = null;
        List<Article> articles = null;
        List<Article> articlesInput = null;
        List<Article> userHistory = null;
        Scanner scanner = new Scanner(System.in);
        Database.UsersTableMaker();
        Database.PreferenceTableMaker();
        Database.ArticlesTableMaker();
        ArticleCategorizer categorizer = new ArticleCategorizer();
        categorizer.Categorize();
        System.out.println("===============================================================================================================================================");
        System.out.println("                                                           News Article Recommendation System                                                 \n");
        System.out.println("     Please Select An Option\n");
        System.out.println("     - 1. Login");
        System.out.println("     - 2. Sign Up");
        boolean loggedIn = false;

        int choice;
        while(true) {
            System.out.print("\n     Your Choice: ");
            String input = scanner.nextLine().strip();

            try {
                choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    break;
                }

                System.out.println("Invalid choice! Please enter 1 or 2.");
            } catch (NumberFormatException var16) {
                System.out.println("Invalid input! Please enter a valid integer (1 or 2).");
            }
        }

        if (choice == 1) {
            Login login = new Login();
            loggedIn = true;
            user = login.getUser();
        } else if (choice == 2) {
            Signup signup = new Signup();
            loggedIn = true;
            user = signup.getUser();
        }

        if (loggedIn && "User".equalsIgnoreCase(user.getLoginType())) {
            FetchArticles fetch = new FetchArticles();
            articles = fetch.fetchAllArticles();
            articlesInput = fetch.getArticlesInput();

            label59:
            while(true) {
                System.out.println("===============================================================================================================================================");
                System.out.println("\n                                                          --Choose an option--             ");
                System.out.println("     - 1. View Articles");
                System.out.println("     - 2. Get Recommendations");
                System.out.println("     - 3. View Profile");
                System.out.println("     - 4. Logout");

                while(true) {
                    int option;
                    try {
                        System.out.print("\n     - Enter choice: ");
                        String input = scanner.nextLine().strip();
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException var14) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                        continue;
                    }

                    switch (option) {
                        case 1:
                            View view = new View();
                            view.setUser(user);
                            view.displayArticles(articles);
                            continue label59;
                        case 2:
                            userHistory = Database.retrievePreferences(user.getId(), articles);
                            articlesInput.removeAll(userHistory);
                            RecommendationEngine model = new RecommendationEngine();
                            model.setArticlesInput(articlesInput);
                            model.setUserHistory(userHistory);
                            model.Recommend();
                            continue label59;
                        case 3:
                            UpdateProfile update = new UpdateProfile();
                            update.viewProfile(user);
                            continue label59;
                        case 4:
                            System.out.println("Logging out...");
                            return;
                        default:
                            System.out.println("Invalid option, please try again.");
                            continue label59;
                    }
                }
            }
        } else if (loggedIn && "Admin".equalsIgnoreCase(user.getLoginType())) {
            label69:
            while(true) {
                System.out.println("===============================================================================================================================================");
                System.out.println("\n                                                          --Choose an option--             ");
                System.out.println("     - 1. Add Articles");
                System.out.println("     - 2. Edit Articles");
                System.out.println("     - 3. Remove Articles");
                System.out.println("     - 4. Logout");

                while(true) {
                    int option;
                    try {
                        System.out.print("\n     - Enter choice: ");
                        String input = scanner.nextLine().strip();
                        option = Integer.parseInt(input);
                    } catch (NumberFormatException var15) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                        continue;
                    }

                    switch (option) {
                        case 1:
                            AdminAddArticles adminAdd = new AdminAddArticles();
                            adminAdd.addArticles();
                            continue label69;
                        case 2:
                            AdminEditArticles adminEdit = new AdminEditArticles();
                            adminEdit.editArticle();
                            continue label69;
                        case 3:
                            AdminDeleteArticles adminDelete = new AdminDeleteArticles();
                            adminDelete.deleteArticle();
                            continue label69;
                        case 4:
                            System.out.println("Logging out...");
                            return;
                        default:
                            System.out.println("Invalid option, please try again.");
                            continue label69;
                    }
                }
            }
        } else {
            scanner.close();
        }
    }
}
