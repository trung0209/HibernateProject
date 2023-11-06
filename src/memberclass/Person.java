package memberclass;

public class Person {
    String name;
    String ID;
    private String password;

    public Person(String name, String ID, String password) {
        this.name = name;
        this.ID = ID;
        this.password = password;
    }

    public Person(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", ID='" + ID + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
