package user;

import controller.Library;

public class LibrarianStaff extends Staff {

    private float bonus;

    // ====== Constructor using super() for chaining ======
    public LibrarianStaff(String staffId, String fullName, String phone,
            String username, String password, String position, float salary, float bonus) {
        super(staffId, fullName, phone, username, password, position);
        setSalary(salary);
        setBonus(bonus);
    }

    public LibrarianStaff(Staff s, float salary, float bonus) {
        super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword(), s.getPosition());
        setSalary(salary);
        setBonus(bonus);
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
        return Float.floatToIntBits(bonus) == Float.floatToIntBits(other.bonus);
    }

    // ====== Getters and Setters ======
    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        if (bonus < 0 || bonus > 50) {
            System.out.println("Bonus must not be negative.");
        } else {
            this.bonus = bonus;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " LibrarianStaff{" +
                "bonus=" + bonus +
                '}';
    }
}
