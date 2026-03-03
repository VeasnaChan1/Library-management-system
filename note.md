📘 Book class
Attributes

id (int)

title (String)

author (String)

isAvailable (boolean)

Methods

borrowBook()

returnBook()

toString()

👤 Member class
Attributes

memberId (int)

name (String)

Methods

getMemberId()

getName()

(Keep it simple — borrowing logic stays in Library 👍)

🏛️ Library class (THE BRAIN 🧠)
Attributes

ArrayList<Book> books

ArrayList<Member> members

Methods

addBook(Book book)

addMember(Member member)

showBooks()

borrowBook(int bookId)

returnBook(int bookId)

👉 All logic lives here (teachers LOVE this separation).

▶️ Main class
Responsibilities

Display menu

Get user input

Call Library methods

🚫 No business logic here.

-static method'
-setter functiom 

ID	        Password	Role	Permissions you can try
STAFF001	pass1	    BorrowStaff	borrow/return only
STAFF002	pass2	    LibrarianStaff	add book, update members, view stats
STAFF003	pass3	    ManagerStaff	none of the above (but could add manage‑staff option)
