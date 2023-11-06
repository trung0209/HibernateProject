package Mapping;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Weight {
    private String course;
    private Float weightMid;
    private Float weightFinal;
    private Float weightAssignment;
    private Float weightAttendance;
    private Course courseByCourse;

    @Id
    @Column(name = "course", nullable = false, length = 20)
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Basic
    @Column(name = "WeightMid", nullable = true, precision = 0)
    public Float getWeightMid() {
        return weightMid;
    }

    public void setWeightMid(Float weightMid) {
        this.weightMid = weightMid;
    }

    @Basic
    @Column(name = "WeightFinal", nullable = true, precision = 0)
    public Float getWeightFinal() {
        return weightFinal;
    }

    public void setWeightFinal(Float weightFinal) {
        this.weightFinal = weightFinal;
    }

    @Basic
    @Column(name = "WeightAssignment", nullable = true, precision = 0)
    public Float getWeightAssignment() {
        return weightAssignment;
    }

    public void setWeightAssignment(Float weightAssignment) {
        this.weightAssignment = weightAssignment;
    }

    @Basic
    @Column(name = "WeightAttendance", nullable = true, precision = 0)
    public Float getWeightAttendance() {
        return weightAttendance;
    }

    public void setWeightAttendance(Float weightAttendance) {
        this.weightAttendance = weightAttendance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return Objects.equals(course, weight.course) &&
                Objects.equals(weightMid, weight.weightMid) &&
                Objects.equals(weightFinal, weight.weightFinal) &&
                Objects.equals(weightAssignment, weight.weightAssignment) &&
                Objects.equals(weightAttendance, weight.weightAttendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, weightMid, weightFinal, weightAssignment, weightAttendance);
    }

    @OneToOne(mappedBy = "weightById")
    public Course getCourseByCourse() {
        return courseByCourse;
    }

    public void setCourseByCourse(Course courseByCourse) {
        this.courseByCourse = courseByCourse;
    }
}
