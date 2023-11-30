package memberclass;

import javafx.scene.control.Button;

public class CourseGUI {
    private String classID;
    private String professor;
    private String professorID;
    private String className;
    private String room;
    private String time;
    private String day;
    private Button button;

    public CourseGUI(String classID, String className, String professor, String professorID) {
        this.classID = classID;
        this.professor = professor;
        this.className = className;
        this.professorID = professorID;
        this.button = new Button("Open Chat");
        button.setPrefWidth(200);
    }

    public CourseGUI(String classID, String professor, String className, String room, String time, String day) {
        this.classID = classID;
        this.professor = professor;
        this.className = className;
        this.room = room;
        this.time = time;
        this.day = day;
        this.button = new Button();
        button.setPrefWidth(100);
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getProfessorID() {
        return professorID;
    }

    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
