package com.library.model;

import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Book class
 */
@DisplayName("Book Class Tests")
class BookTest {
    
    private Book book;
    
    @BeforeEach
    void setUp() {
        System.out.println("Setting up test data for Book tests...");
        book = new Book("978-0134685991", "Effective Java", "Joshua Bloch", 
                       "Programming", LocalDate.of(2017, 12, 27));
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("Test completed.\n");
    }
    
    @Nested
    @DisplayName("Book Creation Tests")
    class BookCreationTests {
        
        @Test
        @DisplayName("Should create book with all required fields")
        void testBookCreation() {
            System.out.println("🧪 Running: Should create book with all required fields");
            
            assertNotNull(book);
            assertEquals("978-0134685991", book.getIsbn());
            assertEquals("Effective Java", book.getTitle());
            assertEquals("Joshua Bloch", book.getAuthor());
            assertEquals("Programming", book.getCategory());
            assertTrue(book.isAvailable());
            assertEquals(0, book.getBorrowCount());
            
            System.out.println("✅ Book created successfully with all fields");
        }
        
        @Test
        @DisplayName("Should initialize book as available with zero borrow count")
        void testInitialBookState() {
            System.out.println("🧪 Running: Should initialize book as available with zero borrow count");
            
            assertTrue(book.isAvailable(), "New book should be available");
            assertEquals(0, book.getBorrowCount(), "New book should have zero borrow count");
            
            System.out.println("✅ Book initialized with correct default state");
        }
    }
    
    @Nested
    @DisplayName("Book Property Tests")
    class BookPropertyTests {
        
        @Test
        @DisplayName("Should update book properties correctly")
        void testSettersAndGetters() {
            System.out.println("🧪 Running: Should update book properties correctly");
            
            // Test title update
            book.setTitle("New Title");
            assertEquals("New Title", book.getTitle());
            System.out.println("📝 Title updated successfully");
            
            // Test author update
            book.setAuthor("New Author");
            assertEquals("New Author", book.getAuthor());
            System.out.println("👤 Author updated successfully");
            
            // Test availability update
            book.setAvailable(false);
            assertFalse(book.isAvailable());
            System.out.println("📚 Availability updated successfully");
            
            System.out.println("✅ All property updates working correctly");
        }
        
        @Test
        @DisplayName("Should increment borrow count correctly")
        void testIncrementBorrowCount() {
            System.out.println("🧪 Running: Should increment borrow count correctly");
            
            assertEquals(0, book.getBorrowCount(), "Initial borrow count should be 0");
            System.out.println("📊 Initial borrow count: " + book.getBorrowCount());
            
            book.incrementBorrowCount();
            assertEquals(1, book.getBorrowCount(), "Borrow count should be 1 after first increment");
            System.out.println("📊 After first increment: " + book.getBorrowCount());
            
            book.incrementBorrowCount();
            assertEquals(2, book.getBorrowCount(), "Borrow count should be 2 after second increment");
            System.out.println("📊 After second increment: " + book.getBorrowCount());
            
            System.out.println("✅ Borrow count incrementation working correctly");
        }
    }
    
    @Nested
    @DisplayName("Book Equality Tests")
    class BookEqualityTests {
        
        @Test
        @DisplayName("Should consider books equal when ISBN matches")
        void testEqualsAndHashCode() {
            System.out.println("🧪 Running: Should consider books equal when ISBN matches");
            
            Book sameBook = new Book("978-0134685991", "Different Title", "Different Author", 
                                    "Different Category", LocalDate.of(2020, 1, 1));
            
            assertEquals(book, sameBook, "Books with same ISBN should be equal");
            assertEquals(book.hashCode(), sameBook.hashCode(), "Books with same ISBN should have same hash code");
            System.out.println("✅ Books with same ISBN are correctly identified as equal");
            
            Book differentBook = new Book("978-1234567890", "Effective Java", "Joshua Bloch", 
                                         "Programming", LocalDate.of(2017, 12, 27));
            
            assertNotEquals(book, differentBook, "Books with different ISBN should not be equal");
            System.out.println("✅ Books with different ISBN are correctly identified as different");
        }
    }
    
    @Nested
    @DisplayName("Book String Representation Tests")
    class BookStringTests {
        
        @Test
        @DisplayName("Should contain all important fields in string representation")
        void testToString() {
            System.out.println("🧪 Running: Should contain all important fields in string representation");
            
            String bookString = book.toString();
            System.out.println("📄 Book string representation: " + bookString);
            
            assertTrue(bookString.contains("978-0134685991"), "Should contain ISBN");
            assertTrue(bookString.contains("Effective Java"), "Should contain title");
            assertTrue(bookString.contains("Joshua Bloch"), "Should contain author");
            assertTrue(bookString.contains("Programming"), "Should contain category");
            
            System.out.println("✅ String representation contains all required fields");
        }
    }
}