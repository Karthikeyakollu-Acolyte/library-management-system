import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LibraryApp {
    private static final LibraryService LIBRARY_SERVICE = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isTestMode = false;
    private static final Logger LOGGER = Logger.getLogger(LibraryApp.class.getName());
    
    private LibraryApp() {
        // Private constructor to hide implicit public one
    }
    
    public static void main(String[] args) {
        initializeData();
        showMenu();
    }
    
    // Package-private method for testing
    static void setScanner(Scanner newScanner) {
        scanner = newScanner;
        isTestMode = true;
    }
    
    private static void initializeData() {
        // Add books
        LIBRARY_SERVICE.addBook(new Book("B001", "Java Programming", "John Doe"));
        LIBRARY_SERVICE.addBook(new Book("B002", "Data Structures", "Jane Smith"));
        LIBRARY_SERVICE.addBook(new Book("B003", "Spring Boot Guide", "Michael Johnson"));
        LIBRARY_SERVICE.addBook(new Book("B004", "Database Design", "Sarah Wilson"));
        LIBRARY_SERVICE.addBook(new Book("B005", "Web Development", "David Brown"));
        LIBRARY_SERVICE.addBook(new Book("B006", "Python Basics", "Emily Davis"));
        LIBRARY_SERVICE.addBook(new Book("B007", "Machine Learning", "Robert Miller"));
        LIBRARY_SERVICE.addBook(new Book("B008", "Clean Code", "Jennifer Garcia"));
        
        // Add members
        LIBRARY_SERVICE.addMember(new Member("M001", "Alice Johnson", "alice@email.com"));
        LIBRARY_SERVICE.addMember(new Member("M002", "Bob Wilson", "bob@email.com"));
        LIBRARY_SERVICE.addMember(new Member("M003", "Charlie Brown", "charlie@email.com"));
        LIBRARY_SERVICE.addMember(new Member("M004", "Diana Smith", "diana@email.com"));
        LIBRARY_SERVICE.addMember(new Member("M005", "Edward Davis", "edward@email.com"));
        LIBRARY_SERVICE.addMember(new Member("M006", "Fiona Miller", "fiona@email.com"));
        LIBRARY_SERVICE.addMember(new Member("M007", "George Wilson", "george@email.com"));
        
        // Create some transactions
        LIBRARY_SERVICE.borrowBook("B001", "M001");
        LIBRARY_SERVICE.borrowBook("B003", "M002");
        LIBRARY_SERVICE.borrowBook("B005", "M003");
        LIBRARY_SERVICE.borrowBook("B007", "M004");
        LIBRARY_SERVICE.returnBook("B001");
        LIBRARY_SERVICE.borrowBook("B002", "M005");
        LIBRARY_SERVICE.borrowBook("B004", "M006");
    }
    
    private static void showMenu() {
        boolean running = true;
        
        while (running) {
            printMenuOptions();
            
            int choice = getIntInput();
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addMember();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    viewAllBooks();
                    break;
                case 6:
                    viewAllMembers();
                    break;
                case 7:
                    viewAllTransactions();
                    break;
                case 8:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        if (isTestMode) {
            scanner.close();
        }
    }
    
    private static void printMenuOptions() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Add Book");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. View All Books");
        System.out.println("6. View All Members");
        System.out.println("7. View All Transactions");
        System.out.println("8. Exit");
        System.out.print("Choose an option: ");
    }
    
    private static void addBook() {
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        
        LIBRARY_SERVICE.addBook(new Book(id, title, author));
        System.out.println("Book added successfully!");
    }
    
    private static void addMember() {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Member Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Member Email: ");
        String email = scanner.nextLine();
        
        LIBRARY_SERVICE.addMember(new Member(id, name, email));
        System.out.println("Member added successfully!");
    }
    
    private static void borrowBook() {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        
        boolean success = LIBRARY_SERVICE.borrowBook(bookId, memberId);
        if (success) {
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Failed to borrow book. Check if book exists and is available.");
        }
    }
    
    private static void returnBook() {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();
        
        boolean success = LIBRARY_SERVICE.returnBook(bookId);
        if (success) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book. Check if book is currently borrowed.");
        }
    }
    
    private static void viewAllBooks() {
        System.out.println("\n=== All Books ===");
        for (Book book : LIBRARY_SERVICE.getAllBooks()) {
            System.out.printf("%s - %s by %s (Available: %s)%n", 
                book.getId(), book.getTitle(), book.getAuthor(), book.isAvailable());
        }
    }
    
    private static void viewAllMembers() {
        System.out.println("\n=== All Members ===");
        for (Member member : LIBRARY_SERVICE.getAllMembers()) {
            System.out.printf("%s - %s (%s)%n", 
                member.getId(), member.getName(), member.getEmail());
        }
    }
    
    private static void viewAllTransactions() {
        System.out.println("\n=== All Transactions ===");
        for (Transaction transaction : LIBRARY_SERVICE.getAllTransactions()) {
            System.out.printf("%s - Book: %s, Member: %s, Borrowed: %s, Returned: %s%n",
                transaction.getId(), transaction.getBookId(), transaction.getMemberId(),
                transaction.getBorrowDate(), transaction.isReturned());
        }
    }
    
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid number input", e);
            return -1;
        }
    }
}