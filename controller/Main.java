package controller; 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Library library = new Library(1000, 200);
        library.runPolymorphismDemo();
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
