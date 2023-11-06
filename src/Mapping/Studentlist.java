package Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Objects;

@Entity
@IdClass(StudentlistPK.class)
public class Studentlist {
    private String course;
    private String studentId;

    @Id
    @Column(name = "course", nullable = false, length = 20)
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Id
    @Column(name = "student_ID", nullable = false, length = 100)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Studentlist that = (Studentlist) o;
        return Objects.equals(course, that.course) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, studentId);
    }
}
