package Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Locktable {
    private int numbering;

    @Id
    @Column(name = "numbering", nullable = false)
    public int getNumbering() {
        return numbering;
    }

    public void setNumbering(int numbering) {
        this.numbering = numbering;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locktable locktable = (Locktable) o;
        return numbering == locktable.numbering;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbering);
    }
}
