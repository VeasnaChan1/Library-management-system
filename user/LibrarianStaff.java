package user;

import controller.Library;

public class LibrarianStaff extends Staff {

    private float salary;

    // ====== Constructor using super() for chaining ======
    public LibrarianStaff(String staffId, String fullName, String phone,
            String username, String password, String position, float salary) {
        super(staffId, fullName, phone, username, password, position);
        this.setSalary(salary);
    }

    public LibrarianStaff(Staff s, float salary) {
        super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword(), s.getPosition());
        this.setSalary(salary);
    }

    @Override
    public boolean can(String action) {
        if (action.equals(Library.ADD_BOOK) || action.equals(Library.UPDATE_CATALOG)
                || action.equals(Library.VIEW_INVENTORY)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        LibrarianStaff other = (LibrarianStaff) obj;
        return Float.floatToIntBits(salary) == Float.floatToIntBits(other.salary);
    }

    // ====== Getters and Setters ======
    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        if (salary < 450 || salary > 750) {
            System.out.println("Salary must be between 450 and 750.");
        } else {
            this.salary = salary;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " LibrarianStaff{" +
                "salary=" + salary +
                '}';
    }
}
