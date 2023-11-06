package memberclass;

import java.sql.Date;

public class Student_GUI extends Person {
    private String department;
    private String email;
    private String phone;
    private Date birthday;

    public Student_GUI(String name, String ID, String department, String email, String phone, Date birthday) {
        super(name, ID);
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
