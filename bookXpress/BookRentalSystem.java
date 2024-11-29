package bookXpress;
import java.util.*;

public class BookRentalSystem {
    static Map<String, Book> bookLibrary = new LinkedHashMap<>();
    static Map<String, Account> accountLibrary = new HashMap<>();
    static final String ADMIN_USERNAME = "admin";
    static final String ADMIN_PASSWORD = "password123";

    static {
        bookLibrary.put("The Great Gatsby", new FictionBook(3, 5, "Classic"));
        bookLibrary.put("1984", new FictionBook(5, 7, "Dystopian"));
        bookLibrary.put("Moby Dick", new NonFictionBook(2, 4, "Literature"));
    }

    public static void displayAvailableBooks() {
        System.out.println("\nAvailable Books:");
        int index = 1;
        for (Map.Entry<String, Book> entry : bookLibrary.entrySet()) {
            Book book = entry.getValue();
            System.out.print(index++ + ". " + entry.getKey() + " - Copies: " + book.copiesAvailable + ", Cost: $" + book.cost);
            if (book instanceof FictionBook) {
                System.out.println(", Genre: " + ((FictionBook) book).genre);
            } else if (book instanceof NonFictionBook) {
                System.out.println(", Subject: " + ((NonFictionBook) book).subject);
            } else {
                System.out.println();
            }
        }
    }

    public static void addNewBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book name:");
        String bookName = scanner.nextLine();
        System.out.println("Enter number of copies:");
        int copies = scanner.nextInt();
        System.out.println("Enter cost per rental:");
        int cost = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Is this a Fiction book or Non-Fiction book? (Enter 'Fiction' or 'Non-Fiction'):");
        String type = scanner.nextLine();

        if (bookLibrary.containsKey(bookName)) {
            System.out.println("Book already exists.");
        } else {
            if ("Fiction".equalsIgnoreCase(type)) {
                System.out.println("Enter genre:");
                String genre = scanner.nextLine();
                bookLibrary.put(bookName, new FictionBook(copies, cost, genre));
            } else if ("Non-Fiction".equalsIgnoreCase(type)) {
                System.out.println("Enter subject:");
                String subject = scanner.nextLine();
                bookLibrary.put(bookName, new NonFictionBook(copies, cost, subject));
            } else {
                System.out.println("Invalid type. Adding as a general book.");
                bookLibrary.put(bookName, new Book(copies, cost));
            }
            System.out.println("Book added successfully!");
        }
    }

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add a new book");
            System.out.println("2. Update book copies");
            System.out.println("3. Update book cost");
            System.out.println("4. View all books");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addNewBook();
                case 2 -> updateBookCopies();
                case 3 -> updateBookCost();
                case 4 -> displayAvailableBooks();
                case 5 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void updateBookCopies() {
        Scanner scanner = new Scanner(System.in);

        displayAvailableBooks();
        System.out.println("Enter the number of the book to update copies:");
        int choice = scanner.nextInt();

        List<String> bookTitles = new ArrayList<>(bookLibrary.keySet());
        if (choice < 1 || choice > bookTitles.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String bookName = bookTitles.get(choice - 1);
        System.out.println("Enter the new number of copies for \"" + bookName + "\":");
        int copies = scanner.nextInt();

        bookLibrary.get(bookName).copiesAvailable = copies;
        System.out.println("Updated copies for \"" + bookName + "\" successfully!");
    }

    public static void updateBookCost() {
        Scanner scanner = new Scanner(System.in);

        displayAvailableBooks();
        System.out.println("Enter the number of the book to update cost:");
        int choice = scanner.nextInt();

        List<String> bookTitles = new ArrayList<>(bookLibrary.keySet());
        if (choice < 1 || choice > bookTitles.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String bookName = bookTitles.get(choice - 1);
        System.out.println("Enter the new cost for \"" + bookName + "\":");
        int cost = scanner.nextInt();

        bookLibrary.get(bookName).cost = cost;
        System.out.println("Updated cost for \"" + bookName + "\" successfully!");
    }

    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (accountLibrary.containsKey(username)) {
            System.out.println("Account already exists. Try logging in.");
        } else {
            accountLibrary.put(username, new Account(username, password));
            System.out.println("Account created successfully!");
        }
    }

    public static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (!accountLibrary.containsKey(username)) {
            System.out.println("Account not found. Please create one.");
        } else {
            Account user = accountLibrary.get(username);
            if (user.password.equals(password)) {
                System.out.println("Login successful!");
                userMenu(username);
            } else {
                System.out.println("Incorrect password.");
            }
        }
    }

    public static void userMenu(String username) {
        Scanner scanner = new Scanner(System.in);
        Account user = accountLibrary.get(username);

        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("Logged in as: " + username);
            System.out.println("Balance: $" + user.balance);
            System.out.println("=================");
            System.out.println("1. View available books");
            System.out.println("2. Rent a book");
            System.out.println("3. Top-up balance");
            System.out.println("4. View inventory");
            System.out.println("5. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> displayAvailableBooks();
                case 2 -> rentBook(username);
                case 3 -> topUpBalance(username);
                case 4 -> viewInventory(username);
                case 5 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void rentBook(String username) {
        Scanner scanner = new Scanner(System.in);
        Account user = accountLibrary.get(username);

        displayAvailableBooks();
        System.out.println("Enter the number of the book you want to rent:");
        int choice = scanner.nextInt();

        List<String> bookTitles = new ArrayList<>(bookLibrary.keySet());
        if (choice < 1 || choice > bookTitles.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String bookName = bookTitles.get(choice - 1);
        Book book = bookLibrary.get(bookName);

        if (book.copiesAvailable <= 0) {
            System.out.println("Sorry, no copies available.");
        } else if (user.balance < book.cost) {
            System.out.println("Insufficient balance. Please top-up.");
        } else {
            book.copiesAvailable--;
            user.balance -= book.cost;
            user.points += book.cost;
            user.inventory.add(bookName);
            System.out.println("You rented \"" + bookName + "\" successfully!");
            System.out.println("You earned " + book.cost + " points. Total points: " + user.points);
        }
    }

    public static void topUpBalance(String username) {
        Scanner scanner = new Scanner(System.in);
        Account user = accountLibrary.get(username);

        System.out.println("Enter the amount to top-up:");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            user.balance += amount;
            System.out.println("Balance updated. Current balance: $" + user.balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public static void viewInventory(String username) {
        Account user = accountLibrary.get(username);

        System.out.println("Your rented books:");
        for (String book : user.inventory) {
            System.out.println("- " + book);
        }
        System.out.println("Total points: " + user.points);
    }

    public static void adminLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter admin username:");
        String username = scanner.nextLine();
        System.out.println("Enter admin password:");
        String password = scanner.nextLine();

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Admin login successful!");
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Book Rental System ===");
            System.out.println("1. Admin login");
            System.out.println("2. Create an account");
            System.out.println("3. Login");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> createAccount();
                case 3 -> login();
                case 4 -> {
                    System.out.println("Thank you for using the Book Rental System.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
