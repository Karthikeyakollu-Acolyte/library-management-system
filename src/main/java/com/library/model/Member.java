package com.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a library member
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;
    private List<String> borrowedBooks; // List of ISBN numbers
    private int maxBooksAllowed;

    // Constructor
    public Member(String memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = LocalDate.now();
        this.borrowedBooks = new ArrayList<>();
        this.maxBooksAllowed = 3; // Default limit
    }

    // Getters and Setters
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public List<String> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks); // Return copy to maintain encapsulation
    }

    public void addBorrowedBook(String isbn) {
        borrowedBooks.add(isbn);
    }

    public void removeBorrowedBook(String isbn) {
        borrowedBooks.remove(isbn);
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public boolean canBorrowMoreBooks() {
        return borrowedBooks.size() < maxBooksAllowed;
    }

    public int getBorrowedBooksCount() {
        return borrowedBooks.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    @Override
    public String toString() {
        return String.format("Member{memberId='%s', name='%s', email='%s', borrowedBooks=%d/%d}",
                memberId, name, email, borrowedBooks.size(), maxBooksAllowed);
    }
}