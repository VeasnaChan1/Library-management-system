package user;
import controller.Library;

public class LibrarianStaff extends Staff {

    // ====== Constructor using super() for chaining ======
    public LibrarianStaff(String staffId, String fullName, String phone,
                          String username, String password, String position) {
        super(staffId, fullName, phone, username, password, position);
    }

    @Override
    public String getRole() { 
        return "LibrarianStaff"; 
    }

    @Override
    public boolean can(String action) {
        if(action.equals(Library.ADD_BOOK) || action.equals(Library.UPDATE_CATALOG) || action.equals(Library.VIEW_INVENTORY)) {
            return true;
        }
        return false;
    }

    // ====== toString ======
    @Override
    public String toString() {
        return "LibrarianStaff{" +
                "staffId='" + staffId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", position='" + position + '\'' +
                ", active=" + active +
                '}';
    }
}
