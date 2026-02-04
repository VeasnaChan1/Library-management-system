public class Main {
    public static void main(String[] args) {
        Book book1 = new Book("English" , "B1" , "Joe" ,"978-1-73430-269-2" , true );
        Book book2 = new Book("English" , "B1" , "Joe" ,"978-1-73430-269-2" , true);
        Book book3 = new Book("English" , "B2" , "Jow" , "978-1-73430-269-1", true);
        Book book4 = new Book("English" , "B2" , "Jow" , "978-1-73430-269-1", true);

        Library library = new Library();

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);

        library.displayAllBooks();
        library.displayBookStatistics();

        Member m1 = new Member("John Doe", 25, "M");
        Member m2 = new Member("Jane Smith", 22, "F");


        System.out.println("\nLibrary members:");
        System.out.println(m1);
        System.out.println(m2);

        //Borrow book
        System.out.println("\n==Borrowing infromation==");
        Borrow borrow1 = library.borrowBook(book3.id, m1, java.time.LocalDate.now());
        displayBorrowRecord(borrow1);

        Borrow borrow2 = library.borrowBook(book2.id, m2, java.time.LocalDate.now());
        displayBorrowRecord(borrow2);

        System.out.println("\n=== Books after borrowing ===");
        library.displayAllBooks();
        library.displayBookStatistics();

        Borrow Returned = library.returnBook(m1.memberID, book3.id, java.time.LocalDate.now().plusDays(7));
        Borrow Returned1 = library.returnBook(m2.memberID, book2.id, java.time.LocalDate.now().plusDays(9));
    

        System.out.println("\n=== Books after returning ===");
        library.displayAllBooks();
        library.displayBookStatistics();
    }

    static void displayBorrowRecord(Borrow borrow) {
    if (borrow != null) {
        System.out.println("Member ID: " + Member.PREFIX + String.format("%0" + Member.WIDTH + "d", borrow.memberId));
        System.out.println("Member Name: " + borrow.memberName);
        System.out.println("Book ID: " + borrow.book.id);
        System.out.println("Book Title: " + borrow.book.title);
        System.out.println("Borrow Date: " + borrow.borrowDate);
        System.out.println("Status: " + borrow.status);
        System.out.println("---");
    }
}
}