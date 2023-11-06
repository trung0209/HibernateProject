package Mapping;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Course {
    private String id;
    private String professorId;
    private String courseName;
    private Integer credit;
    private Weight weightById;
    private Professor professorByProfessorId;
    private Collection<Grade> gradesById;
    private Collection<Section> sectionById;

    @Id
    @Column(name = "ID", nullable = false, length = 20, insertable = false, updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Professor_ID", nullable = true, length = 20, insertable = false, updatable = false)
    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    @Basic
    @Column(name = "CourseName", nullable = false, length = 45)
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Basic
    @Column(name = "Credit", nullable = true)
    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(professorId, course.professorId) &&
                Objects.equals(courseName, course.courseName) &&
                Objects.equals(credit, course.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, professorId, courseName, credit);
    }

    @OneToOne
    @JoinColumn(name = "ID", referencedColumnName = "course", nullable = false)
    public Weight getWeightById() {
        return weightById;
    }

    public void setWeightById(Weight weightById) {
        this.weightById = weightById;
    }

    @ManyToOne
    @JoinColumn(name = "Professor_ID", referencedColumnName = "ID", nullable = true)
    public Professor getProfessorByProfessorId() {
        return professorByProfessorId;
    }

    public void setProfessorByProfessorId(Professor professorByProfessorId) {
        this.professorByProfessorId = professorByProfessorId;
    }

    @OneToMany(mappedBy = "courseByID")
    public Collection<Grade> getGradesById() {
        return gradesById;
    }

    public void setGradesById(Collection<Grade> gradesById) {
        this.gradesById = gradesById;
    }

    @OneToMany(mappedBy = "courseByCourseid")
    public Collection<Section> getSectionById() {
        return sectionById;
    }

    public void setSectionById(Collection<Section> sectionById) {
        this.sectionById = sectionById;
    }


}
