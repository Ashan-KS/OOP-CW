import java.util.*;

public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize database and create table if it doesn't exist
        SignUp.initializeDatabase();

        System.out.println("===============================================================================================================================================");
        System.out.println("                                                           News Article Recommendation System                                                 \n");
        System.out.println("     Please Select An Option\n");
        System.out.println("     - 1. Login");
        System.out.println("     - 2. Sign Up");

        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("\n     Your Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            System.out.println("\n===============================================================================================================================================");
        }

        boolean loggedIn = false;
        String username = null;

        if (choice == 1) {
            System.out.println("                                                           Login to Existing Account                                                          \n");
            System.out.print("\n     - Enter email: ");
            String email = scanner.nextLine();
            System.out.print("     - Enter password: ");
            String password = scanner.nextLine();

            Login login = new Login(email, password);
            loggedIn = login.authenticate();  // Check credentials from the database
            username = login.getUsername();   // Retrieve username if login is successful

        } else if (choice == 2) {
            // User chooses to sign up
            System.out.println("                                                             Register New Account                                                             \n");
            System.out.print("     - Enter email: ");
            String email = scanner.nextLine();
            System.out.print("     - Enter password: ");
            String password = scanner.nextLine();
            System.out.print("     - Enter username: ");
            String usernameInput = scanner.nextLine();


            SignUp signUp = new SignUp(email, password, usernameInput);
            signUp.save();

            Map<Integer, String> categories = new HashMap<>();
            categories.put(1, "Politics");
            categories.put(2, "Business");
            categories.put(3, "Crime");
            categories.put(4, "Environment");
            categories.put(5, "Comedy");
            categories.put(6, "Sports");
            categories.put(7, "Travel");
            categories.put(8, "Style & Beauty");
            categories.put(9, "Technology");
            categories.put(10, "Entertainment");

            System.out.println("\n                                                          --News Categories--                                                                  ");
            System.out.println("===============================================================================================================================================");
            System.out.println("|                            1. Politics                             ||                              2. Business                              |");
            System.out.println("|                            3. Crime                                ||                              4. Environment                           |");
            System.out.println("|                            5. Comedy                               ||                              6. Sports                                |");
            System.out.println("|                            7. Travel                               ||                              8. Style & Beauty                        |");
            System.out.println("|                            9. Technology                           ||                              10. Entertainment                        |");
            System.out.println("===============================================================================================================================================");
            System.out.println("                                           Enter your interests as numbers, separated by commas.");
            System.out.println("                                                        Sample input: 1,2,4,8,9,10");
            System.out.print("\n     - Enter your interest/s: ");
            String input = scanner.nextLine();



            // Convert input to a list of category names
            String[] selections = input.split(",");
            List<String> selectedCategories = new ArrayList<>();

            for (String selection : selections) {
                int categoryNum = Integer.parseInt(selection.trim());
                if (categories.containsKey(categoryNum)) {
                    selectedCategories.add(categories.get(categoryNum));
                }
            }

            // Join selected categories into a comma-separated string
            String interests = String.join(", ", selectedCategories);
            UserPreferences preferences = new UserPreferences();
            preferences.updatePreferences(interests, usernameInput);

            System.out.println("\n                                                     Account Registered Successfully                                                         ");
            System.out.println("===============================================================================================================================================");

        }

        // Once logged in, show options
        if (loggedIn) {
            while (true) {
                System.out.println("\n                                                          --Choose an option--             ");
                System.out.println("     - 1. View Articles");
                System.out.println("     - 2. View History");
                System.out.println("     - 3. View Profile");
                System.out.println("     - 4. Logout");

                System.out.print("\n     - Enter choice: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        viewArticles();
                        break;
                    case 2:
                        viewHistory(username); // Pass username to view user-specific history
                        break;
                    case 3:
                        viewProfile(username);
                        break;
                    case 4:
                        System.out.println("Logging out...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            }
        }

        scanner.close();
    }

    private static void viewArticles() {
        System.out.println("Displaying articles...");
        // Logic to display articles goes here
    }

    private static void viewHistory(String username) {
        System.out.println("Displaying history for " + username + "...");
        // Logic to display user history goes here
    }

    private static void viewProfile(String username) {
        System.out.println("Displaying profile for " + username + "...");
        // Logic to display user profile goes here
    }
}
