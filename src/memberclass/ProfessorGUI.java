package memberclass;

import java.util.ArrayList;

public class ProfessorGUI extends Person {
    protected ArrayList<Classroom> classrooms_List;
    private int total_class;
    private int dept_id;

    public ProfessorGUI(String name, String ID, int dept_id) {
        super(name, ID);
        total_class = 0;
        this.dept_id = dept_id;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public int getTotal_class() {
        return total_class;
    }

    public void setTotal_class(int total_class) {
        this.total_class = total_class;
    }
}
