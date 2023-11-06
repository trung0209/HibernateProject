package Mapping;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Logininfo {
    private String loginId;
    private String password;
    private Professor professorByLoginId;
    private Student studentByLoginId;

    @Id
    @Column(name = "Login_ID", nullable = false, length = 20)
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logininfo logininfo = (Logininfo) o;
        return Objects.equals(loginId, logininfo.loginId) &&
                Objects.equals(password, logininfo.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId, password);
    }

    @OneToOne(mappedBy = "logininfoById")
    public Professor getProfessorByLoginId() {
        return professorByLoginId;
    }

    public void setProfessorByLoginId(Professor professorByLoginId) {
        this.professorByLoginId = professorByLoginId;
    }

    @OneToOne(mappedBy = "logininfoById")
    public Student getStudentByLoginId() {
        return studentByLoginId;
    }

    public void setStudentByLoginId(Student studentByLoginId) {
        this.studentByLoginId = studentByLoginId;
    }
}
