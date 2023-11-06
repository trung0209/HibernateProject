package Mapping;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Professor {
    private String id;
    private int departmentId;
    private String name;
    private String email;
    private String phone;
    private Collection<Course> coursesById;
    private Logininfo logininfoById;
    private Department departmentByProfessor;

    @Id
    @Column(name = "ID", nullable = false, length = 20)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DepartmentID", nullable = false, insertable = false, updatable = false)
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Email", nullable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "Phone", nullable = true, length = 45)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return departmentId == professor.departmentId &&
                Objects.equals(id, professor.id) &&
                Objects.equals(name, professor.name) &&
                Objects.equals(email, professor.email) &&
                Objects.equals(phone, professor.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departmentId, name, email, phone);
    }

    @OneToMany(mappedBy = "professorByProfessorId")
    public Collection<Course> getCoursesById() {
        return coursesById;
    }

    public void setCoursesById(Collection<Course> coursesById) {
        this.coursesById = coursesById;
    }

    @OneToOne
    @JoinColumn(name = "ID", referencedColumnName = "Login_ID", nullable = false)
    public Logininfo getLogininfoById() {
        return logininfoById;
    }

    public void setLogininfoById(Logininfo logininfoById) {
        this.logininfoById = logininfoById;
    }

    @ManyToOne
    @JoinColumn(name = "DepartmentID", referencedColumnName = "ID", nullable = false)
    public Department getDepartmentByProfessor() {
        return departmentByProfessor;
    }

    public void setDepartmentByProfessor(Department departmentByProfessor) {
        this.departmentByProfessor = departmentByProfessor;
    }
}
