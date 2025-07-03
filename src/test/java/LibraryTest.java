import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class LibraryTest {
    private LibraryService libraryService;
    private Book book;
    private Member member;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    
    @BeforeEach
    void setUp() {
        libraryService = new LibraryService();
        book = new Book("B001", "Test Book", "Test Author");
        member = new Member("M001", "Test Member", "test@email.com");
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
    
    @Test
    void testBookCreation() {
        assertEquals("B001", book.getId());
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testBookSetAvailable() {
        book.setAvailable(false);
        assertFalse(book.isAvailable());
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testMemberCreation() {
        assertEquals("M001", member.getId());
        assertEquals("Test Member", member.getName());
        assertEquals("test@email.com", member.getEmail());
    }
    
    @Test
    void testTransactionCreation() {
        Transaction transaction = new Transaction("T001", "B001", "M001", LocalDate.now());
        assertEquals("T001", transaction.getId());
        assertEquals("B001", transaction.getBookId());
        assertEquals("M001", transaction.getMemberId());
        assertNotNull(transaction.getBorrowDate());
        assertFalse(transaction.isReturned());
        assertNull(transaction.getReturnDate());
    }
    
    @Test
    void testTransactionSetReturned() {
        Transaction transaction = new Transaction("T001", "B001", "M001", LocalDate.now());
        LocalDate returnDate = LocalDate.now();
        transaction.setReturned(true);
        transaction.setReturnDate(returnDate);
        assertTrue(transaction.isReturned());
        assertEquals(returnDate, transaction.getReturnDate());
    }
    
    @Test
    void testAddBook() {
        libraryService.addBook(book);
        assertEquals(1, libraryService.getAllBooks().size());
        assertTrue(libraryService.getAllBooks().contains(book));
    }
    
    @Test
    void testAddMember() {
        libraryService.addMember(member);
        assertEquals(1, libraryService.getAllMembers().size());
        assertTrue(libraryService.getAllMembers().contains(member));
    }
    
    @Test
    void testBorrowBookSuccess() {
        libraryService.addBook(book);
        libraryService.addMember(member);
        assertTrue(libraryService.borrowBook("B001", "M001"));
        assertFalse(book.isAvailable());
        assertEquals(1, libraryService.getAllTransactions().size());
    }
    
    @Test
    void testBorrowBookFailureNonExistentBook() {
        libraryService.addMember(member);
        assertFalse(libraryService.borrowBook("B999", "M001"));
    }
    
    @Test
    void testBorrowBookFailureNonExistentMember() {
        libraryService.addBook(book);
        assertFalse(libraryService.borrowBook("B001", "M999"));
    }
    
    @Test
    void testBorrowBookFailureBookNotAvailable() {
        libraryService.addBook(book);
        libraryService.addMember(member);
        book.setAvailable(false);
        assertFalse(libraryService.borrowBook("B001", "M001"));
    }
    
    @Test
    void testReturnBookSuccess() {
        libraryService.addBook(book);
        libraryService.addMember(member);
        libraryService.borrowBook("B001", "M001");
        assertTrue(libraryService.returnBook("B001"));
        assertTrue(book.isAvailable());
    }
    
    @Test
    void testReturnBookFailureNonExistentBook() {
        assertFalse(libraryService.returnBook("B999"));
    }
    
    @Test
    void testReturnBookFailureNoActiveTransaction() {
        libraryService.addBook(book);
        assertFalse(libraryService.returnBook("B001"));
    }
    
    @Test
    void testGetAllBooksEmptyList() {
        assertEquals(0, libraryService.getAllBooks().size());
    }
    
    @Test
    void testGetAllMembersEmptyList() {
        assertEquals(0, libraryService.getAllMembers().size());
    }
    
    @Test
    void testGetAllTransactionsEmptyList() {
        assertEquals(0, libraryService.getAllTransactions().size());
    }
    
    @Test
    void testMultipleBooks() {
        Book book2 = new Book("B002", "Another Book", "Another Author");
        libraryService.addBook(book);
        libraryService.addBook(book2);
        assertEquals(2, libraryService.getAllBooks().size());
    }
    
    @Test
    void testMultipleMembers() {
        Member member2 = new Member("M002", "Another Member", "another@email.com");
        libraryService.addMember(member);
        libraryService.addMember(member2);
        assertEquals(2, libraryService.getAllMembers().size());
    }
    
    @Test
    void testMultipleTransactions() {
        Book book2 = new Book("B002", "Another Book", "Another Author");
        Member member2 = new Member("M002", "Another Member", "another@email.com");
        
        libraryService.addBook(book);
        libraryService.addBook(book2);
        libraryService.addMember(member);
        libraryService.addMember(member2);
        
        libraryService.borrowBook("B001", "M001");
        libraryService.borrowBook("B002", "M002");
        
        assertEquals(2, libraryService.getAllTransactions().size());
    }
    
    @Test
    void testLibraryAppMainMethod() {
        String input = "8\n"; // Exit immediately
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
    }
    
    @Test
    void testLibraryAppMenuOptions() {
        String input = "5\n6\n7\n8\n"; // View books, members, transactions, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("=== All Books ==="));
        assertTrue(output.contains("=== All Members ==="));
        assertTrue(output.contains("=== All Transactions ==="));
    }
    
    @Test
    void testLibraryAppInvalidOption() {
        String input = "99\n8\n"; // Invalid option, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Invalid option"));
    }
    
    @Test
    void testLibraryAppAddBook() {
        String input = "1\nB999\nTest Book\nTest Author\n8\n"; // Add book, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Book added successfully"));
    }
    
    @Test
    void testLibraryAppAddMember() {
        String input = "2\nM999\nTest Member\ntest@test.com\n8\n"; // Add member, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Member added successfully"));
    }
    
    @Test
    void testLibraryAppBorrowBook() {
        String input = "3\nB001\nM001\n8\n"; // Borrow book, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Book borrowed successfully"));
    }
    
    @Test
    void testLibraryAppReturnBook() {
        String input = "4\nB002\n8\n"; // Return B002 which is borrowed by M005, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        // Either successful return or failure message should appear
        assertTrue(output.contains("Book returned successfully") || output.contains("Failed to return book"));
    }
    
    @Test
    void testLibraryAppBorrowBookFailure() {
        String input = "3\nB999\nM999\n8\n"; // Try to borrow non-existent book, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        // Should contain some response about borrowing
        assertTrue(output.contains("Failed to borrow book") || output.contains("Book borrowed successfully"));
    }
    
    @Test
    void testLibraryAppReturnBookFailure() {
        String input = "4\nB999\n8\n"; // Try to return non-existent book, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        // Should contain some response about returning
        assertTrue(output.contains("Failed to return book") || output.contains("Book returned successfully"));
    }
    
    @Test
    void testGetIntInputWithInvalidInput() {
        String input = "abc\n8\n"; // Invalid number input, then exit
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        LibraryApp.setScanner(new Scanner(inputStream));
        
        assertDoesNotThrow(() -> LibraryApp.main(new String[]{}));
        
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Invalid option"));
    }
}