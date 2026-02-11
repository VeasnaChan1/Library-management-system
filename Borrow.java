import java.time.LocalDate;

public class Borrow {

     private int memberId;
     private String memberName;
     private Book book;
     private LocalDate borrowDate;
     private LocalDate returnDate;
     private String status;

    public Borrow(int memberId, String memberName, Book book, LocalDate borrowDate, String status) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.book = book;
        this.borrowDate = borrowDate;
        this.status = status;
        this.returnDate = null;
    }

    @Override
    public String toString() {
        return "Borrow [memberId=" + memberId + ", memberName=" + memberName + ", book=" + (book != null ? book.getTitle() : "null") + ", borrowDate="
                + borrowDate + ", returnDate=" + returnDate + ", status=" + status + "]";
    }
    public int getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.status = "returned";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

