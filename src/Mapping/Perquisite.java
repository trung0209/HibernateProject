package Mapping;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Perquisite {
    private String courseId;
    private String prequisiteId;

    @Id
    @Column(name = "course_id", nullable = false, length = 20)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "prequisite_id", length = 20)
    public String getPrequisiteId() {
        return prequisiteId;
    }

    public void setPrequisiteId(String prequisiteId) {
        this.prequisiteId = prequisiteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perquisite that = (Perquisite) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(prequisiteId, that.prequisiteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, prequisiteId);
    }
}
