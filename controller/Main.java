package controller; 
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Library library = new Library(1000, 200);
        library.populateSampleData();
      

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            if (library.getLoggedInUser() == null) {
                Library.printLoginMenu();
                choice = readInt(sc, "Choice: ", 0, 1);
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
                choice = readInt(sc, "Choice: ", 0, 11);

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

    private static int readInt(Scanner sc, String prompt, int min, int max) {
        int choice;
        while (true) {
            System.out.print(prompt);
            try {
                choice = sc.nextInt();
                sc.nextLine();

                if (choice < min || choice > max) {
                    System.out.printf("Invalid choice. Enter a number between %d and %d.%n", min, max);
                    continue;
                }
                return choice;
            } catch (java.util.InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Error reading input. Please try again.");
            }
        }
    }
}
