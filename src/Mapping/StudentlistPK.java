package Mapping;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class StudentlistPK implements Serializable {
    private String course;
    private String studentId;

    @Column(name = "course", nullable = false, length = 20)
    @Id
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Column(name = "student_ID", nullable = false, length = 100)
    @Id
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
        StudentlistPK that = (StudentlistPK) o;
        return Objects.equals(course, that.course) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, studentId);
    }
}
