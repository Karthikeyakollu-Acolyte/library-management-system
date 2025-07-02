package com.library.model;

import java.time.LocalDate;

/**
 * Represents a book borrowing/returning transaction
 */
public class Transaction {
    private String transactionId;
    private String memberId;
    private String isbn;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private TransactionType type;

    public enum TransactionType {
        BORROW, RETURN
    }

    // Constructor for borrowing
    public Transaction(String transactionId, String memberId, String isbn, TransactionType type) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.type = type;
        this.borrowDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusWeeks(2); // 2 weeks loan period
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }

    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return LocalDate.now().toEpochDay() - dueDate.toEpochDay();
    }

    @Override
    public String toString() {
        return String.format("Transaction{id='%s', member='%s', isbn='%s', type=%s, borrowDate=%s, dueDate=%s, returnDate=%s}",
                transactionId, memberId, isbn, type, borrowDate, dueDate, returnDate);
    }
}