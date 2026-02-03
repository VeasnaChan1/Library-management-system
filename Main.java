

public class Main {
    public static void main(String[] args) {

        // Test Book Class
        Book book1 = new Book("English" , "B1" , "Joe" ,"978-1-73430-269-2" , true );
        Book book2 = new Book("English" , "B1" , "Joe" ,"978-1-73430-269-2" , true);
        Book book3 = new Book("English" , "B2" , "Jow" , "978-1-73430-269-1", true);
        Book book4 = new Book("English" , "B2" , "Jow" , "978-1-73430-269-1", true);
        
        
        // Test Library Class
        Library library = new Library();
        
        library.addBook(book1);
        library.addBook(book2);  
        library.addBook(book3);
        library.addBook(book4);
        
        library.displayAllBooks();
        library.displayBookStatistics();
        
        
        // // Test borrowing and returning
        // System.out.println("\nBorrowing book1...");
        // library.borrowBook("B1", LocalDate.now());
        
        // System.out.println("\nReturning book1...");
        // library.returnBook("B1");

    }

}
