package user;
public class ManagerStaff extends Staff {

    private float salary;

    @Override
    public boolean can(String action) {
        return true; 
    }

    // ====== Constructor using super() for chaining ======
    public ManagerStaff(Staff s, float salary) {
        super(s.getStaffId(), s.getFullName(), s.getPhone(), s.getUsername(), s.getPassword(), s.getPosition());
        this.setSalary(salary);
    }
    public ManagerStaff(String staffId, String fullName, String phone,
                        String username, String password) {
        super(staffId, fullName, phone, username, password, "Manager");
    };
  
    public float getSalary() {
        return salary;
    }
    
    // ====== Setter ======
    public void setSalary(float salary) {
        if (salary < 1000) {
            System.out.println("Salary must be at least 1000.");
        }else{
            this.salary = salary;
        }
    }
    @Override
    public boolean equals(Object obj) {
       
        ManagerStaff other = (ManagerStaff) obj;

        if (!super.equals(obj))
        {
            return false;
        }else
        {

            if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
            {
                return false;
            }
        }
        return true;
    }

    // ====== toString ======
    @Override
    public String toString() {
        return super.toString() + " ManagerStaff{" +
                "salary=" + salary +
                '}';
    }
}
