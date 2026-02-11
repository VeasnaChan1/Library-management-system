public class Main {
    public static void main(String[] args) {


        Book book1 = new Book("English" , "B1" , "Joe" ,"978-1-73430-269-1" , true );
        Book book2 = new Book("English" , "B1" , "Joe" ,"978-1-73430-269-1" , true);
        Book book3 = new Book("English" , "B2" , "Jow" , "978-1-73430-269-2", true);
        Book book4 = new Book("English" , "B2" , "Jow" , "978-1-73430-269-2", true);
        Book book5 = new Book("English" , "C1" , "Jow" , "978-1-73430-269-5", true);
  
        Library library = new Library();

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        library.displayAllBooks();
        library.displayBookStatistics();

        Member m1 = new Member("John Doe", 25, "M");
        Member m2 = new Member("Jane Smith", 22, "F");
        Member m3 = new Member("Jany Smith", 22, "F");


        System.out.println("\nLibrary members:");
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m3);
        // library.updateMemberName("LIB110001", "eychhean");

        //Borrow book
        System.out.println("\n==Borrowing infromation==");
        Borrow borrow1 = library.borrowBook(book3.getId(), m1, java.time.LocalDate.now());
        displayBorrowRecord(borrow1);

        Borrow borrow2 = library.borrowBook(book2.getId(), m2, java.time.LocalDate.now());
        displayBorrowRecord(borrow2);

        
        Borrow borrow3 = library.borrowBook(10, m2, java.time.LocalDate.now());
        displayBorrowRecord(borrow3);

        System.out.println("\n=== Books after borrowing ===");
        library.displayAllBooks();
        library.displayBookStatistics();

        Borrow Returned = library.returnBook(m1.getMemberID(), book3.getId(), java.time.LocalDate.now().plusDays(7));
        Borrow Returned1 = library.returnBook(m2.getMemberID(), book2.getId(), java.time.LocalDate.now().plusDays(9));
    

        System.out.println("\n=== Books after returning ===");
        library.displayAllBooks();
        library.displayBookStatistics();
        }

    static void displayBorrowRecord(Borrow borrow) {
    if (borrow != null) {
        System.out.println("Member ID: " + Member.PREFIX + String.format("%0" + Member.WIDTH + "d", borrow.getMemberId()));
        System.out.println("Member Name: " + borrow.getMemberName());
        System.out.println("Book ID: " + borrow.getBook().getId());
        System.out.println("Book Title: " + borrow.getBook().getTitle());
        System.out.println("Borrow Date: " + borrow.getBorrowDate());
        System.out.println("Status: " + borrow.getStatus());
        System.out.println("---");
    }
}
}