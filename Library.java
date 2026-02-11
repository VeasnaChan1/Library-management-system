public class Library {
    private Book[] books;   // array to store books
    private Borrow[] borrowRecords;  // array to store borrow records
    private int count;      // number of books currently in the array
    private int borrowCount; // number of borrow records
    private int MAX_SIZE;   // maximum number of books

    // Constructor with custom capacity
    Library(int capacity) {
        this.MAX_SIZE = capacity;
        books = new Book[MAX_SIZE]; // initialize array
        borrowRecords = new Borrow[MAX_SIZE]; // initialize borrow records
        count = 0;                  // no books yet
        borrowCount = 0;            // no borrow records yet
    }

    // Default constructor with capacity of 100
    Library() {
        this(150);
    }

    void addBook(Book book) {
        if (count < MAX_SIZE) {
            books[count] = book;
            count++;
        } else {
            System.out.println("Library is full. Cannot add more books.");
        }
    }

    void displayAllBooks() {
        System.out.println("=== Book Library ===");
        for (int i = 0; i < count; i++) {
            System.out.println(books[i]);
        }
    }

    int getTotalBooks() {
        return count;
    }

    void totalook(){
        System.out.println("=== Total Books in Library ===");
        System.out.println("Total number of books: " + count);
    }

    void displayBookStatistics() {
        int totalAmount = 0;
        int availableCount = 0;
        for (int i = 0; i < count; i++) {
            if (books[i] != null) {
                totalAmount += books[i].getAmount();
                if (books[i].isAvailable()) {
                    availableCount++;
                }
            }
        }
        System.out.println("=== Library Statistics ===");
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Available books: " + availableCount);
        System.out.println("Borrowed books: " + (count - availableCount));
    }

    Book findBookById(int id) {
        for (int i = 0; i < count; i++) {
            if (books[i].getId() == id) {
                return books[i];
            } 
        }
        return null;
    }
    // Find book by ISBN code
    Book findBookByISBN(String isbnCode) {
        for (int i = 0; i < count; i++) {
            if (books[i].getIsbnCode().equals(isbnCode)) {
                return books[i];
            }
        }
        return null;
    }

    // Borrow a book from the library - only a Member can borrow
    Borrow borrowBook(int bookId, Member member, java.time.LocalDate borrowDate) {
        if (member == null) {
            System.out.println("Borrow failed: borrower is not a valid member.");
            return null;
        }

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book with ID " + bookId + " not found.");
            return null;
        }
        
        if (!book.isAvailable()) {
            System.out.println("Book '" + book.getTitle() + "' is not available.");
            return null;
        }
        
        // Update book status
        book.borrowBook();  // decreases amount and sets isAvailable to false
        
        // Create borrow record using member fields
        Borrow borrow = new Borrow(member.getMemberID(), member.getName(), book, borrowDate, "borrowed");
        borrowRecords[borrowCount] = borrow;
        borrowCount++;
        
        // Display all books with the same title to show availability
        
        return borrow;
    }

    // Return a book to the library and display borrow/return information
    Borrow returnBook(int memberId, int bookId, java.time.LocalDate returnDate) {
        // Find the borrow record by member ID and book ID
        Borrow borrowRecord = null;
        for (int i = 0; i < borrowCount; i++) {
            if (borrowRecords[i] != null && borrowRecords[i].getMemberId() == memberId 
                && borrowRecords[i].getBook().getId() == bookId && borrowRecords[i].getStatus().equals("borrowed")) {
                borrowRecord = borrowRecords[i];
                break;
            }
        }

        if (borrowRecord == null) {
            System.out.println("No active borrow record found for member ID " + memberId + " and book ID " + bookId + ".");
            return null;
        }

        // Set return date and update status
        borrowRecord.setReturnDate(returnDate);

        // Return the book (restore amount and availability)
        borrowRecord.getBook().returnBook();

        // Display borrow/return information
        System.out.println("\n=== Book Return Information ===");
        System.out.println("Member ID: " + PREFIX + String.format("%0" + WIDTH + "d", borrowRecord.getMemberId()));
        System.out.println("Member Name: " + borrowRecord.getMemberName());
        System.out.println("Book ID: " + borrowRecord.getBook().getId());
        System.out.println("Book Title: " + borrowRecord.getBook().getTitle());
        System.out.println("Book Author: " + borrowRecord.getBook().getAuthor());
        System.out.println("Book Category: " + borrowRecord.getBook().getCategory());
        System.out.println("Book ISBN: " + borrowRecord.getBook().getIsbnCode());
        System.out.println("Borrow Date: " + borrowRecord.getBorrowDate());
        System.out.println("Return Date: " + borrowRecord.getReturnDate());
        System.out.println("Status: " + borrowRecord.getStatus());

        return borrowRecord;
    }
    
    static final String PREFIX = "LIB11";
    static final int WIDTH = 4;

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public Borrow[] getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(Borrow[] borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    public int getCount() {
        return count;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }


}
