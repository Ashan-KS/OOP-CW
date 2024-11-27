package org.example.Main;

import org.example.Account.Login;
import org.example.Account.Signup;
import org.example.Account.UpdateProfile;
import org.example.Account.User;
import org.example.Admin.AdminAddArticles;
import org.example.Admin.AdminDeleteArticles;
import org.example.Admin.AdminEditArticles;
import org.example.Article.ArticleCategorizer;
import org.example.Database.Database;

import java.util.*;

public class Driver {

    public static void main(String[] args) {

        User user = null;

        Scanner scanner = new Scanner(System.in);

        // Initialize database and create table if it doesn't exist
        Database.UsersTableMaker();
        Database.ArticlesTableMaker();
        ArticleCategorizer categorizer = new ArticleCategorizer();
        categorizer.Categorize();

        System.out.println("===============================================================================================================================================");
        System.out.println("                                                           News Article Recommendation System                                                 \n");
        System.out.println("     Please Select An Option\n");
        System.out.println("     - 1. Login");
        System.out.println("     - 2. Sign Up");

        boolean loggedIn = false;
        int choice = 0;
        while (true) {
            System.out.print("\n     Your Choice: ");
            String input = scanner.nextLine().strip(); // Take input as a string

            try {
                choice = Integer.parseInt(input); // Attempt to parse the input as an integer
                if (choice == 1 || choice == 2) {
                    break; // Exit loop if the choice is valid
                } else {
                    System.out.println("Invalid choice! Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
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

        // Once logged in, show options
        if (loggedIn && "User".equalsIgnoreCase(user.getLoginType())) {
            while (true) { // Outer loop for menu
                System.out.println("===============================================================================================================================================");
                System.out.println("\n                                                          --Choose an option--             ");
                System.out.println("     - 1. View Articles");
                System.out.println("     - 2. View Profile");
                System.out.println("     - 3. Logout");

                int option = -1; // Default invalid value for choice

                // Validate if the input is an integer
                while (true) {
                    try {
                        System.out.print("\n     - Enter choice: ");
                        String input = scanner.nextLine().strip();
                        option = Integer.parseInt(input); // Convert the string to an integer
                        break; // Exit validation loop on successful input
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                    }
                }

                // Process the validated choice
                switch (option) {
                    case 1:
                        System.out.println("viewing");; // Call the viewArticles method
                        break;
                    case 2:
                        UpdateProfile update = new UpdateProfile();
                        update.viewProfile(user);
                        // Add functionality to view profile here
                        break;
                    case 3:
                        System.out.println("Logging out...");
                        return; // Exit the menu loop and method
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            }
        }
        else if (loggedIn && "Admin".equalsIgnoreCase(user.getLoginType())) {
            while (true){
                System.out.println("===============================================================================================================================================");
                System.out.println("\n                                                          --Choose an option--             ");
                System.out.println("     - 1. Add Articles");
                System.out.println("     - 2. Edit Articles");
                System.out.println("     - 3. Remove Articles");
                System.out.println("     - 4. Logout");

                int option = -1; // Default invalid value for choice

                // Validate if the input is an integer
                while (true) {
                    try {
                        System.out.print("\n     - Enter choice: ");
                        String input = scanner.nextLine().strip();
                        option = Integer.parseInt(input); // Convert the string to an integer
                        break; // Exit validation loop on successful input
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                    }
                }

                // Process the validated choice
                switch (option) {
                    case 1:
                        AdminAddArticles adminAdd = new AdminAddArticles();
                        adminAdd.addArticles();
                        break;
                    case 2:
                        AdminEditArticles adminEdit = new AdminEditArticles();
                        adminEdit.editArticle();
                        break;
                    case 3:
                        AdminDeleteArticles adminDelete = new AdminDeleteArticles();
                        adminDelete.deleteArticle();
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        return; // Exit the menu loop and method
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            }
        }

        scanner.close();
    }
}

