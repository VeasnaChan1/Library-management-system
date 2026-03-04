package user;
import controller.Library;

public class BorrowStaff extends Staff {

    // ====== Constructor using super() for chaining ======
    public BorrowStaff(String staffId, String fullName, String phone,
                       String username, String password, String position) {
        super(staffId, fullName, phone, username, password, position);
    }

    // ====== Override Methods ======
    @Override
    public String getRole() { 
        return "BorrowStaff"; 
    }

    @Override
    public boolean can(String action) {
        if(action.equals(Library.BORROW_BOOK) || action.equals(Library.RETURN_BOOK) || action.equals(Library.VIEW_BORROW_RECORDS)) {
            return true;
        }
        return false;
    }

    // ====== toString ======
    @Override
    public String toString() {
        return "BorrowStaff{" +
                "staffId='" + staffId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", position='" + position + '\'' +
                ", active=" + active +
                '}';
    }
}
