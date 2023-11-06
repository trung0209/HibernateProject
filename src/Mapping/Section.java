package Mapping;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Section {
    private int id;
    private String courseid;
    private String roomName;
    private Time start;
    private Time end;
    private String day;
    private Course courseByCourseid;
    private Room roomByRoomName;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "courseid", nullable = false, length = 20, insertable = false, updatable = false)
    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    @Basic
    @Column(name = "Room_name", nullable = false, length = 45, insertable = false, updatable = false)
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Basic
    @Column(name = "start", nullable = false)
    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    @Basic
    @Column(name = "end", nullable = false)
    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    @Basic
    @Column(name = "Day", nullable = false, length = 50)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return id == section.id &&
                Objects.equals(courseid, section.courseid) &&
                Objects.equals(roomName, section.roomName) &&
                Objects.equals(start, section.start) &&
                Objects.equals(end, section.end) &&
                Objects.equals(day, section.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseid, roomName, start, end, day);
    }

    @ManyToOne
    @JoinColumn(name = "courseid", referencedColumnName = "ID", nullable = false)
    public Course getCourseByCourseid() {
        return courseByCourseid;
    }

    public void setCourseByCourseid(Course courseByCourseid) {
        this.courseByCourseid = courseByCourseid;
    }

    @ManyToOne
    @JoinColumn(name = "Room_name", referencedColumnName = "Name", nullable = false)
    public Room getRoomByRoomName() {
        return roomByRoomName;
    }

    public void setRoomByRoomName(Room roomByRoomName) {
        this.roomByRoomName = roomByRoomName;
    }
}


