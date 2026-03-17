package user;

public class ActiveStaffFilter implements StaffFilter {

    @Override
    public boolean isActive(Staff staff) {
        return staff != null && staff.isActive();
    }

}
