import java.time.LocalDate;

public class Transaction {
    private String id;
    private String bookId;
    private String memberId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean isReturned;
    
    public Transaction(String id, String bookId, String memberId, LocalDate borrowDate) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.isReturned = false;
    }
    
    public String getId() {
        return id;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public String getMemberId() {
        return memberId;
    }
    
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public boolean isReturned() {
        return isReturned;
    }
    
    public void setReturned(boolean returned) {
        isReturned = returned;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}