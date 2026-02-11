public class Book {
     private static int nextId = 1;  // Static counter for auto-increment
     private int id;
     private String category;
     private String title;
     private String author;
     private String isbnCode;
     private int amount;
     private boolean isAvailable;
    
    // Constructor - ID is auto-assigned
    public Book(String category, String title, String author , String isbnCode , boolean isAvailable) {
        id = nextId++;  // Auto-increment ID
        this.category = category;
        this.title = title;
        this.author = author;
        this.isbnCode = isbnCode;
        this.isAvailable = isAvailable;
        this.amount = 1;
    } 

    public int getId(){
        return id;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(String isbnCode) {
        this.isbnCode = isbnCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
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