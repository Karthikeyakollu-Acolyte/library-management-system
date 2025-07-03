import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private final List<Book> books;
    private final List<Member> members;
    private final List<Transaction> transactions;
    
    public LibraryService() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }
    
    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }
    
    public void addMember(Member member) {
        if (member != null) {
            members.add(member);
        }
    }
    
    public boolean borrowBook(String bookId, String memberId) {
        if (bookId == null || memberId == null) {
            return false;
        }
        
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);
        
        if (book != null && member != null && book.isAvailable()) {
            book.setAvailable(false);
            String transactionId = generateTransactionId();
            Transaction transaction = new Transaction(
                transactionId, 
                bookId, 
                memberId, 
                LocalDate.now()
            );
            transactions.add(transaction);
            return true;
        }
        return false;
    }
    
    public boolean returnBook(String bookId) {
        if (bookId == null) {
            return false;
        }
        
        Book book = findBookById(bookId);
        Transaction transaction = findActiveTransactionByBookId(bookId);
        
        if (book != null && transaction != null && !transaction.isReturned()) {
            book.setAvailable(true);
            transaction.setReturned(true);
            transaction.setReturnDate(LocalDate.now());
            return true;
        }
        return false;
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
    
    public List<Member> getAllMembers() {
        return new ArrayList<>(members);
    }
    
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    private String generateTransactionId() {
        return String.format("TXN%03d", transactions.size() + 1);
    }
    
    private Book findBookById(String id) {
        if (id == null) {
            return null;
        }
        
        return books.stream()
            .filter(book -> id.equals(book.getId()))
            .findFirst()
            .orElse(null);
    }
    
    private Member findMemberById(String id) {
        if (id == null) {
            return null;
        }
        
        return members.stream()
            .filter(member -> id.equals(member.getId()))
            .findFirst()
            .orElse(null);
    }
    
    private Transaction findActiveTransactionByBookId(String bookId) {
        if (bookId == null) {
            return null;
        }
        
        return transactions.stream()
            .filter(transaction -> bookId.equals(transaction.getBookId()) && !transaction.isReturned())
            .findFirst()
            .orElse(null);
    }
}