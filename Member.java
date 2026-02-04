public class Member {
    static int nextSeq = 1;
    static final String PREFIX = "LIB11";
    static final int WIDTH = 4;

    int memberID;
    String name;
    int age;
    String gender;

    public Member(String name, int age, String gender) {
        this.memberID = nextSeq++;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // Keep compatibility with existing code that constructs with numeric id
    public Member(int memberID, String name, int age, String gender) {
        this.memberID = memberID;
        this.name = name;
        this.age = age;
        this.gender = gender;
        if (memberID >= nextSeq) nextSeq = memberID + 1;
    }

    public String getFormattedId() {
        return PREFIX + String.format("%0" + WIDTH + "d", memberID);
    }

    @Override
    public String toString() {
        return "Member [memberID=" + getFormattedId() + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
    }
}