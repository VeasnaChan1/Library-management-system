public class Book {
     static int nextId = 1;  // Static counter for auto-increment
     int id;
     String category;
     String title;
     String author;
     String isbnCode;
        int amount;
     boolean isAvailable;
    
    // Constructor - ID is auto-assigned
    public Book(String category, String title, String author , String isbnCode , boolean isAvailable) {
        this.id = nextId++;  // Auto-increment ID
        this.category = category;
        this.title = title;
        this.author = author;
        this.isbnCode = isbnCode;
        this.isAvailable = isAvailable;
        this.amount = 1;
    } 

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", category=" + category 
                + ", isbnCode=" + isbnCode + ", amount=" + amount + ", isAvailable=" + isAvailable + "]";
    }
    public void borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            amount--;
        }
    }

    public void returnBook() {
        isAvailable = true;
        amount++;
    }
   
}