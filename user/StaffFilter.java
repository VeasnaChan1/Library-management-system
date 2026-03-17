package user;

@FunctionalInterface
public interface StaffFilter {
    boolean isActive(Staff staff);
}