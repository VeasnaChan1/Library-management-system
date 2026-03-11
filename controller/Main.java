package controller;
import java.util.ArrayList;
import java.util.Scanner;
import user.BorrowStaff;
import user.LibrarianStaff;
import user.ManagerStaff;
import user.Staff;

public class Main {
    public static void main(String[] args) {
        // ====== Polymorphism Demo (PDF Section 5) ======
        System.out.println("=== Polymorphism Demo: One list, many role behaviors ===");
        ArrayList<Staff> demoStaff = new ArrayList<>();
        demoStaff.add(new BorrowStaff("D001", "Alice", "012345678", "alice", "pass1", "Borrow Clerk", 350f, 11));
        demoStaff.add(new LibrarianStaff("D002", "Bob", "098765432", "bob", "pass2", "Librarian", 500f, 50f));
        demoStaff.add(new ManagerStaff("D003", "Charlie", "011223344", "charlie", "pass3", "Manager"));

        String[] actions = {Library.BORROW_BOOK, Library.ADD_BOOK, Library.MANAGE_STAFF};
        for (Staff s : demoStaff) {
            for (String action : actions) {
                System.out.println(s.getUsername() + " can " + action + "? " + s.can(action));
            }
        }
        System.out.println();

        Library library = new Library(1000, 200);
        library.populateSampleData();

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            if (library.getLoggedInUser() == null) {
                Library.printLoginMenu();
                System.out.print("Choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Staff ID: ");
                        String id = sc.nextLine();
                        System.out.print("Password: ");
                        String pw = sc.nextLine();
                        library.staffLogin(id, pw);
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                Library.printMenu();
                System.out.print("Choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        library.displayAllBooks();
                        break;
                    case 2:
                        library.displayBookStatistics();
                        break;
                    case 3:
                        library.displayAllMembers();
                        break;
                    case 4:
                        Library.addMemberInteractive(library, sc);
                        break;
                    case 5:
                        Library.addBookInteractive(library, sc);
                        break;
                    case 6:
                        Library.updateMemberNameInteractive(library, sc);
                        break;
                    case 7:
                        Library.borrowInteractive(library, sc);
                        break;
                    case 8:
                        Library.returnInteractive(library, sc);
                        break;
                    case 9:
                        library.createStaff(sc);
                        break;
                    case 10:
                        library.displayAllStaff();
                        break;
                    case 11: // logout
                        library.staffLogout();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } while (choice != 0);

        sc.close();
    }
}
