class Library {
    Book[] books;   // array to store books
    Borrow[] borrowRecords;  // array to store borrow records
    int count;      // number of books currently in the array
    int borrowCount; // number of borrow records
    int MAX_SIZE;   // maximum number of books

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

    void Totalbook(){
        System.out.println("=== Total Books in Library ===");
        System.out.println("Total number of books: " + count);
    }

    void displayBookStatistics() {
        int totalAmount = 0;
        int availableCount = 0;
        for (int i = 0; i < count; i++) {
            if (books[i] != null) {
                totalAmount += books[i].amount;
                if (books[i].isAvailable) {
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
            if (books[i].id == id) {
                return books[i];
            }
        }
        return null;
    }
    // Find book by ISBN code
    Book findBookByISBN(String isbnCode) {
        for (int i = 0; i < count; i++) {
            if (books[i].isbnCode.equals(isbnCode)) {
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
        
        if (!book.isAvailable) {
            System.out.println("Book '" + book.title + "' is not available.");
            return null;
        }
        
        // Update book status
        book.borrowBook();  // decreases amount and sets isAvailable to false
        
        // Create borrow record using member fields (package-private)
        Borrow borrow = new Borrow(member.memberID, member.name, book, borrowDate, "borrowed");
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
            if (borrowRecords[i] != null && borrowRecords[i].memberId == memberId 
                && borrowRecords[i].book.id == bookId && borrowRecords[i].status.equals("borrowed")) {
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
        borrowRecord.book.returnBook();

        // Display borrow/return information
        System.out.println("\n=== Book Return Information ===");
        System.out.println("Member ID: " + PREFIX + String.format("%0" + WIDTH + "d", borrowRecord.memberId));
        System.out.println("Member Name: " + borrowRecord.memberName);
        System.out.println("Book ID: " + borrowRecord.book.id);
        System.out.println("Book Title: " + borrowRecord.book.title);
        System.out.println("Book Author: " + borrowRecord.book.author);
        System.out.println("Book Category: " + borrowRecord.book.category);
        System.out.println("Book ISBN: " + borrowRecord.book.isbnCode);
        System.out.println("Borrow Date: " + borrowRecord.borrowDate);
        System.out.println("Return Date: " + borrowRecord.returnDate);
        System.out.println("Status: " + borrowRecord.status);

        return borrowRecord;
    }
    static final String PREFIX = "LIB11";
    static final int WIDTH = 4;

}
