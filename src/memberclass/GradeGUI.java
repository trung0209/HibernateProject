package memberclass;

public class GradeGUI {
    private String studentID;
    private String studentName;
    private String string_mid;
    private String string_final;
    private String string_attend;
    private String string_assign;
    private String string_average;

    public GradeGUI(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    public GradeGUI(String studentID, String studentName, String string_mid,
                    String string_final, String string_attend, String string_assign, String string_average) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.string_mid = string_mid;
        this.string_final = string_final;
        this.string_attend = string_attend;
        this.string_assign = string_assign;
        this.string_average = string_average;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getString_mid() {
        return string_mid;
    }

    public void setString_mid(String string_mid) {
        this.string_mid = string_mid;
    }

    public String getString_final() {
        return string_final;
    }

    public void setString_final(String string_final) {
        this.string_final = string_final;
    }

    public String getString_attend() {
        return string_attend;
    }

    public void setString_attend(String string_attend) {
        this.string_attend = string_attend;
    }

    public String getString_assign() {
        return string_assign;
    }

    public void setString_assign(String string_assign) {
        this.string_assign = string_assign;
    }

    public String getString_average() {
        return string_average;
    }

    public void setString_average(String string_average) {
        this.string_average = string_average;
    }
}
