package user;
import java.util.Scanner;

public class Staff implements IStaff {
    
    // ====== Fields (Encapsulation) ======
    protected String staffId;
    protected String fullName;
    protected String phone;
    protected String password;
    protected String username;
    protected String position;
    protected boolean active;

    // ====== Constructor ======
    public Staff(String staffId, String fullName, String phone,
                 String username, String password, String position) {
        setStaffId(staffId);
        setFullName(fullName);
        setPhone(phone);
        setUsername(username);
        setPassword(password);
        setPosition(position);
        this.active = true;
    }

    // ====== Getters (inherited by subclasses) ======
    public String getStaffId() { 
        return staffId; 
    }
    
    public String getFullName() { 
        return fullName; 
    }
    
    public String getPhone() { 
        return phone; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public String getPosition() { 
        return position; 
    }
    
    public boolean isActive() { 
        return active; 
    }

    // For login check (simple for lesson)
    @Override
    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }

    // ====== Setters (with simple validation) ======
    public void setStaffId(String staffId) {
        if (isBlank(staffId)) this.staffId = "UNKNOWN";
        else this.staffId = staffId.trim();
    }

    public void setFullName(String fullName) {
        if (isBlank(fullName)) this.fullName = "No Name";
        else this.fullName = fullName.trim();
    }

    public void setPhone(String phone) {
        String p = (phone == null) ? "" : phone.trim();
        // simple validation: only digits, length 8–15
        if (!isDigits(p) || p.length() < 8 || p.length() > 15) this.phone = "00000000";
        else this.phone = p;
    }

    public void setUsername(String username) {
        if (isBlank(username)) this.username = "staff_" + this.staffId;
        else this.username = username.trim();
    }

    public void setPassword(String password) {
        String pw = (password == null) ? "" : password;
        // simple rule for teaching: >= 4 chars
        if (pw.length() < 4) this.password = "0000";
        else this.password = pw;
    }

    public void setPosition(String position) {
        if (isBlank(position)) this.position = "Staff";
        else this.position = position.trim();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // ====== Helper Methods ======
    protected boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    protected boolean isDigits(String s) {
        if (isBlank(s)) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    // ====== Methods to override in subclasses ======
    @Override
    public String getRole() {
        return "Staff";
    }

    @Override
    public boolean can(String action) {
        return false;  // base class has no permissions
    }

    // ====== toString ======
    @Override
    public String toString() {
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", position='" + position + '\'' +
                ", active=" + active +
                '}';
    }
}

