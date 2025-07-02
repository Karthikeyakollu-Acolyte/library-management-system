package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for generating library reports
 */
public class ReportService {
    private LibraryService libraryService;

    public ReportService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Generate report of most popular books (by borrow count)
     */
    public void generatePopularBooksReport() {
        System.out.println("\n=== POPULAR BOOKS REPORT ===");
        System.out.println("Books sorted by borrow count:");
        
        List<Book> popularBooks = libraryService.getBooks().values().stream()
                .sorted((b1, b2) -> Integer.compare(b2.getBorrowCount(), b1.getBorrowCount()))
                .limit(10) // Top 10
                .collect(Collectors.toList());

        if (popularBooks.isEmpty()) {
            System.out.println("No books have been borrowed yet.");
            return;
        }

        for (int i = 0; i < popularBooks.size(); i++) {
            Book book = popularBooks.get(i);
            System.out.printf("%d. %s by %s (Borrowed %d times)%n", 
                    i + 1, book.getTitle(), book.getAuthor(), book.getBorrowCount());
        }
    }

    /**
     * Generate report of overdue books
     */
    public void generateOverdueBooksReport() {
        System.out.println("\n=== OVERDUE BOOKS REPORT ===");
        
        List<Transaction> overdueTransactions = libraryService.getOverdueBooks();
        
        if (overdueTransactions.isEmpty()) {
            System.out.println("No overdue books found.");
            return;
        }

        System.out.println("Books that are overdue:");
        for (Transaction transaction : overdueTransactions) {
            Book book = libraryService.getBooks().get(transaction.getIsbn());
            Member member = libraryService.getMembers().get(transaction.getMemberId());
            
            System.out.printf("- %s by %s%n", book.getTitle(), book.getAuthor());
            System.out.printf("  Borrowed by: %s (ID: %s)%n", member.getName(), member.getMemberId());
            System.out.printf("  Due date: %s (%d days overdue)%n", 
                    transaction.getDueDate(), transaction.getDaysOverdue());
            System.out.println();
        }
    }

    /**
     * Generate library statistics report
     */
    public void generateLibraryStatsReport() {
        System.out.println("\n=== LIBRARY STATISTICS ===");
        
        Map<String, Book> books = libraryService.getBooks();
        Map<String, Member> members = libraryService.getMembers();
        List<Transaction> transactions = libraryService.getTransactions();
        
        long availableBooks = books.values().stream().filter(Book::isAvailable).count();
        long borrowedBooks = books.size() - availableBooks;
        
        int totalBorrowedByMembers = members.values().stream()
                .mapToInt(Member::getBorrowedBooksCount)
                .sum();
        
        long borrowTransactions = transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.BORROW)
                .count();
        
        long returnTransactions = transactions.stream()
                .filter(t -> t.getType() == Transaction.TransactionType.RETURN)
                .count();
        
        System.out.println("Total books in library: " + books.size());
        System.out.println("Available books: " + availableBooks);
        System.out.println("Currently borrowed books: " + borrowedBooks);
        System.out.println("Total members: " + members.size());
        System.out.println("Total borrow transactions: " + borrowTransactions);
        System.out.println("Total return transactions: " + returnTransactions);
        System.out.println("Books currently with members: " + totalBorrowedByMembers);
    }

    /**
     * Generate member activity report
     */
    public void generateMemberActivityReport() {
        System.out.println("\n=== MEMBER ACTIVITY REPORT ===");
        
        Map<String, Member> members = libraryService.getMembers();
        
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        System.out.println("Member borrowing activity:");
        for (Member member : members.values()) {
            System.out.printf("- %s (ID: %s)%n", member.getName(), member.getMemberId());
            System.out.printf("  Currently borrowed: %d/%d books%n", 
                    member.getBorrowedBooksCount(), member.getMaxBooksAllowed());
            
            if (member.getBorrowedBooksCount() > 0) {
                System.out.print("  Books: ");
                for (String isbn : member.getBorrowedBooks()) {
                    Book book = libraryService.getBooks().get(isbn);
                    System.out.print(book.getTitle() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * Generate books by category report
     */
    public void generateBooksByCategoryReport() {
        System.out.println("\n=== BOOKS BY CATEGORY REPORT ===");
        
        Map<String, List<Book>> booksByCategory = libraryService.getBooks().values().stream()
                .collect(Collectors.groupingBy(Book::getCategory));
        
        for (Map.Entry<String, List<Book>> entry : booksByCategory.entrySet()) {
            System.out.println("\nCategory: " + entry.getKey());
            System.out.println("Books count: " + entry.getValue().size());
            
            for (Book book : entry.getValue()) {
                String status = book.isAvailable() ? "Available" : "Borrowed";
                System.out.printf("  - %s by %s [%s]%n", 
                        book.getTitle(), book.getAuthor(), status);
            }
        }
    }
}