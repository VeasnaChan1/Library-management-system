public class Member {
    static int nextSeq = 1;
    static final String PREFIX = "LIB11";
    static final int WIDTH = 4;

    private String memberID;
    private String name;
    private int age;
    private String gender;

    private static  String generateNextMemberID() {
        String id = PREFIX + String.format("%0" + WIDTH + "d", nextSeq);
        nextSeq++;
        return id;
    }

    public Member(String name, int age, String gender) {
        memberID = generateNextMemberID();
        this.setName(name);        
        this.setAge(age);
        this.setGender(gender);
    }

    // Constructor that accepts an explicit member ID string
    public Member(String memberID, String name, int age, String gender) {
        this.memberID = memberID;
        this.setName(name);
        this.setAge(age);
        this.setGender(gender);
        try {
            int seq = Integer.parseInt(memberID.replace(PREFIX, ""));
            if (seq >= nextSeq) nextSeq = seq + 1;
        } catch (Exception e) {
            // ignore parse errors and leave nextSeq as-is
        }
    }

    public String getMemberID() {
        return memberID;
    }

    // Backwards-compatible accessor previously named getFormattedId
    public String getFormattedId() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.trim().isEmpty()) {
            this.name = "Unknown Name";
        } else {
            this.name = name;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age < 0) {
            this.age = 0;
        } else {
            this.age = age;
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if(gender == null || gender.trim().isEmpty()) {
            this.gender = "Unknown Gender";
        } else {
            this.gender = gender;
        }
    }

    @Override
    public String toString() {
        return "Member [memberID=" + memberID + ", name=" + name + ", age=" + age + ", gender=" + gender + "]";
    }
}