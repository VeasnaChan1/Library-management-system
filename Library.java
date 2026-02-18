public class Library {
    private Book[] books;   // array to store books
    private Borrow[] borrowRecords;  // array to store borrow records
    private Member[] members;  // array to store members
    private int curBook;      // number of books currently in the array
    private int borrowCount; // number of borrow records
    private int memberCount; // number of members
    private int MAX_BOOKS;   // maximum number of books
    private int MAX_MEMBERS; // maximum number of members

    // Constructor with custom capacity for books and members
    public Library(int bookCapacity, int memberCapacity) {
        this.MAX_BOOKS = bookCapacity;
        this.MAX_MEMBERS = memberCapacity;
        books = new Book[MAX_BOOKS]; // initialize array for books
        borrowRecords = new Borrow[MAX_BOOKS]; // initialize borrow records
        members = new Member[MAX_MEMBERS]; // initialize members array
        curBook = 0;                  // no books yet
        borrowCount = 0;            // no borrow records yet 
        memberCount = 0;            // no members yet
    }
    
    public void addBook(Book book) {
        if (curBook < MAX_BOOKS) {
            books[curBook] = book;
            curBook++;
        } else {
            System.out.println("Library is full. Cannot add more books.");
        }
    }

    // Add a member to the library
    public void addMember(Member member) {
        if (memberCount < MAX_MEMBERS) {
            members[memberCount] = member;
            memberCount++;
            System.out.println("Member added successfully: " + member);
        } else {
            System.out.println("Library member capacity is full. Cannot add more members.");
        }
    }

    // Find a member by member ID (string like LIB110001)
    public Member findMemberById(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i] != null && members[i].getMemberID().equals(memberId)) {
                return members[i];
            }
        }
        return null;
    }

    // Update member information by member ID
    // public void updateMemberInfo(String memberId, String newName, int newAge, String newGender) {
    //     Member member = findMemberById(memberId);
    //     if (member == null) {
    //         System.out.println("Member with ID " + memberId + " not found.");
    //         return false;
    //     }

    //     // Update only non-null values
    //     if (newName != null && !newName.isEmpty()) {
    //         member.setName(newName);
    //     }
    //     if (newAge > 0) {
    //         member.setAge(newAge);
    //     }
    //     if (newGender != null && !newGender.isEmpty()) {
    //         member.setGender(newGender);
    //     }

    //     System.out.println("Member information updated successfully: " + member);
    //     return true;
    // }

    public void updateName(String memberId, String newName) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }
        member.setName(newName);
        System.out.println("Member name updated successfully: " + member);
    }

    public void updateAge(String memberId, int newAge) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }
        member.setAge(newAge);
        System.out.println("Member age updated successfully: " + member);
    }

    public void updateGender(String memberId, String newGender) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }
        member.setGender(newGender);
        System.out.println("Member gender updated successfully: " + member);
    }
    

    

    // Display all members in the library
    void displayAllMembers() {
        System.out.println("\n=== All Library Members ===");
        if (memberCount == 0) {
            System.out.println("No members in the library yet.");
            return;
        }
        for (int i = 0; i < memberCount; i++) {
            if (members[i] != null) {
                System.out.println(members[i]);
            }
        }
    }

    // Get total number of members
    int getTotalMembers() {
        return memberCount;
    }

    void displayAllBooks() {
        System.out.println("=== Book Library ===");
        for (int i = 0; i < curBook; i++) {
            System.out.println(books[i]);
        }
    }

    int getTotalBooks() {
        return curBook;
    }

    void totalook(){
        System.out.println("=== Total Books in Library ===");
        System.out.println("Total number of books: " + curBook);
    }

    void displayBookStatistics() {
        int totalAmount = 0;
        int availableCount = 0;
        for (int i = 0; i < curBook; i++) {
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
        System.out.println("Borrowed books: " + (curBook - availableCount));
    }

    Book findBookById(int id) {
        for (int i = 0; i < curBook; i++) {
            if (books[i].getId() == id) {
                return books[i];
            } 
        }
        return null;
    }
    // Find book by ISBN code
    Book findBookByISBN(String isbnCode) {
        for (int i = 0; i < curBook; i++) {
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
    Borrow returnBook(String memberId, int bookId, java.time.LocalDate returnDate) {
        // Find the borrow record by member ID and book ID
        Borrow borrowRecord = null;
        for (int i = 0; i < borrowCount; i++) {
            if (borrowRecords[i] != null && borrowRecords[i].getMemberId().equals(memberId)
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
        System.out.println("Member ID: " + borrowRecord.getMemberId());
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
    
    // Populate library with sample books & members (moved from Main)
    void populateSampleData() {
        
        Book book1 = new Book("English", "B1", "Joe", "978-1-73430-269-1", true);
        Book book2 = new Book("English", "B1", "Joe", "978-1-73430-269-1", true);
        Book book3 = new Book("English", "B2", "Jow", "978-1-73430-269-2", true);
        Book book4 = new Book("English", "B2", "Jow", "978-1-73430-269-2", true);
        Book book5 = new Book("English", "C1", "Jow", "978-1-73430-269-5", true);
        addBook(book1);
        addBook(book2);
        addBook(book3);
        addBook(book4);
        addBook(book5);

        
        Member m1 = new Member("John Doe", 25, "M");
        Member m2 = new Member("Jane Smith", 22, "F");
        Member m3 = new Member("Jany Smith", 22, "F");
        addMember(m1);
        addMember(m2);
        addMember(m3);
    }
    
    static void displayBorrowRecord(Borrow borrow) {
    if (borrow != null) {
        System.out.println("Member ID: " + borrow.getMemberId());
        System.out.println("Member Name: " + borrow.getMemberName());
        System.out.println("Book ID: " + borrow.getBook().getId());
        System.out.println("Book Title: " + borrow.getBook().getTitle());
        System.out.println("Borrow Date: " + borrow.getBorrowDate());
        System.out.println("Status: " + borrow.getStatus());
        System.out.println("---");

    }
    else {
        System.out.println("Borrowing failed.");
    }

    }
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

    public int getCurBook() {
        return curBook;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }


}
