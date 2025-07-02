package com.library.model;

import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Member class
 */
@DisplayName("Member Class Tests")
class MemberTest {
    
    private Member member;
    
    @BeforeEach
    void setUp() {
        System.out.println("Setting up test data for Member tests...");
        member = new Member("M001", "John Doe", "john.doe@email.com", "555-1234");
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("Test completed.\n");
    }
    
    @Nested
    @DisplayName("Member Creation Tests")
    class MemberCreationTests {
        
        @Test
        @DisplayName("Should create member with all required fields and defaults")
        void testMemberCreation() {
            System.out.println("🧪 Running: Should create member with all required fields and defaults");
            
            assertNotNull(member);
            assertEquals("M001", member.getMemberId());
            assertEquals("John Doe", member.getName());
            assertEquals("john.doe@email.com", member.getEmail());
            assertEquals("555-1234", member.getPhone());
            assertEquals(LocalDate.now(), member.getMembershipDate());
            assertEquals(0, member.getBorrowedBooksCount());
            assertEquals(3, member.getMaxBooksAllowed()); // Default limit
            assertTrue(member.canBorrowMoreBooks());
            
            System.out.println("✅ Member created successfully with all fields and defaults");
        }
        
        @Test
        @DisplayName("Should initialize with empty borrowed books list")
        void testInitialBorrowedBooksState() {
            System.out.println("🧪 Running: Should initialize with empty borrowed books list");
            
            assertTrue(member.getBorrowedBooks().isEmpty(), "New member should have no borrowed books");
            assertTrue(member.canBorrowMoreBooks(), "New member should be able to borrow books");
            assertEquals(0, member.getBorrowedBooksCount(), "Borrowed books count should be 0");
            
            System.out.println("✅ Member initialized with correct borrowed books state");
        }
    }
    
    @Nested
    @DisplayName("Member Property Tests")
    class MemberPropertyTests {
        
        @Test
        @DisplayName("Should update member properties correctly")
        void testSettersAndGetters() {
            System.out.println("🧪 Running: Should update member properties correctly");
            
            // Test name update
            member.setName("Jane Smith");
            assertEquals("Jane Smith", member.getName());
            System.out.println("👤 Name updated to: " + member.getName());
            
            // Test email update
            member.setEmail("jane.smith@email.com");
            assertEquals("jane.smith@email.com", member.getEmail());
            System.out.println("📧 Email updated to: " + member.getEmail());
            
            // Test max books limit update
            member.setMaxBooksAllowed(5);
            assertEquals(5, member.getMaxBooksAllowed());
            System.out.println("📚 Max books limit updated to: " + member.getMaxBooksAllowed());
            
            System.out.println("✅ All property updates working correctly");
        }
    }
    
    @Nested
    @DisplayName("Borrowed Books Management Tests")
    class BorrowedBooksTests {
        
        @Test
        @DisplayName("Should manage borrowed books correctly within limits")
        void testBorrowedBooksManagement() {
            System.out.println("🧪 Running: Should manage borrowed books correctly within limits");
            
            assertTrue(member.getBorrowedBooks().isEmpty());
            assertTrue(member.canBorrowMoreBooks());
            System.out.println("📊 Initial state - Books borrowed: 0/3");
            
            // Add first book
            member.addBorrowedBook("978-0134685991");
            assertEquals(1, member.getBorrowedBooksCount());
            assertTrue(member.getBorrowedBooks().contains("978-0134685991"));
            assertTrue(member.canBorrowMoreBooks());
            System.out.println("📊 After first book - Books borrowed: 1/3 ✅");
            
            // Add second book
            member.addBorrowedBook("978-0596009205");
            assertEquals(2, member.getBorrowedBooksCount());
            assertTrue(member.canBorrowMoreBooks());
            System.out.println("📊 After second book - Books borrowed: 2/3 ✅");
            
            // Add third book (reaches limit)
            member.addBorrowedBook("978-0321356680");
            assertEquals(3, member.getBorrowedBooksCount());
            assertFalse(member.canBorrowMoreBooks());
            System.out.println("📊 After third book - Books borrowed: 3/3 ⚠️ (Limit reached)");
            
            System.out.println("✅ Borrowed books management working correctly");
        }
        
        @Test
        @DisplayName("Should remove borrowed books correctly")
        void testRemoveBorrowedBook() {
            System.out.println("🧪 Running: Should remove borrowed books correctly");
            
            // Add books first
            member.addBorrowedBook("978-0134685991");
            member.addBorrowedBook("978-0596009205");
            assertEquals(2, member.getBorrowedBooksCount());
            System.out.println("📊 Added 2 books - Current count: " + member.getBorrowedBooksCount());
            
            // Remove a book
            member.removeBorrowedBook("978-0134685991");
            assertEquals(1, member.getBorrowedBooksCount());
            assertFalse(member.getBorrowedBooks().contains("978-0134685991"));
            assertTrue(member.getBorrowedBooks().contains("978-0596009205"));
            assertTrue(member.canBorrowMoreBooks());
            System.out.println("📊 After removal - Current count: " + member.getBorrowedBooksCount());
            
            System.out.println("✅ Book removal working correctly");
        }
        
        @Test
        @DisplayName("Should maintain encapsulation of borrowed books list")
        void testBorrowedBooksEncapsulation() {
            System.out.println("🧪 Running: Should maintain encapsulation of borrowed books list");
            
            member.addBorrowedBook("978-0134685991");
            System.out.println("📊 Added 1 book to member");
            
            // Modifying returned list should not affect original
            member.getBorrowedBooks().clear();
            assertEquals(1, member.getBorrowedBooksCount(), "Original list should not be affected");
            
            System.out.println("✅ Encapsulation maintained - external modifications don't affect internal state");
        }
    }
    
    @Nested
    @DisplayName("Member Equality Tests")
    class MemberEqualityTests {
        
        @Test
        @DisplayName("Should consider members equal when member ID matches")
        void testEqualsAndHashCode() {
            System.out.println("🧪 Running: Should consider members equal when member ID matches");
            
            Member sameMember = new Member("M001", "Different Name", "different@email.com", "555-9999");
            
            assertEquals(member, sameMember, "Members with same ID should be equal");
            assertEquals(member.hashCode(), sameMember.hashCode(), "Members with same ID should have same hash code");
            System.out.println("✅ Members with same ID are correctly identified as equal");
            
            Member differentMember = new Member("M002", "John Doe", "john.doe@email.com", "555-1234");
            
            assertNotEquals(member, differentMember, "Members with different ID should not be equal");
            System.out.println("✅ Members with different ID are correctly identified as different");
        }
    }
    
    @Nested
    @DisplayName("Member String Representation Tests")
    class MemberStringTests {
        
        @Test
        @DisplayName("Should contain all important fields in string representation")
        void testToString() {
            System.out.println("🧪 Running: Should contain all important fields in string representation");
            
            String memberString = member.toString();
            System.out.println("📄 Member string representation: " + memberString);
            
            assertTrue(memberString.contains("M001"), "Should contain member ID");
            assertTrue(memberString.contains("John Doe"), "Should contain name");
            assertTrue(memberString.contains("john.doe@email.com"), "Should contain email");
            assertTrue(memberString.contains("0/3"), "Should contain borrowed/max format");
            
            System.out.println("✅ String representation contains all required fields");
        }
    }
}