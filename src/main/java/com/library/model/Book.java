package com.library.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a book in the library system
 */
public class Book {
    private String isbn;
    private String title;
    private String author;
    private String category;
    private boolean isAvailable;
    private LocalDate publicationDate;
    private int borrowCount;

    // Constructor
    public Book(String isbn, String title, String author, String category, LocalDate publicationDate) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationDate = publicationDate;
        this.isAvailable = true;
        this.borrowCount = 0;
    }

    // Getters and Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void incrementBorrowCount() {
        this.borrowCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return String.format("Book{isbn='%s', title='%s', author='%s', category='%s', available=%s, borrowCount=%d}",
                isbn, title, author, category, isAvailable, borrowCount);
    }
}