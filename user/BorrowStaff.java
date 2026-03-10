package user;

import java.util.Objects;

import controller.Library;
import model.Borrow;

public class BorrowStaff extends Staff {

    private float salary;

    // ====== Constructor using super() for chaining ======
    public BorrowStaff(String staffId, String fullName, String phone,
            String username, String password, String position, float salary) {
        super(staffId, fullName, phone, username, password, position);
        this.setSalary(salary);
    }

    public BorrowStaff(Staff s, float salary) {
        super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword(), s.getPosition());
        this.setSalary(salary);
    }

    @Override
    public boolean can(String action) {
        if (action.equals(Library.BORROW_BOOK) || action.equals(Library.RETURN_BOOK)
                || action.equals(Library.VIEW_BORROW_RECORDS)) {
            return true;
        }
        return false;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if (salary < 250 || salary > 450) {
            System.out.println("Salary must be between 250 and 450.");
        } else {
            this.salary = salary;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        BorrowStaff other = (BorrowStaff) obj;
        return Float.floatToIntBits(salary) == Float.floatToIntBits(other.salary);
    }

    @Override
    public String toString() {
        return super.toString() + " BorrowStaff{" +
                "salary=" + salary +
                '}';
    }
}
