import java.time.LocalDate;

public class Borrow {

     private String memberId;
     private String memberName;
     private Book book;
     private LocalDate borrowDate;
     private LocalDate returnDate;
     private String status;
    public Borrow(String memberId, String memberName, Book book, LocalDate borrowDate, String status) {
        this.memberId = memberId;
        this.setMemberName(memberName);
        this.setBook(book);
        this.setBorrowDate(borrowDate);
        this.setStatus(status);
        this.setReturnDate(null);
    }

    @Override
    public String toString() {
        return "Borrow [memberId=" + memberId + ", memberName=" + memberName + ", book=" + (book != null ? book.getTitle() : "null") + ", borrowDate="
                + borrowDate + ", returnDate=" + returnDate + ", status=" + status + "]";
    }
    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        if(memberName == null || memberName.trim().isEmpty()) {
            this.memberName = "Unknown Member";
        } else {
            this.memberName = memberName;
        }
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
        if(borrowDate == null) {
            this.borrowDate = LocalDate.now();
        } else {
            this.borrowDate = borrowDate;
        }
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if(returnDate != null && returnDate.isBefore(borrowDate)) {
            this.returnDate = borrowDate.plusDays(14); // default to 2 weeks after borrow date
        } else {
            this.returnDate = returnDate;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status == null || status.trim().isEmpty()) {
            this.status = "Unknown Status";
        } else {
            this.status = status;
        }
    }

}

