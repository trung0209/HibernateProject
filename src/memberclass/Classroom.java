package memberclass;

import javafx.scene.control.Button;

public class Classroom {
    private String class_ID;
    private String class_name;
    private String credit;
    private Button grade_btn;
    private Button delete_btn;
    private Button button2;

    public Classroom(String class_ID, String class_name, String credit) {
        this.class_ID = class_ID;
        this.class_name = class_name;
        this.credit = credit;
        this.grade_btn = new Button("Grade");
        grade_btn.setPrefWidth(100);
        this.delete_btn = new Button("Delete");
        delete_btn.setPrefWidth(100);
        this.button2 = new Button("Student List");
        button2.setPrefWidth(100);
    }

    public Button getButton2() {
        return button2;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getClass_ID() {
        return class_ID;
    }

    public void setClass_ID(String class_ID) {
        this.class_ID = class_ID;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Button getGrade_btn() {
        return grade_btn;
    }

    public void setGrade_btn(Button grade_btn) {
        this.grade_btn = grade_btn;
    }

    public Button getDelete_btn() {
        return delete_btn;
    }

    public void setDelete_btn(Button delete_btn) {
        this.delete_btn = delete_btn;
    }
}
