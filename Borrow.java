import java.time.LocalDate;

public class Borrow {
     int memberId;
     String memberName;
     Book book;
     LocalDate borrowDate;
     LocalDate returnDate;
     String status;

    public Borrow(int memberId, String memberName, Book book, LocalDate borrowDate, String status) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.book = book;
        this.borrowDate = borrowDate;
        this.status = status;
        this.returnDate = null;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        this.status = "returned";
    }

    @Override
    public String toString() {
        return "Borrow [memberId=" + memberId + ", memberName=" + memberName + ", book=" + (book != null ? book.title : "null") + ", borrowDate="
                + borrowDate + ", returnDate=" + returnDate + ", status=" + status + "]";
    }
}
