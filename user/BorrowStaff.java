package user;

import java.util.Objects;

import controller.Library;
import model.Borrow;

public class BorrowStaff extends Staff {

    private int workingHours;

    // ====== Constructor using super() for chaining ======
    public BorrowStaff(String staffId, String fullName, String phone,
            String username, String password, String position, float salary, int workingHours) {
        super(staffId, fullName, phone, username, password, position);
        setSalary(salary);
        setWorkingHours(workingHours);
    }

    public BorrowStaff(Staff s, float salary, int workingHours) {
        super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword(), s.getPosition());
        setSalary(salary);
        setWorkingHours(workingHours);
    }

    @Override
    public boolean can(String action) {
        if (action.equals(Library.BORROW_BOOK) || action.equals(Library.RETURN_BOOK)
                || action.equals(Library.VIEW_BORROW_RECORDS)) {
            return true;
        }
        return false;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        if (workingHours < 10 || workingHours > 12) {
            System.out.println("Working hours must be between 10 and 12.");
        } else {
            this.workingHours = workingHours;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        BorrowStaff other = (BorrowStaff) obj;
        return workingHours == other.workingHours;
    }

    @Override
    public String toString() {
        return super.toString() + " BorrowStaff{" +
                "workingHours=" + workingHours +
                '}';
    }
}
