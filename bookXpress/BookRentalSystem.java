import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BookRentalSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== bookXpress: Book Rental System ===");
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
                    System.out.println("Thank you for using the bookXpress: Book Rental System.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void adminLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter admin username:");
        String username = scanner.nextLine();
        System.out.println("Enter admin password:");
        String password = scanner.nextLine();

        // Hardcoded admin credentials
        if (username.equals("admin") && password.equals("password123")) {
            System.out.println("Admin login successful!");
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add a new book");
            System.out.println("2. View all books");
            System.out.println("3. Remove a book");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addNewBook();
                case 2 -> displayAvailableBooks();
                case 3 -> removeBook();
                case 4 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void addNewBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter book title:");
        String title = scanner.nextLine();
        System.out.println("Enter number of copies:");
        int copies = scanner.nextInt();
        System.out.println("Enter cost per rental:");
        double cost = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Is this a Fiction book or Non-Fiction book?");
        String type = scanner.nextLine();
        System.out.println(type.equalsIgnoreCase("Fiction") ? "Enter genre:" : "Enter subject:");
        String genreOrSubject = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO books (title, copies_available, cost, type, genre_or_subject) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, title);
            pstmt.setInt(2, copies);
            pstmt.setDouble(3, cost);
            pstmt.setString(4, type);
            pstmt.setString(5, genreOrSubject);

            pstmt.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayAvailableBooks() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String query = "SELECT * FROM books";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\nAvailable Books:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " +
                                   rs.getString("title") + " - Copies: " +
                                   rs.getInt("copies_available") + ", Cost: $" +
                                   rs.getDouble("cost") + ", Type: " +
                                   rs.getString("type") + ", " +
                                   (rs.getString("type").equalsIgnoreCase("Fiction") ?
                                    "Genre: " : "Subject: ") + rs.getString("genre_or_subject"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeBook() {
        Scanner scanner = new Scanner(System.in);

        displayAvailableBooks();

        System.out.println("Enter the book ID to remove:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement("SELECT title FROM books WHERE id = ?")) {

            pstmtSelect.setInt(1, bookId);
            ResultSet rs = pstmtSelect.executeQuery();

            if (rs.next()) {
                String bookTitle = rs.getString("title");
                System.out.println("Do you want to delete \"" + bookTitle + "\"? (yes/no)");
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("yes")) {
                    try (PreparedStatement pstmtDelete = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {
                        pstmtDelete.setInt(1, bookId);
                        int rowsAffected = pstmtDelete.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Book \"" + bookTitle + "\" removed successfully.");
                        } else {
                            System.out.println("Failed to remove the book.");
                        }
                    }
                } else {
                    System.out.println("Book removal canceled.");
                }
            } else {
                System.out.println("Book ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO users (username, password, balance) VALUES (?, ?, 0.0)")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            pstmt.executeUpdate();
            System.out.println("Account created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT * FROM users WHERE username = ? AND password = ?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful!");
                Account account = new Account(rs.getString("username"), rs.getDouble("balance"));
                userMenu(account);
            } else {
                System.out.println("Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void userMenu(Account account) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            account.displayAccountInfo(); // Display account info at the top of the menu
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View available books");
            System.out.println("2. Rent a book");
            System.out.println("3. Top up balance");
            System.out.println("4. View inventory");
            System.out.println("5. Return a book");
            System.out.println("6. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> displayAvailableBooks();
                case 2 -> rentBook(account);
                case 3 -> topUpBalance(account);  // Top up balance method
                case 4 -> viewInventory(account);  // View inventory method
                case 5 -> returnBook(account);  // Return book method
                case 6 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void topUpBalance(Account account) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the amount to top up:");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(
                         "UPDATE users SET balance = balance + ? WHERE username = ?")) {

                pstmt.setDouble(1, amount);
                pstmt.setString(2, account.username);

                pstmt.executeUpdate();
                account.balance += amount; // Update the balance in the Account object
                System.out.println("Balance topped up successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    // Declare this map to store the item number and corresponding rental ID
private static Map<Integer, Integer> rentalIdToItemMap = new HashMap<>();

public static void viewInventory(Account account) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT rentals.id, books.title, rentals.rent_date " +
                         "FROM rentals " +
                         "INNER JOIN books ON rentals.book_id = books.id " +
                         "WHERE rentals.username = ?")) {

        pstmt.setString(1, account.username);
        ResultSet rs = pstmt.executeQuery();

        System.out.println("\nYour Rented Books:");
        boolean hasBooks = false;
        int itemNumber = 1;  // Start item number from 1

        // Clear the map before populating it again
        rentalIdToItemMap.clear();

        while (rs.next()) {
            hasBooks = true;
            int rentalId = rs.getInt("id");  // Rental ID
            String bookTitle = rs.getString("title");
            String rentDate = rs.getString("rent_date");

            // Display item number, book title, and rent date
            System.out.println(itemNumber + ". " + bookTitle + " - Rented on: " + rentDate);

            // Map item number to rental ID
            rentalIdToItemMap.put(itemNumber, rentalId);

            itemNumber++;  // Increment item number for the next book
        }

        if (!hasBooks) {
            System.out.println("You haven't rented any books yet.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void rentBook(Account account) {
        Scanner scanner = new Scanner(System.in);
    
        // Display available books
        displayAvailableBooks();
    
        System.out.println("Enter the book ID you want to rent:");
        int bookId = scanner.nextInt();
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement bookStmt = conn.prepareStatement("SELECT * FROM books WHERE id = ?");
             PreparedStatement updateBookStmt = conn.prepareStatement("UPDATE books SET copies_available = copies_available - 1 WHERE id = ?");
             PreparedStatement updateUserStmt = conn.prepareStatement("UPDATE users SET balance = balance - ? WHERE username = ?");
             PreparedStatement insertRentalStmt = conn.prepareStatement("INSERT INTO rentals (user_id, book_id, username) VALUES (?, ?, ?)")) {
    
            // Fetch the selected book
            bookStmt.setInt(1, bookId);
            ResultSet bookRs = bookStmt.executeQuery();
    
            if (bookRs.next()) {
                String bookTitle = bookRs.getString("title");
                int copiesAvailable = bookRs.getInt("copies_available");
                double cost = bookRs.getDouble("cost");
    
                // Check if the book is available
                if (copiesAvailable > 0) {
                    // Check if the user has enough balance
                    if (account.balance >= cost) {
                        // Confirm rental
                        System.out.println("Do you want to rent \"" + bookTitle + "\" for $" + cost + "? (yes/no)");
                        scanner.nextLine(); // Consume newline
                        String confirmation = scanner.nextLine();
    
                        if (confirmation.equalsIgnoreCase("yes")) {
                            // Update book copies
                            updateBookStmt.setInt(1, bookId);
                            updateBookStmt.executeUpdate();
    
                            // Deduct balance from user
                            updateUserStmt.setDouble(1, cost);
                            updateUserStmt.setString(2, account.username);
                            updateUserStmt.executeUpdate();
    
                            // Insert rental record, including username
                            insertRentalStmt.setInt(1, getUserId(account.username));  // User ID (if needed for other purposes)
                            insertRentalStmt.setInt(2, bookId);
                            insertRentalStmt.setString(3, account.username);  // Insert username directly
                            insertRentalStmt.executeUpdate();
    
                            // Update local account balance
                            account.balance -= cost;
    
                            System.out.println("You have successfully rented \"" + bookTitle + "\"!");
                        } else {
                            System.out.println("Rental canceled.");
                        }
                    } else {
                        System.out.println("Insufficient balance. Please top up your account.");
                    }
                } else {
                    System.out.println("Sorry, \"" + bookTitle + "\" is currently unavailable.");
                }
            } else {
                System.out.println("Book ID not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void returnBook(Account account) {
        Scanner scanner = new Scanner(System.in);
    
        // Display user's rented books with item numbers
        viewInventory(account);
    
        System.out.println("Enter the item number of the book you want to return:");
        int itemNumber = scanner.nextInt();
    
        // Get the corresponding rental ID from the map
        Integer rentalId = rentalIdToItemMap.get(itemNumber);
    
        if (rentalId != null) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmtCheckRental = conn.prepareStatement(
                         "SELECT rentals.book_id, books.title " +
                                 "FROM rentals " +
                                 "INNER JOIN books ON rentals.book_id = books.id " +
                                 "WHERE rentals.id = ? AND rentals.username = ?")) {
    
                pstmtCheckRental.setInt(1, rentalId);  // Use rental ID
                pstmtCheckRental.setString(2, account.username);
                ResultSet rs = pstmtCheckRental.executeQuery();
    
                if (rs.next()) {
                    // Proceed with returning the book
                    String bookTitle = rs.getString("title");  // Fetch the book title
                    System.out.println("Are you sure you want to return \"" + bookTitle + "\"? (yes/no)");
                    scanner.nextLine();  // Consume newline
                    String confirmation = scanner.nextLine();
    
                    if (confirmation.equalsIgnoreCase("yes")) {
                        // Remove rental record and update book availability
                        try (PreparedStatement pstmtDeleteRental = conn.prepareStatement(
                                "DELETE FROM rentals WHERE id = ?")) {
                            pstmtDeleteRental.setInt(1, rentalId);
                            pstmtDeleteRental.executeUpdate();
    
                            // Update the book's available copies in the books table
                            try (PreparedStatement pstmtUpdateBook = conn.prepareStatement(
                                    "UPDATE books SET copies_available = copies_available + 1 WHERE id = ?")) {
                                pstmtUpdateBook.setInt(1, rs.getInt("book_id"));
                                pstmtUpdateBook.executeUpdate();
    
                                System.out.println("You have successfully returned \"" + bookTitle + "\"!");
                            }
                        }
                    } else {
                        System.out.println("Return canceled.");
                    }
                } else {
                    System.out.println("This rental record doesn't exist or you haven't rented this book.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid item number.");
        }
    }
    
    // Helper method to get the book title by its ID
    public static String getBookTitleById(int bookId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT title FROM books WHERE id = ?")) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("title");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to get the user ID by username
    public static int getUserId(String username) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("User not found.");
            }
        }
    }
}
