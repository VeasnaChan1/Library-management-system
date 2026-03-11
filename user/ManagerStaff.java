package user;
public class ManagerStaff extends Staff {

    private float positionSalary;

    @Override
    public boolean can(String action) {
        return true;
    }

    // ====== Constructor using super() for chaining ======
    public ManagerStaff(Staff s, float positionSalary) {
        super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword(), s.getPosition());
        this.setPositionSalary(positionSalary);
    }
    
    public ManagerStaff(String staffId, String fullName, String phone,
                        String username, String password, String position) {
        super(staffId, fullName, phone, username, password, position);
    };
  
    public float getPositionSalary() {
        return positionSalary;
    }

    public void setPositionSalary(float positionSalary) {
        if (positionSalary < 100 || positionSalary > 250) {
            System.out.println("Position salary must be between 100 and 250.");
        } else {
            this.positionSalary = positionSalary;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        ManagerStaff other = (ManagerStaff) obj;
        return Float.floatToIntBits(positionSalary) == Float.floatToIntBits(other.positionSalary);
    }

    // ====== toString ======
    @Override
    public String toString() {
        return super.toString() + " ManagerStaff{" +
                "positionSalary=" + positionSalary +
                '}';
    }
}
