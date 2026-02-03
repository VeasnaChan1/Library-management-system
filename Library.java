class Library {
    Book[] books;   // array to store books
    int count;      // number of books currently in the array
    int MAX_SIZE;   // maximum number of books

    // Constructor with custom capacity
    Library(int capacity) {
        this.MAX_SIZE = capacity;
        books = new Book[MAX_SIZE]; // initialize array
        count = 0;                  // no books yet
    }

    // Default constructor with capacity of 100
    Library() {
        this(3);
    }

    void addBook(Book book) {
        if (count < MAX_SIZE) {
            books[count] = book;
            count++;
        } else if(count >= MAX_SIZE) {
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
        System.out.println("Total titles: " + count);
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

}
