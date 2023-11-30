package memberclass;

import javafx.scene.control.Button;

import java.sql.Date;

public class StudentGUI extends Person {
    private String department;
    private String email;
    private String phone;
    private Date birthday;
    private byte[] image;
    private Button btn;

    public StudentGUI(String name, String ID, String department, String email, String phone, Date birthday, byte[] image) {
        super(name, ID);
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.image = image;
    }

    public StudentGUI(String name, String ID, String password) {
        super(name, ID, password);
        this.btn = new Button("Open Chat");
    }

    public StudentGUI(String name, String ID) {
        super(name, ID);
        this.btn = new Button("Open Chat");
        this.btn.setPrefWidth(200);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Button getBtn() {
        return btn;
    }
}
