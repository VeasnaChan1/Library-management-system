public class Main {
    public static void main(String[] args) {


        Library library = new Library(1000, 200);
        // populate sample data (books + members)
        library.populateSampleData();

        library.displayAllBooks();
        library.displayBookStatistics();

        // Display all members

        library.displayAllMembers();
        library.updateName("LIB110001", "Eychhean");
        library.updateAge("LIB110001", 22);
        library.updateGender("LIB110002", "Male");
        library.displayAllMembers();
       
    

        // Display all members after update
        library.displayAllMembers();

        // Borrow book
        System.out.println("\n==Borrowing information==");
        Member m1 = library.findMemberById("LIB110001");
        Member m2 = library.findMemberById("LIB110002");
        Book book3 = library.findBookByISBN("978-1-73430-269-2");
        Book book2 = library.findBookByISBN("978-1-73430-269-1");

        Borrow borrow1 = library.borrowBook(book3.getId(), m1, java.time.LocalDate.now());
        Library.displayBorrowRecord(borrow1);

        Borrow borrow2 = library.borrowBook(book2.getId(), m2, java.time.LocalDate.now());
        Library.displayBorrowRecord(borrow2);

        Borrow borrow3 = library.borrowBook(10, m2, java.time.LocalDate.now());
        Library.displayBorrowRecord(borrow3);

        // System.out.println("\n=== Books after borrowing ===");
        // library.displayAllBooks();
        // library.displayBookStatistics();

        // Borrow Returned = library.returnBook(m1.getMemberID(), book3.getId(), java.time.LocalDate.now().plusDays(7));
        // Borrow Returned1 = library.returnBook(m2.getMemberID(), book2.getId(), java.time.LocalDate.now().plusDays(9));
    

        // System.out.println("\n=== Books after returning ===");
        // library.displayAllBooks();
        // library.displayBookStatistics();
        }

}