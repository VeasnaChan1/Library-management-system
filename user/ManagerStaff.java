package user;
public class ManagerStaff extends Staff {

    // ====== Constructor using super() for chaining ======
    public ManagerStaff(String staffId, String fullName, String phone,
                        String username, String password, String position) {
        super(staffId, fullName, phone, username, password, position);
    }

    // ====== Override Methods ======
    @Override
    public String getRole() { 
        return "ManagerStaff"; 
    }

    @Override
    public boolean can(String action) {
        return true;  // ManagerStaff can do everything (for demo purposes)
    }

    // ====== toString ======
    @Override
    public String toString() {
        return "ManagerStaff{" +
                "staffId='" + staffId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", position='" + position + '\'' +
                ", active=" + active +
                '}';
    }
}
