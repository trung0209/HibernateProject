package Mapping;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Department {
    private int id;
    private String name;
    private String building;
    private Collection<Professor> professorsById;
    private Collection<Room> roomsById;
    private Collection<Student> studentsById;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Building", length = 45)
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(building, that.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, building);
    }

    @OneToMany(mappedBy = "departmentByProfessor")
    public Collection<Professor> getProfessorsById() {
        return professorsById;
    }

    public void setProfessorsById(Collection<Professor> professorsById) {
        this.professorsById = professorsById;
    }


    @OneToMany(mappedBy = "departmentByBuilding")
    public Collection<Room> getRoomsById() {
        return roomsById;
    }

    public void setRoomsById(Collection<Room> roomsById) {
        this.roomsById = roomsById;
    }

    @OneToMany(mappedBy = "departmentByStudent")
    public Collection<Student> getStudentsById() {
        return studentsById;
    }

    public void setStudentsById(Collection<Student> studentsById) {
        this.studentsById = studentsById;
    }
}
