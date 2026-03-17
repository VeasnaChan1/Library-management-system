package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Book;
import model.Borrow;
import user.ActiveStaffFilter;
import user.BorrowStaff;
import user.LibrarianStaff;
import user.ManagerStaff;
import user.Member;
import user.Staff;
import user.StaffFilter;

public class Library {
    // ====== Role Permissions ======
    public static final String BORROW_BOOK = "BORROW_BOOK";
    public static final String RETURN_BOOK = "RETURN_BOOK";
    public static final String VIEW_BORROW_RECORDS = "VIEW_BORROW_RECORDS";
    public static final String MANAGE_STAFF = "MANAGE_STAFF";
    public static final String ADD_MEMBER = "ADD_MEMBER";
    public static final String VIEW_REPORTS = "VIEW_REPORTS";
    public static final String APPROVE_OPERATIONS = "APPROVE_OPERATIONS";
    public static final String ADD_BOOK = "ADD_BOOK";
    public static final String UPDATE_CATALOG = "UPDATE_CATALOG";
    public static final String VIEW_INVENTORY = "VIEW_INVENTORY";

    private ArrayList<Book> books;          // list to store books
    private ArrayList<Borrow> borrowRecords; // list to store borrow records
    private ArrayList<Member> members;       // list to store members
    private ArrayList<Staff> staff;         // list to store staff members
    private Staff loggedInUser;             // currently logged-in staff member

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

    // ====== Demo Utilities ======
    public void runPolymorphismDemo() {
        System.out.println("=== Polymorphism Demo: One list, many role behaviors ===");
        ArrayList<Staff> demoStaff = new ArrayList<>();
        demoStaff.add(new BorrowStaff("D001", "Alice", "012345678", "alice", "pass1", "Borrow Clerk", 350f, 11));
        demoStaff.add(new LibrarianStaff("D002", "Bob", "098765432", "bob", "pass2", "Librarian", 500f, 50f));
        demoStaff.add(new ManagerStaff("D003", "Charlie", "011223344", "charlie", "pass3", "Manager"));

        String[] actions = {Library.BORROW_BOOK, Library.ADD_BOOK, Library.MANAGE_STAFF, Library.ADD_MEMBER};
        for (Staff s : demoStaff) {
            for (String action : actions) {
                System.out.println(s.getUsername() + " can " + action + "? " + s.can(action));
            }
        }
        System.out.println();
    }

    // ====== Permission Checking ======
    public boolean requirePermission(Staff user, String action) {
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
    public void addStaff(Staff staffMember) {
        if (requirePermission(loggedInUser, MANAGE_STAFF)) {
            staff.add(staffMember);
            System.out.println("Staff member added: " + staffMember.getFullName());
        }
    }

    // internal helper used by populateSampleData or setup routines where no
    // user has logged in yet.  It bypasses permission checks.
    void addStaffInternal(Staff staffMember) {
        staff.add(staffMember);
    }

    /** Attempt to log a staff member in using their ID and password. */
    public boolean staffLogin(String staffId, String password) {
        for (Staff s : staff) {
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

    public void setLoggedInUser(Staff user) {
        this.loggedInUser = user;
    }

    public Staff getLoggedInUser() {
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

    // Add a member to the library (requires permission for interactive use)
    public void addMember(Member member) {
        if (!requirePermission(loggedInUser, ADD_MEMBER)) {
            return;
        }
        addMemberInternal(member);
    }

    // Internal add member helper used during initialization or bypassing permission checks
    void addMemberInternal(Member member) {
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

    // Display all staff in the library
    public void displayAllStaff() {
        if (!requirePermission(loggedInUser, MANAGE_STAFF)) {
            return;
        }
        System.out.println("\n=== All Staff Members ===");
        if (staff.isEmpty()) {
            System.out.println("No staff in the library yet.");
            return;
        }
        staff.forEach(System.out::println);
    }

    // Display only active staff in the library
    public void displayActiveStaff() {
        if (!requirePermission(loggedInUser, MANAGE_STAFF)) {
            return;
        }
        System.out.println("\n=== Active Staff Members ===");
        if (staff.isEmpty()) {
            System.out.println("No staff in the library yet.");
            return;
        }

        // Stage 4: Anonymous inner class behavior implementation
        StaffFilter activeFilterAnon = new StaffFilter() {
            @Override
            public boolean isActive(Staff s) {
                return s != null && s.isActive();
            }
        };

        // Stage 5: Lambda expression final evolution
        staff.stream()
                .filter(s -> s.isActive())
                .forEach(s -> System.out.println(s.getFullName()));

        // Optional method reference form for brevity:
        // staff.stream().filter(activeFilterAnon::isActive).forEach(System.out::println);
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
        addMemberInternal(m1);
        addMemberInternal(m2);
        addMemberInternal(m3);

        // --- sample staff for each role ---
               // --- sample staff for each role ---

    //            public ManagerStaff(Staff s, float salary) {
    //     super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword(), s.getPosition());
    //     this.setSalary(salary);
    // }

        BorrowStaff bs = new BorrowStaff("STAFF001", "Alice Borrower", "012345678",
                "alice", "pass1", "Borrow Clerk", 350f, 11);
        LibrarianStaff ls = new LibrarianStaff("STAFF002", "Bob Librarian", "098765432",
                "bob", "pass2", "Head Librarian", 500f, 150f);
        ManagerStaff ms = new ManagerStaff("STAFF003", "Charlie Manager", "011223344",
                "charlie", "pass3", "Library Manager");
        ms.setSalary(2200f);
        ms.setPositionSalary(200f);

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
        System.out.println("4. Add a new member (manager/librarian)");
        System.out.println("5. Add a new book");
        System.out.println("6. Update member name");
        System.out.println("7. Borrow book");
        System.out.println("8. Return book");
        System.out.println("9. Create staff (manager only)");
        System.out.println("10. Display all staff (manager only)");
        System.out.println("11. Display active staff (manager only)");
        System.out.println("12. Logout");
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

    public static void addMemberInteractive(Library lib, Scanner sc) {
        System.out.print("Member Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age;
        try {
            age = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Member creation canceled.");
            return;
        }
        System.out.print("Gender: ");
        String gender = sc.nextLine();

        Member member = new Member(name, age, gender);
        lib.addMember(member);
    }

    public void createStaff(Scanner sc){
        if (!requirePermission(loggedInUser, MANAGE_STAFF)) {
            return;
        }

        System.out.println("\n=== Create New Staff ===");
        System.out.println("1. Borrow Staff");
        System.out.println("2. Librarian Staff");
        System.out.println("3. Manager Staff");
        System.out.print("Select role (1-3): ");
        int roleChoice;
        try {
            roleChoice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter 1, 2, or 3.");
            return;
        }

        System.out.print("Staff ID: ");
        String staffId = sc.nextLine().trim();
        System.out.print("Full Name: ");
        String fullName = sc.nextLine().trim();
        System.out.print("Phone: ");
        String phone = sc.nextLine().trim();
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        // Basic validation
        if (staffId.isEmpty() || fullName.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required. Creation cancelled.");
            return;
        }

        for (Staff s : staff) {
            if (s.getStaffId().equalsIgnoreCase(staffId)) {
                System.out.println("Staff ID already exists. Creation cancelled.");
                return;
            }
            if (s.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists. Creation cancelled.");
                return;
            }
        }

        String position;
        if (roleChoice == 1) position = "Borrow Clerk";
        else if (roleChoice == 2) position = "Librarian";
        else if (roleChoice == 3) position = "Manager";
        else {
            System.out.println("Invalid role choice.");
            return;
        }

        Staff newStaff;

        try {
            if (roleChoice == 1) {
                System.out.print("Salary: ");
                float bsSalary = Float.parseFloat(sc.nextLine());
                System.out.print("Working Hours: ");
                int workingHours = Integer.parseInt(sc.nextLine());
                newStaff = new BorrowStaff(staffId, fullName, phone, username, password, position, bsSalary, workingHours);
            } else if (roleChoice == 2) {
                System.out.print("Salary: ");
                float lsSalary = Float.parseFloat(sc.nextLine());
                System.out.print("Bonus: ");
                float bonus = Float.parseFloat(sc.nextLine());
                newStaff = new LibrarianStaff(staffId, fullName, phone, username, password, position, lsSalary, bonus);
            } else {
                System.out.print("Salary: ");
                float managerSalary = Float.parseFloat(sc.nextLine());
                ManagerStaff manager = new ManagerStaff(staffId, fullName, phone, username, password, position);
                manager.setSalary(managerSalary);

                // manager replacement logic
                ManagerStaff existingManager = null;
                for (Staff s : staff) {
                    if (s instanceof ManagerStaff) {
                        existingManager = (ManagerStaff) s;
                        break;
                    }
                }
                if (existingManager != null) {
                    staff.remove(existingManager);
                    System.out.println("Existing manager " + existingManager.getFullName() + " removed.");
                }

                newStaff = manager;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number format. Creation cancelled.");
            return;
        }

        addStaff(newStaff);
        System.out.println("New staff successfully created: " + newStaff.getFullName() + " (" + newStaff.getRole() + ")");
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
