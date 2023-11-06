package Mapping;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Student {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Date birthday;
    private Grade gradeById;
    private Logininfo logininfoById;
    private Department departmentByStudent;

    @ManyToOne
    @JoinColumn(name = "DepartmentID", referencedColumnName = "ID", nullable = false)
    public Department getDepartmentByStudent() {
        return departmentByStudent;
    }

    public void setDepartmentByStudent(Department departmentByStudent) {
        this.departmentByStudent = departmentByStudent;
    }

    @Id
    @Column(name = "ID", nullable = false, length = 20)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 45)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "birthday", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return
                Objects.equals(id, student.id) &&
                        Objects.equals(name, student.name) &&
                        Objects.equals(email, student.email) &&
                        Objects.equals(phone, student.phone) &&
                        Objects.equals(birthday, student.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, phone, birthday);
    }

    @OneToOne(mappedBy = "studentByStudentId")
    public Grade getGradeById() {
        return gradeById;
    }

    public void setGradeById(Grade gradeById) {
        this.gradeById = gradeById;
    }

    @OneToOne
    @JoinColumn(name = "ID", referencedColumnName = "Login_ID", nullable = false)
    public Logininfo getLogininfoById() {
        return logininfoById;
    }

    public void setLogininfoById(Logininfo logininfoById) {
        this.logininfoById = logininfoById;
    }

}
