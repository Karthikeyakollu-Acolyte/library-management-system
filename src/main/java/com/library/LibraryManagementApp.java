package com.library;

import com.library.model.Book;
import com.library.model.Member;
import com.library.service.LibraryService;
import com.library.service.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main application class for Library Management System
 */
public class LibraryManagementApp {
    private LibraryService libraryService;
    private ReportService reportService;
    private Scanner scanner;

    public LibraryManagementApp() {
        this.libraryService = new LibraryService();
        this.reportService = new ReportService(libraryService);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LibraryManagementApp app = new LibraryManagementApp();
        app.run();
    }

    public void run() {
        System.out.println("Welcome to Library Management System!");
        System.out.println("====================================");
        
        // Demo with sample data
        runDemo();
        
        boolean running = true;
        while (running) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    addNewMember();
                    break;
                case 3:
                    borrowBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    searchBooks();
                    break;
                case 6:
                    showAvailableBooks();
                    break;
                case 7:
                    showReports();
                    break;
                case 8:
                    running = false;
                    System.out.println("Thank you for using Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
        System.out.println("1. Add New Book");
        System.out.println("2. Add New Member");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Books");
        System.out.println("6. Show Available Books");
        System.out.println("7. Generate Reports");
        System.out.println("8. Exit");
    }

    private void runDemo() {
        System.out.println("\n=== RUNNING DEMO WITH SAMPLE DATA ===");
        
        // Demo borrowing books
        System.out.println("\nDemo: John Doe borrowing 'Effective Java'");
        libraryService.borrowBook("M001", "978-0134685991");
        
        System.out.println("\nDemo: Jane Smith borrowing 'Clean Code'");
        libraryService.borrowBook("M002", "978-0132350884");
        
        System.out.println("\nDemo: Bob Johnson borrowing 'Head First Design Patterns'");
        libraryService.borrowBook("M003", "978-0596009205");
        
        // Demo returning a book
        System.out.println("\nDemo: John Doe returning 'Effective Java'");
        libraryService.returnBook("M001", "978-0134685991");
        
        System.out.println("\n=== DEMO COMPLETED ===");
    }

    private void addNewBook() {
        System.out.println("\n=== ADD NEW BOOK ===");
        
        String isbn = getStringInput("Enter ISBN: ");
        String title = getStringInput("Enter Title: ");
        String author = getStringInput("Enter Author: ");
        String category = getStringInput("Enter Category: ");
        
        System.out.print("Enter Publication Year (YYYY): ");
        int year = getIntInput("");
        
        Book book = new Book(isbn, title, author, category, LocalDate.of(year, 1, 1));
        libraryService.addBook(book);
    }

    private void addNewMember() {
        System.out.println("\n=== ADD NEW MEMBER ===");
        
        String memberId = getStringInput("Enter Member ID: ");
        String name = getStringInput("Enter Name: ");
        String email = getStringInput("Enter Email: ");
        String phone = getStringInput("Enter Phone: ");
        
        Member member = new Member(memberId, name, email, phone);
        libraryService.addMember(member);
    }

    private void borrowBook() {
        System.out.println("\n=== BORROW BOOK ===");
        
        String memberId = getStringInput("Enter Member ID: ");
        String isbn = getStringInput("Enter Book ISBN: ");
        
        libraryService.borrowBook(memberId, isbn);
    }

    private void returnBook() {
        System.out.println("\n=== RETURN BOOK ===");
        
        String memberId = getStringInput("Enter Member ID: ");
        String isbn = getStringInput("Enter Book ISBN: ");
        
        libraryService.returnBook(memberId, isbn);
    }

    private void searchBooks() {
        System.out.println("\n=== SEARCH BOOKS ===");
        
        String searchTerm = getStringInput("Enter search term (title, author, or category): ");
        List<Book> results = libraryService.searchBooks(searchTerm);
        
        if (results.isEmpty()) {
            System.out.println("No books found matching your search.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : results) {
                String status = book.isAvailable() ? "Available" : "Borrowed";
                System.out.printf("ISBN: %s | %s by %s [%s] - %s%n", 
                        book.getIsbn(), book.getTitle(), book.getAuthor(), book.getCategory(), status);
            }
        }
    }

    private void showAvailableBooks() {
        System.out.println("\n=== AVAILABLE BOOKS ===");
        
        List<Book> availableBooks = libraryService.getAvailableBooks();
        
        if (availableBooks.isEmpty()) {
            System.out.println("No books are currently available.");
        } else {
            System.out.println("Available Books:");
            for (Book book : availableBooks) {
                System.out.printf("ISBN: %s | %s by %s [%s]%n", 
                        book.getIsbn(), book.getTitle(), book.getAuthor(), book.getCategory());
            }
        }
    }

    private void showReports() {
        System.out.println("\n=== REPORTS MENU ===");
        System.out.println("1. Popular Books Report");
        System.out.println("2. Overdue Books Report");
        System.out.println("3. Library Statistics");
        System.out.println("4. Member Activity Report");
        System.out.println("5. Books by Category Report");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                reportService.generatePopularBooksReport();
                break;
            case 2:
                reportService.generateOverdueBooksReport();
                break;
            case 3:
                reportService.generateLibraryStatsReport();
                break;
            case 4:
                reportService.generateMemberActivityReport();
                break;
            case 5:
                reportService.generateBooksByCategoryReport();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}