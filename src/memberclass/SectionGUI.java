package memberclass;

public class SectionGUI {
    private String class_id;
    private String className;
    private String room;
    private String time;
    private String day;

    public SectionGUI() {
    }

    public SectionGUI(String class_id, String className, String room, String time, String day) {
        this.class_id = class_id;
        this.className = className;
        this.room = room;
        this.time = time;
        this.day = day;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
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
}
