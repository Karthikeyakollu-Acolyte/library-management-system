package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.model.Transaction.TransactionType;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Main service class for library operations
 */
public class LibraryService {
    private Map<String, Book> books; // ISBN -> Book
    private Map<String, Member> members; // MemberID -> Member
    private List<Transaction> transactions;
    private int transactionCounter;

    public LibraryService() {
        this.books = new HashMap<>();
        this.members = new HashMap<>();
        this.transactions = new ArrayList<>();
        this.transactionCounter = 1;
        initializeSampleData();
    }

    /**
     * Add a new book to the library
     */
    public void addBook(Book book) {
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("Book and ISBN cannot be null or empty");
        }
        books.put(book.getIsbn(), book);
        System.out.println("Book added successfully: " + book.getTitle());
    }

    /**
     * Add a new member to the library
     */
    public void addMember(Member member) {
        if (member == null || member.getMemberId() == null || member.getMemberId().trim().isEmpty()) {
            throw new IllegalArgumentException("Member and Member ID cannot be null or empty");
        }
        members.put(member.getMemberId(), member);
        System.out.println("Member added successfully: " + member.getName());
    }

    /**
     * Borrow a book
     */
    public boolean borrowBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        Book book = books.get(isbn);

        if (member == null) {
            System.out.println("Member not found: " + memberId);
            return false;
        }

        if (book == null) {
            System.out.println("Book not found: " + isbn);
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is not available: " + book.getTitle());
            return false;
        }

        if (!member.canBorrowMoreBooks()) {
            System.out.println("Member has reached maximum book limit: " + member.getName());
            return false;
        }

        // Process the borrowing
        book.setAvailable(false);
        book.incrementBorrowCount();
        member.addBorrowedBook(isbn);

        // Create transaction record
        String transactionId = "T" + String.format("%04d", transactionCounter++);
        Transaction transaction = new Transaction(transactionId, memberId, isbn, TransactionType.BORROW);
        transactions.add(transaction);

        System.out.println("Book borrowed successfully: " + book.getTitle() + " by " + member.getName());
        return true;
    }

    /**
     * Return a book
     */
    public boolean returnBook(String memberId, String isbn) {
        Member member = members.get(memberId);
        Book book = books.get(isbn);

        if (member == null) {
            System.out.println("Member not found: " + memberId);
            return false;
        }

        if (book == null) {
            System.out.println("Book not found: " + isbn);
            return false;
        }

        if (!member.getBorrowedBooks().contains(isbn)) {
            System.out.println("Member has not borrowed this book: " + book.getTitle());
            return false;
        }

        // Process the return
        book.setAvailable(true);
        member.removeBorrowedBook(isbn);

        // Update transaction record
        Transaction borrowTransaction = transactions.stream()
                .filter(t -> t.getMemberId().equals(memberId) && 
                           t.getIsbn().equals(isbn) && 
                           t.getType() == TransactionType.BORROW &&
                           t.getReturnDate() == null)
                .findFirst()
                .orElse(null);

        if (borrowTransaction != null) {
            borrowTransaction.setReturnDate(LocalDate.now());
        }

        // Create return transaction
        String transactionId = "T" + String.format("%04d", transactionCounter++);
        Transaction returnTransaction = new Transaction(transactionId, memberId, isbn, TransactionType.RETURN);
        returnTransaction.setReturnDate(LocalDate.now());
        transactions.add(returnTransaction);

        System.out.println("Book returned successfully: " + book.getTitle() + " by " + member.getName());
        return true;
    }

    /**
     * Search books by title, author, or category
     */
    public List<Book> searchBooks(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>(books.values());
        }

        String term = searchTerm.toLowerCase();
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(term) ||
                               book.getAuthor().toLowerCase().contains(term) ||
                               book.getCategory().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }

    /**
     * Get all available books
     */
    public List<Book> getAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Get overdue books
     */
    public List<Transaction> getOverdueBooks() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.BORROW && t.isOverdue())
                .collect(Collectors.toList());
    }

    /**
     * Initialize sample data for testing
     */
    private void initializeSampleData() {
        // Sample books
        addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch", "Programming", LocalDate.of(2017, 12, 27)));
        addBook(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman", "Programming", LocalDate.of(2004, 10, 25)));
        addBook(new Book("978-0321356680", "Effective C++", "Scott Meyers", "Programming", LocalDate.of(2005, 5, 22)));
        addBook(new Book("978-0132350884", "Clean Code", "Robert Martin", "Programming", LocalDate.of(2008, 8, 1)));
        addBook(new Book("978-0201616224", "The Pragmatic Programmer", "Andrew Hunt", "Programming", LocalDate.of(1999, 10, 20)));

        // Sample members
        addMember(new Member("M001", "John Doe", "john.doe@email.com", "555-1234"));
        addMember(new Member("M002", "Jane Smith", "jane.smith@email.com", "555-5678"));
        addMember(new Member("M003", "Bob Johnson", "bob.johnson@email.com", "555-9012"));

        System.out.println("Sample data initialized successfully!");
    }

    // Getters for accessing data (mainly for testing and reports)
    public Map<String, Book> getBooks() {
        return new HashMap<>(books);
    }

    public Map<String, Member> getMembers() {
        return new HashMap<>(members);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}