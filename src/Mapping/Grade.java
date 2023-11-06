package Mapping;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Grade {
    private String studentId;
    private String courseId;
    private String grade;
    private Integer year;
    private String semester;
    private Student studentByStudentId;
    private Course courseByID;


    @Id
    @Column(name = "Student_ID", nullable = false, length = 20)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "Course_ID", nullable = false, length = 20, insertable = false, updatable = false)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "Grade", nullable = true, length = 2)
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "year", nullable = true)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @Column(name = "semester", nullable = true, length = 6)
    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(studentId, grade1.studentId) &&
                Objects.equals(courseId, grade1.courseId) &&
                Objects.equals(grade, grade1.grade) &&
                Objects.equals(year, grade1.year) &&
                Objects.equals(semester, grade1.semester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, grade, year, semester);
    }

    @OneToOne
    @JoinColumn(name = "Student_ID", referencedColumnName = "ID", nullable = false)
    public Student getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(Student studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }

    @ManyToOne
    @JoinColumn(name = "Course_ID", referencedColumnName = "ID", nullable = false)
    public Course getCourseByID() {
        return courseByID;
    }

    public void setCourseByID(Course courseByID) {
        this.courseByID = courseByID;
    }
}
