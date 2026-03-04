package user;
public interface IStaff {
    String getStaffId();
    String getUsername();
    String getPassword();
    String getPosition();
    String getRole();
    boolean isActive();
    boolean checkPassword(String input);
    String getFullName();
    boolean can(String action);
}
