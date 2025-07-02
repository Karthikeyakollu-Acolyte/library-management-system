package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LibraryService class
 */
@DisplayName("Library Service Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LibraryServiceTest {
    
    private LibraryService libraryService;
    private Book testBook;
    private Member testMember;
    
    @BeforeAll
    static void setUpClass() {
        System.out.println("\n🚀 Starting LibraryService Test Suite");
        System.out.println("=====================================");
    }
    
    @AfterAll
    static void tearDownClass() {
        System.out.println("\n✅ LibraryService Test Suite Completed");
        System.out.println("======================================");
    }
    
    @BeforeEach
    void setUp() {
        System.out.println("\n⚙️ Setting up test data...");
        libraryService = new LibraryService();
        testBook = new Book("978-1111111111", "Test Book", "Test Author", "Test Category", LocalDate.now());
        testMember = new Member("TEST001", "Test User", "test@email.com", "555-0000");
        System.out.println("📚 Test book created: " + testBook.getTitle());
        System.out.println("👤 Test member created: " + testMember.getName());
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("🧹 Test cleanup completed.\n");
    }
    
    @Nested
    @DisplayName("Book Management Tests")
    @Order(1)
    class BookManagementTests {
        
        @Test
        @DisplayName("Should add book to library successfully")
        @Order(1)
        void testAddBook() {
            System.out.println("🧪 Running: Should add book to library successfully");
            
            int initialBookCount = libraryService.getBooks().size();
            System.out.println("📊 Initial book count: " + initialBookCount);
            
            libraryService.addBook(testBook);
            
            assertTrue(libraryService.getBooks().containsKey("978-1111111111"));
            assertEquals(testBook, libraryService.getBooks().get("978-1111111111"));
            assertEquals(initialBookCount + 1, libraryService.getBooks().size());
            
            System.out.println("📊 Final book count: " + libraryService.getBooks().size());
            System.out.println("✅ Book added successfully to library");
        }
        
        @Test
        @DisplayName("Should reject book with null ISBN")
        @Order(2)
        void testAddBookWithNullISBN() {
            System.out.println("🧪 Running: Should reject book with null ISBN");
            
            Book invalidBook = new Book(null, "Title", "Author", "Category", LocalDate.now());
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                libraryService.addBook(invalidBook);
            });
            
            System.out.println("⚠️ Expected exception caught: " + exception.getMessage());
            System.out.println("✅ Null ISBN validation working correctly");
        }
        
        @Test
        @DisplayName("Should reject book with empty ISBN")
        @Order(3)
        void testAddBookWithEmptyISBN() {
            System.out.println("🧪 Running: Should reject book with empty ISBN");
            
            Book invalidBook = new Book("   ", "Title", "Author", "Category", LocalDate.now());
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                libraryService.addBook(invalidBook);
            });
            
            System.out.println("⚠️ Expected exception caught: " + exception.getMessage());
            System.out.println("✅ Empty ISBN validation working correctly");
        }
    }
    
    @Nested
    @DisplayName("Member Management Tests")
    @Order(2)
    class MemberManagementTests {
        
        @Test
        @DisplayName("Should add member to library successfully")
        @Order(1)
        void testAddMember() {
            System.out.println("🧪 Running: Should add member to library successfully");
            
            int initialMemberCount = libraryService.getMembers().size();
            System.out.println("📊 Initial member count: " + initialMemberCount);
            
            libraryService.addMember(testMember);
            
            assertTrue(libraryService.getMembers().containsKey("TEST001"));
            assertEquals(testMember, libraryService.getMembers().get("TEST001"));
            assertEquals(initialMemberCount + 1, libraryService.getMembers().size());
            
            System.out.println("📊 Final member count: " + libraryService.getMembers().size());
            System.out.println("✅ Member added successfully to library");
        }
        
        @Test
        @DisplayName("Should reject member with null ID")
        @Order(2)
        void testAddMemberWithNullId() {
            System.out.println("🧪 Running: Should reject member with null ID");
            
            Member invalidMember = new Member(null, "Name", "email", "phone");
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                libraryService.addMember(invalidMember);
            });
            
            System.out.println("⚠️ Expected exception caught: " + exception.getMessage());
            System.out.println("✅ Null member ID validation working correctly");
        }
    }
    
    @Nested
    @DisplayName("Book Borrowing Tests")
    @Order(3)
    class BookBorrowingTests {
        
        @Test
        @DisplayName("Should borrow book successfully when all conditions are met")
        @Order(1)
        void testBorrowBookSuccess() {
            System.out.println("🧪 Running: Should borrow book successfully when all conditions are met");
            
            libraryService.addBook(testBook);
            libraryService.addMember(testMember);
            
            System.out.println("📚 Book available before borrowing: " + testBook.isAvailable());
            System.out.println("📊 Member's borrowed books before: " + testMember.getBorrowedBooksCount());
            
            boolean result = libraryService.borrowBook("TEST001", "978-1111111111");
            
            assertTrue(result, "Borrowing should succeed");
            assertFalse(testBook.isAvailable(), "Book should be marked as unavailable");
            assertEquals(1, testBook.getBorrowCount(), "Book borrow count should increment");
            assertTrue(testMember.getBorrowedBooks().contains("978-1111111111"), "Member should have book in borrowed list");
            assertEquals(1, testMember.getBorrowedBooksCount(), "Member's borrowed count should be 1");
            
            System.out.println("📚 Book available after borrowing: " + testBook.isAvailable());
            System.out.println("📊 Member's borrowed books after: " + testMember.getBorrowedBooksCount());
            System.out.println("✅ Book borrowing completed successfully");
        }
        
        @Test
        @DisplayName("Should fail to borrow when member not found")
        @Order(2)
        void testBorrowBookMemberNotFound() {
            System.out.println("🧪 Running: Should fail to borrow when member not found");
            
            libraryService.addBook(testBook);
            
            boolean result = libraryService.borrowBook("NONEXISTENT", "978-1111111111");
            
            assertFalse(result, "Borrowing should fail for non-existent member");
            assertTrue(testBook.isAvailable(), "Book should remain available");
            assertEquals(0, testBook.getBorrowCount(), "Book borrow count should remain 0");
            
            System.out.println("⚠️ Borrowing correctly failed for non-existent member");
            System.out.println("✅ Member validation working correctly");
        }
        
        @Test
        @DisplayName("Should fail to borrow when book not found")
        @Order(3)
        void testBorrowBookNotFound() {
            System.out.println("🧪 Running: Should fail to borrow when book not found");
            
            libraryService.addMember(testMember);
            
            boolean result = libraryService.borrowBook("TEST001", "NONEXISTENT");
            
            assertFalse(result, "Borrowing should fail for non-existent book");
            assertEquals(0, testMember.getBorrowedBooksCount(), "Member's borrowed count should remain 0");
            
            System.out.println("⚠️ Borrowing correctly failed for non-existent book");
            System.out.println("✅ Book validation working correctly");
        }
        
        @Test
        @DisplayName("Should fail to borrow unavailable book")
        @Order(4)
        void testBorrowUnavailableBook() {
            System.out.println("🧪 Running: Should fail to borrow unavailable book");
            
            libraryService.addBook(testBook);
            libraryService.addMember(testMember);
            
            testBook.setAvailable(false); // Make book unavailable
            System.out.println("📚 Book set as unavailable for testing");
            
            boolean result = libraryService.borrowBook("TEST001", "978-1111111111");
            
            assertFalse(result, "Borrowing should fail for unavailable book");
            assertEquals(0, testMember.getBorrowedBooksCount(), "Member's borrowed count should remain 0");
            
            System.out.println("⚠️ Borrowing correctly failed for unavailable book");
            System.out.println("✅ Book availability validation working correctly");
        }
        
        @Test
        @DisplayName("Should fail to borrow when member at borrowing limit")
        @Order(5)
        void testBorrowBookMemberAtLimit() {
            System.out.println("🧪 Running: Should fail to borrow when member at borrowing limit");
            
            libraryService.addBook(testBook);
            libraryService.addMember(testMember);
            
            // Add 3 books to reach limit
            testMember.addBorrowedBook("book1");
            testMember.addBorrowedBook("book2");
            testMember.addBorrowedBook("book3");
            
            System.out.println("📊 Member's borrowed books before attempt: " + testMember.getBorrowedBooksCount() + "/3");
            System.out.println("🚫 Member is now at borrowing limit");
            
            boolean result = libraryService.borrowBook("TEST001", "978-1111111111");
            
            assertFalse(result, "Borrowing should fail when member at limit");
            assertTrue(testBook.isAvailable(), "Book should remain available");
            
            System.out.println("⚠️ Borrowing correctly failed due to member limit");
            System.out.println("✅ Member borrowing limit validation working correctly");
        }
    }
    
    @Nested
    @DisplayName("Book Returning Tests")
    @Order(4)
    class BookReturningTests {
        
        @Test
        @DisplayName("Should return book successfully when borrowed")
        @Order(1)
        void testReturnBookSuccess() {
            System.out.println("🧪 Running: Should return book successfully when borrowed");
            
            libraryService.addBook(testBook);
            libraryService.addMember(testMember);
            
            // First borrow the book
            libraryService.borrowBook("TEST001", "978-1111111111");
            System.out.println("📚 Book borrowed first");
            System.out.println("📊 Book available before return: " + testBook.isAvailable());
            System.out.println("📊 Member's borrowed books before return: " + testMember.getBorrowedBooksCount());
            
            // Then return it
            boolean result = libraryService.returnBook("TEST001", "978-1111111111");
            
            assertTrue(result, "Return should succeed");
            assertTrue(testBook.isAvailable(), "Book should be available again");
            assertFalse(testMember.getBorrowedBooks().contains("978-1111111111"), "Book should be removed from member's list");
            assertEquals(0, testMember.getBorrowedBooksCount(), "Member's borrowed count should be 0");
            
            System.out.println("📚 Book available after return: " + testBook.isAvailable());
            System.out.println("📊 Member's borrowed books after return: " + testMember.getBorrowedBooksCount());
            System.out.println("✅ Book return completed successfully");
        }
        
        @Test
        @DisplayName("Should fail to return book not borrowed by member")
        @Order(2)
        void testReturnBookNotBorrowed() {
            System.out.println("🧪 Running: Should fail to return book not borrowed by member");
            
            libraryService.addBook(testBook);
            libraryService.addMember(testMember);
            
            boolean result = libraryService.returnBook("TEST001", "978-1111111111");
            
            assertFalse(result, "Return should fail for book not borrowed");
            
            System.out.println("⚠️ Return correctly failed for non-borrowed book");
            System.out.println("✅ Book return validation working correctly");
        }
    }
    
    @Nested
    @DisplayName("Search and Query Tests")
    @Order(5)
    class SearchAndQueryTests {
        
        @Test
        @DisplayName("Should search books by various criteria")
        @Order(1)
        void testSearchBooks() {
            System.out.println("🧪 Running: Should search books by various criteria");
            
            libraryService.addBook(testBook);
            System.out.println("📚 Added test book for searching");
            
            // Test search by title
            List<Book> results = libraryService.searchBooks("Test");
            assertEquals(1, results.size(), "Should find 1 book by title");
            assertTrue(results.contains(testBook), "Should contain our test book");
            System.out.println("🔍 Search by title 'Test': Found " + results.size() + " book(s)");
            
            // Test case insensitive search
            results = libraryService.searchBooks("test");
            assertEquals(1, results.size(), "Case insensitive search should work");
            System.out.println("🔍 Case insensitive search 'test': Found " + results.size() + " book(s)");
            
            // Test search by author
            results = libraryService.searchBooks("Author");
            assertEquals(1, results.size(), "Should find 1 book by author");
            System.out.println("🔍 Search by author 'Author': Found " + results.size() + " book(s)");
            
            // Test search with no results
            results = libraryService.searchBooks("NonExistent");
            assertTrue(results.isEmpty(), "Should find no books for non-existent term");
            System.out.println("🔍 Search for 'NonExistent': Found " + results.size() + " book(s)");
            
            System.out.println("✅ All search functionality working correctly");
        }
        
        @Test
        @DisplayName("Should return all books for empty search term")
        @Order(2)
        void testSearchBooksEmptyTerm() {
            System.out.println("🧪 Running: Should return all books for empty search term");
            
            libraryService.addBook(testBook);
            int totalBooks = libraryService.getBooks().size();
            System.out.println("📊 Total books in library: " + totalBooks);
            
            List<Book> results = libraryService.searchBooks("");
            
            assertFalse(results.isEmpty(), "Empty search should return all books");
            assertTrue(results.size() >= 1, "Should return at least our test book plus sample data");
            
            System.out.println("🔍 Empty search returned: " + results.size() + " book(s)");
            System.out.println("✅ Empty search functionality working correctly");
        }
        
        @Test
        @DisplayName("Should filter available books correctly")
        @Order(3)
        void testGetAvailableBooks() {
            System.out.println("🧪 Running: Should filter available books correctly");
            
            libraryService.addBook(testBook);
            
            List<Book> availableBooks = libraryService.getAvailableBooks();
            assertTrue(availableBooks.contains(testBook), "Available books should include our test book");
            System.out.println("📊 Available books (book available): " + availableBooks.size());
            
            // Make book unavailable
            testBook.setAvailable(false);
            System.out.println("📚 Set test book as unavailable");
            
            availableBooks = libraryService.getAvailableBooks();
            assertFalse(availableBooks.contains(testBook), "Unavailable books should not be in available list");
            System.out.println("📊 Available books (book unavailable): " + availableBooks.size());
            
            System.out.println("✅ Available books filtering working correctly");
        }
        
        @Test
        @DisplayName("Should identify overdue books")
        @Order(4)
        void testGetOverdueBooks() {
            System.out.println("🧪 Running: Should identify overdue books");
            
            List<Transaction> overdueBooks = libraryService.getOverdueBooks();
            assertNotNull(overdueBooks, "Overdue books list should not be null");
            
            System.out.println("📊 Current overdue books: " + overdueBooks.size());
            System.out.println("✅ Overdue books query working correctly");
        }
    }
    
    @Nested
    @DisplayName("Sample Data Tests")
    @Order(6)
    class SampleDataTests {
        
        @Test
        @DisplayName("Should initialize with sample data")
        @Order(1)
        void testSampleDataInitialization() {
            System.out.println("🧪 Running: Should initialize with sample data");
            
            // Verify sample data is loaded
            assertFalse(libraryService.getBooks().isEmpty(), "Should have sample books");
            assertFalse(libraryService.getMembers().isEmpty(), "Should have sample members");
            
            System.out.println("📊 Sample books loaded: " + libraryService.getBooks().size());
            System.out.println("📊 Sample members loaded: " + libraryService.getMembers().size());
            
            // Check for specific sample books
            assertTrue(libraryService.getBooks().containsKey("978-0134685991"), "Should contain 'Effective Java'");
            assertTrue(libraryService.getMembers().containsKey("M001"), "Should contain member M001");
            
            System.out.println("✅ Sample data initialization verified");
        }
        
        @Test
        @DisplayName("Should have correct sample book details")
        @Order(2)
        void testSampleBookDetails() {
            System.out.println("🧪 Running: Should have correct sample book details");
            
            Book effectiveJava = libraryService.getBooks().get("978-0134685991");
            assertNotNull(effectiveJava, "Effective Java book should exist");
            assertEquals("Effective Java", effectiveJava.getTitle());
            assertEquals("Joshua Bloch", effectiveJava.getAuthor());
            assertEquals("Programming", effectiveJava.getCategory());
            
            System.out.println("📚 Sample book verified: " + effectiveJava.getTitle() + " by " + effectiveJava.getAuthor());
            System.out.println("✅ Sample book details are correct");
        }
        
        @Test
        @DisplayName("Should have correct sample member details")
        @Order(3)
        void testSampleMemberDetails() {
            System.out.println("🧪 Running: Should have correct sample member details");
            
            Member johnDoe = libraryService.getMembers().get("M001");
            assertNotNull(johnDoe, "John Doe member should exist");
            assertEquals("John Doe", johnDoe.getName());
            assertEquals("john.doe@email.com", johnDoe.getEmail());
            
            System.out.println("👤 Sample member verified: " + johnDoe.getName() + " (" + johnDoe.getEmail() + ")");
            System.out.println("✅ Sample member details are correct");
        }
    }
}