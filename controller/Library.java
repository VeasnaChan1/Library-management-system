package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Book;
import model.Borrow;
import user.BorrowStaff;
import user.IStaff;
import user.LibrarianStaff;
import user.ManagerStaff;
import user.Member;

public class Library {
    // ====== Role Permissions ======
    public static final String BORROW_BOOK = "BORROW_BOOK";
    public static final String RETURN_BOOK = "RETURN_BOOK";
    public static final String VIEW_BORROW_RECORDS = "VIEW_BORROW_RECORDS";
    public static final String MANAGE_STAFF = "MANAGE_STAFF";
    public static final String VIEW_REPORTS = "VIEW_REPORTS";
    public static final String APPROVE_OPERATIONS = "APPROVE_OPERATIONS";
    public static final String ADD_BOOK = "ADD_BOOK";
    public static final String UPDATE_CATALOG = "UPDATE_CATALOG";
    public static final String VIEW_INVENTORY = "VIEW_INVENTORY";

    private ArrayList<Book> books;          // list to store books
    private ArrayList<Borrow> borrowRecords; // list to store borrow records
    private ArrayList<Member> members;       // list to store members
    private ArrayList<IStaff> staff;         // list to store staff members
    private IStaff loggedInUser;             // currently logged-in staff member

    // optional capacity limits (0 or negative means no limit)
    private int MAX_BOOKS;   // maximum number of books
    private int MAX_MEMBERS; // maximum number of members

    // Constructor with custom capacity for books and members
    public Library(int bookCapacity, int memberCapacity) {
        this.MAX_BOOKS = bookCapacity;
        this.MAX_MEMBERS = memberCapacity;
        books = new ArrayList<>(MAX_BOOKS > 0 ? MAX_BOOKS : 10); // initial capacity only
        borrowRecords = new ArrayList<>();
        members = new ArrayList<>(MAX_MEMBERS > 0 ? MAX_MEMBERS : 10);
        staff = new ArrayList<>();
        this.loggedInUser = null;
    }

    // ====== Permission Checking ======
    public boolean requirePermission(IStaff user, String action) {
        if (user == null) {
            System.out.println("ERROR: No user logged in. Permission denied for action: " + action);
            return false;
        }
        
        if (!user.can(action)) {
            System.out.println("ERROR: User '" + user.getUsername() + "' (" + user.getRole() + ") is not allowed to perform: " + action);
            return false;
        }
        
        System.out.println("✓ Permission granted for '" + user.getUsername() + "' to perform: " + action);
        return true;
    }

    // ====== Staff Management ======
    public void addStaff(IStaff staffMember) {
        if (requirePermission(loggedInUser, MANAGE_STAFF)) {
            staff.add(staffMember);
            System.out.println("Staff member added: " + staffMember.getFullName());
        }
    }

    // internal helper used by populateSampleData or setup routines where no
    // user has logged in yet.  It bypasses permission checks.
    void addStaffInternal(IStaff staffMember) {
        staff.add(staffMember);
    }

    /** Attempt to log a staff member in using their ID and password. */
    public boolean staffLogin(String staffId, String password) {
        for (IStaff s : staff) {
            if (s.getStaffId().equals(staffId)) {
                if (!s.isActive()) {
                    System.out.println("Login failed: staff member is not active.");
                    return false;
                }
                if (s.checkPassword(password)) {
                    loggedInUser = s;
                    System.out.println("Login successful: " + s.getFullName()
                                       + " (" + s.getRole() + ")");
                    return true;
                } else {
                    System.out.println("Login failed: incorrect password.");
                    return false;
                }
            }
        }
        System.out.println("Login failed: staff ID not found.");
        return false;
    }

    /** Logout currently logged in staff member. */
    public void staffLogout() {
        if (loggedInUser != null) {
            System.out.println("User " + loggedInUser.getFullName() + " logged out.");
        }
        loggedInUser = null;
    }

    public void setLoggedInUser(IStaff user) {
        this.loggedInUser = user;
    }

    public IStaff getLoggedInUser() {
        return loggedInUser;
    }
    
    public void addBook(Book book) {
        if (requirePermission(loggedInUser, ADD_BOOK)) {
            if (MAX_BOOKS <= 0 || books.size() < MAX_BOOKS) {
                books.add(book);
                System.out.println("Book added successfully: " + book.getTitle());
            } else {
                System.out.println("Library is full. Cannot add more books.");
            }
        }
    }

    // Add a member to the library
    public void addMember(Member member) {
        if (MAX_MEMBERS <= 0 || members.size() < MAX_MEMBERS) {
            members.add(member);
            System.out.println("Member added successfully: " + member);
        } else {
            System.out.println("Library member capacity is full. Cannot add more members.");
        }
    }

    // Find a member by member ID (string like LIB110001)
    public Member findMemberById(String memberId) {
        for (Member m : members) {
            if (m != null && m.getMemberID().equals(memberId)) {
                return m;
            }
        }
        return null;
    }

    public void updateName(String memberId, String newName) {
        if (!requirePermission(loggedInUser, UPDATE_CATALOG)) {
            return;
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }
        member.setName(newName);
        System.out.println("Member name updated successfully: " + member);
    }

    public void updateAge(String memberId, int newAge) {
        if (!requirePermission(loggedInUser, UPDATE_CATALOG)) {
            return;
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }
        member.setAge(newAge);
        System.out.println("Member age updated successfully: " + member);
    }

    public void updateGender(String memberId, String newGender) {
        if (!requirePermission(loggedInUser, UPDATE_CATALOG)) {
            return;
        }

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
        if (members.isEmpty()) {
            System.out.println("No members in the library yet.");
            return;
        }
        for (Member m : members) {
            System.out.println(m);
        }
    }

    // Get total number of members
    int getTotalMembers() {
        return members.size();
    }

    void displayAllBooks() {
        System.out.println("=== Book Library ===");
        for (Book b : books) {
            System.out.println(b);
        }
    }

    int getTotalBooks() {
        return books.size();
    }

    void totalook(){
        System.out.println("=== Total Books in Library ===");
        System.out.println("Total number of books: " + books.size());
    }

    void displayBookStatistics() {
        if (!requirePermission(loggedInUser, VIEW_INVENTORY)) {
            return;
        }

        int totalAmount = 0;
        int availableCount = 0;
        for (Book b : books) {
            if (b != null) {
                totalAmount += b.getAmount();
                if (b.isAvailable()) {
                    availableCount++;
                }
            }
        }
        System.out.println("=== Library Statistics ===");
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Available books: " + availableCount);
        System.out.println("Borrowed books: " + (books.size() - availableCount));
    }

    Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }
    // Find book by ISBN code
    Book findBookByISBN(String isbnCode) {
        for (Book b : books) {
            if (b.getIsbnCode().equals(isbnCode)) {
                return b;
            }
        }
        return null;
    }

    // Borrow a book from the library - only a Member can borrow
    Borrow borrowBook(int bookId, Member member, java.time.LocalDate borrowDate) {
        if (!requirePermission(loggedInUser, BORROW_BOOK)) {
            return null;
        }

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
        borrowRecords.add(borrow);
        
        // Display all books with the same title to show availability
        
        return borrow;
    }

    // Return a book to the library and display borrow/return information
    Borrow returnBook(String memberId, int bookId, java.time.LocalDate returnDate) {
        if (!requirePermission(loggedInUser, RETURN_BOOK)) {
            return null;
        }

        // Find the borrow record by member ID and book ID
        Borrow borrowRecord = null;
        for (Borrow r : borrowRecords) {
            if (r != null && r.getMemberId().equals(memberId)
                && r.getBook().getId() == bookId && r.getStatus().equals("borrowed")) {
                borrowRecord = r;
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
    void addBookInternal(Book book) {
    if (MAX_BOOKS <= 0 || books.size() < MAX_BOOKS) {
        books.add(book);
        System.out.println("Book added successfully: " + book.getTitle());
    } else {
        System.out.println("Library is full. Cannot add more books.");
    }
}
    
    // Populate library with sample books & members (moved from Main)
    void populateSampleData() {
        
        Book book1 = new Book("English", "B1", "Joe", "978-1-73430-269-1", true);
        Book book2 = new Book("English", "B1", "Joe", "978-1-73430-269-1", true);
        Book book3 = new Book("English", "B2", "Jow", "978-1-73430-269-2", true);
        Book book4 = new Book("English", "B2", "Jow", "978-1-73430-269-2", true);
        Book book5 = new Book("English", "C1", "Jow", "978-1-73430-269-5", true);

        addBookInternal(book1);
        addBookInternal(book2);
        addBookInternal(book3);
        addBookInternal(book4);
        addBookInternal(book5);

        
        Member m1 = new Member("John Doe", 25, "M");
        Member m2 = new Member("Jane Smith", 22, "F");
        Member m3 = new Member("Jany Smith", 22, "F");
        addMember(m1);
        addMember(m2);
        addMember(m3);

        // --- sample staff for each role ---
        BorrowStaff bs = new BorrowStaff("STAFF001", "Alice Borrower", "012345678",
                "alice", "pass1", "Borrow Clerk");
        LibrarianStaff ls = new LibrarianStaff("STAFF002", "Bob Librarian", "098765432",
                "bob", "pass2", "Head Librarian");
        ManagerStaff ms = new ManagerStaff("STAFF003", "Carol Manager", "011223344",
                "carol", "pass3", "Library Manager");

        // use internal helper to avoid permission check during initialization
        addStaffInternal(bs);
        addStaffInternal(ls);
        addStaffInternal(ms);
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

    // ====== Console/interactive helpers ======
    public static void printMenu() {
        System.out.println("\n=== Library Menu ===");
        System.out.println("1. Display all books");
        System.out.println("2. Display book statistics");
        System.out.println("3. Display all members");
        System.out.println("4. Add a new book");
        System.out.println("5. Update member name");
        System.out.println("6. Borrow book");
        System.out.println("7. Return book");
        System.out.println("8. Logout");
        System.out.println("0. Exit");
    }

    public static void printLoginMenu() {
        System.out.println("\n=== Staff Login ===");
        System.out.println("1. Login (requires ID & password)");
        System.out.println("0. Exit");
    }

    public static void borrowInteractive(Library lib, Scanner sc) {
        System.out.print("Member ID: ");
        String memberId = sc.nextLine();
        Member member = lib.findMemberById(memberId);
        System.out.print("Book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();
        Borrow b = lib.borrowBook(bookId, member, java.time.LocalDate.now());
        displayBorrowRecord(b);
    }

    public static void addBookInteractive(Library lib, Scanner sc) {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Category: ");
        String cat = sc.nextLine();
        System.out.print("Author: ");
        String auth = sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();
        System.out.print("Available (true/false): ");
        boolean available = sc.nextBoolean();
        sc.nextLine();
        Book book = new Book(title, cat, auth, isbn, available);
        lib.addBook(book);
    }

    public static void updateMemberNameInteractive(Library lib, Scanner sc) {
        System.out.print("Member ID: ");
        String id = sc.nextLine();
        System.out.print("New Name: ");
        String newName = sc.nextLine();
        lib.updateName(id, newName);
    }

    public static void returnInteractive(Library lib, Scanner sc) {
        System.out.print("Member ID: ");
        String memberId = sc.nextLine();
        System.out.print("Book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();
        Borrow b = lib.returnBook(memberId, bookId, java.time.LocalDate.now());
        displayBorrowRecord(b);
    }

    // remove earlier instance helpers except displayBorrowRecord already exists
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public List<Borrow> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(ArrayList<Borrow> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    // removed getCurBook, getBorrowCount, and their setters since list size covers same info


}
