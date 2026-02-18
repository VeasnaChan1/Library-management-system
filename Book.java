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
        this.setCategory(category);
        this.setTitle(title);
        this.setAuthor(author);
        this.setIsbnCode(isbnCode);
        this.setAvailable(isAvailable);
        this.setAmount(1);
    } 

    public int getId(){
        return id;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if(category == null || category.trim().isEmpty()) {
            this.category = "Uncategorized";
        } else {
            this.category = category;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            this.title = "Untitled";
        } else {
            this.title = title;
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            this.author = "Unknown Author";
        } else {
            this.author = author;
        }
    }

    public String getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(String isbnCode) {
        if (isbnCode == null || isbnCode.trim().isEmpty()) {
            this.isbnCode = "N/A";
        } else {
            this.isbnCode = isbnCode;
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if(amount < 0) {
            this.amount = 0;
        } else {
            this.amount = amount;
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        if(isAvailable && amount == 0) {
            this.isAvailable = false;
        } else {
            this.isAvailable = isAvailable;
        }
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