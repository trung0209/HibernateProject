package Mapping;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Room {
    private String name;
    private int building;
    private Integer capacity;
    private Department departmentByBuilding;
    private Collection<Section> sectionsByName;

    @Id
    @Column(name = "Name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Building", nullable = false, insertable = false, updatable = false)
    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    @Basic
    @Column(name = "Capacity", nullable = true)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return building == room.building &&
                Objects.equals(name, room.name) &&
                Objects.equals(capacity, room.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, building, capacity);
    }

    @ManyToOne
    @JoinColumn(name = "Building", referencedColumnName = "ID", nullable = false)
    public Department getDepartmentByBuilding() {
        return departmentByBuilding;
    }

    public void setDepartmentByBuilding(Department departmentByBuilding) {
        this.departmentByBuilding = departmentByBuilding;
    }

    @OneToMany(mappedBy = "roomByRoomName")
    public Collection<Section> getSectionsByName() {
        return sectionsByName;
    }

    public void setSectionsByName(Collection<Section> sectionsByName) {
        this.sectionsByName = sectionsByName;
    }
}
