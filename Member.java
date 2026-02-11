public class Member {
    static int nextSeq = 1;
    static final String PREFIX = "LIB11";
    static final int WIDTH = 4;

    private int memberID;
    private String name;
    private int age;
    private String gender;

    public Member(String name, int age, String gender) {
        this.memberID = nextSeq++;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Keep compatibility with existing code that constructs with numeric id
    public Member(int memberID, String name, int age, String gender) {
        this.memberID = memberID;
        this.setName(name);
        this.setAge(age);
        this.setGender(gender);
        if (memberID >= nextSeq) nextSeq = memberID + 1;
    }

    public String getFormattedId() {
        return PREFIX + String.format("%0" + WIDTH + "d", memberID);
    }

    public int getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Member [memberID=" + getFormattedId() + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
    }
}