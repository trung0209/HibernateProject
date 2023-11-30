package ControllerFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import memberclass.CourseGUI;
import memberclass.StudentGUI;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CourseRegisterController implements Initializable {
    private StudentGUI studentInfo;
    ArrayList<CourseGUI> list_course;
    ArrayList<CourseGUI> added_course;
    String previous;
    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private TableView<CourseGUI> registerTable;

    @FXML
    private TableColumn<CourseGUI, String> idCol1;

    @FXML
    private TableColumn<CourseGUI, String> nameCol1;

    @FXML
    private TableColumn<CourseGUI, String> roomCol1;

    @FXML
    private TableColumn<CourseGUI, String> timeCol1;

    @FXML
    private TableColumn<CourseGUI, String> dayCol1;

    @FXML
    private TableColumn<CourseGUI, String> deleteCol;

    @FXML
    private TableView<CourseGUI> courseTable;

    @FXML
    private TableColumn<CourseGUI, String> idCol;

    @FXML
    private TableColumn<CourseGUI, String> nameCol;

    @FXML
    private TableColumn<CourseGUI, String> roomCol;

    @FXML
    private TableColumn<CourseGUI, String> timeCol;

    @FXML
    private TableColumn<CourseGUI, String> dayCol;

    @FXML
    private TableColumn<CourseGUI, String> registerCol;

    @FXML
    private TableColumn<CourseGUI, String> professorCol, professorCol1;
    @FXML
    private ComboBox<String> deptList;

    @FXML
    private Button searchBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        previous = "";
        nameField.setText(studentInfo.getName());
        idField.setText(studentInfo.getID());
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("select Name from classmanagement.department");
        List<Object> list = query.list();
        ArrayList<String> temp = new ArrayList<>();
        for (Object object : list) {
            temp.add(object.toString());
        }
        ObservableList<String> depName = FXCollections.observableArrayList(temp);
        idCol.setCellValueFactory(new PropertyValueFactory<>("classID"));
        professorCol.setCellValueFactory(new PropertyValueFactory<>("professor"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("className"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        registerCol.setCellValueFactory(new PropertyValueFactory<>("button"));
        query = session.createNativeQuery("SELECT S.ID, S.Name, S.CourseName, S.Room, S.TimeRanges, S.Days\n" +
                "FROM classmanagement.student_schedule as S\n" +
                "where (S.ID,?) in (select course, student_ID from classmanagement.studentlist)");
        query.setParameter(1, studentInfo.getID());

        idCol1.setCellValueFactory(new PropertyValueFactory<>("classID"));
        professorCol1.setCellValueFactory(new PropertyValueFactory<>("professor"));
        nameCol1.setCellValueFactory(new PropertyValueFactory<>("className"));
        roomCol1.setCellValueFactory(new PropertyValueFactory<>("room"));
        timeCol1.setCellValueFactory(new PropertyValueFactory<>("time"));
        dayCol1.setCellValueFactory(new PropertyValueFactory<>("day"));
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("button"));
        List<Object[]> test = query.list();
        added_course = new ArrayList<>();
        for (Object[] x : test) {
            CourseGUI courseGUI = new CourseGUI(x[0].toString(), x[1].toString(), x[2].toString(),
                    x[3].toString(), x[4].toString(), x[5].toString());
            courseGUI.setButton(new Button("Delete"));
            courseGUI.getButton().setOnAction(this::delete_course);
            added_course.add(courseGUI);
        }
        ObservableList<CourseGUI> list1 = FXCollections.observableArrayList(added_course);
        registerTable.setItems(list1);

        deptList.setItems(depName);
        searchBtn.setOnAction(this::setSearchBtn);
        list_course = new ArrayList<>();
    }

    public StudentGUI getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentGUI studentInfo) {
        this.studentInfo = studentInfo;
    }

    private void delete_course(ActionEvent actionEvent) {
        CourseGUI course = registerTable.getSelectionModel().getSelectedItem();
        if (course != null) {
            list_course.remove(course);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery("DELETE FROM classmanagement.studentlist WHERE course = ? and student_ID = ?");
            query.setParameter(1, course.getClassID());
            query.setParameter(2, studentInfo.getID());
            query.executeUpdate();
            transaction.commit();
            refresh();
        }
    }

    private void refresh() {
        added_course.clear();
        list_course.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("SELECT S.ID, S.Name, S.CourseName, S.Room, S.TimeRanges, S.Days\n" +
                "FROM classmanagement.student_schedule as S\n" +
                "where (S.ID,?) in (select course, student_ID from classmanagement.studentlist)");
        query.setParameter(1, studentInfo.getID());

        List<Object[]> test = query.list();
        added_course = new ArrayList<>();
        for (Object[] x : test) {
            CourseGUI courseGUI = new CourseGUI(x[0].toString(), x[1].toString(), x[2].toString(),
                    x[3].toString(), x[4].toString(), x[5].toString());
            courseGUI.setButton(new Button("Delete"));
            courseGUI.getButton().setOnAction(this::delete_course);
            added_course.add(courseGUI);
        }
        ObservableList<CourseGUI> list1 = FXCollections.observableArrayList(added_course);
        registerTable.setItems(list1);

        query = session.createNativeQuery("SELECT S.ID, S.Name, S.CourseName, S.Room, S.TimeRanges, S.Days\n" +
                "FROM classmanagement.student_schedule as S\n" +
                "where S.Department = ? and (S.ID,?) not in ( select * from classmanagement.studentlist)");
        query.setParameter(1, deptList.getValue());
        query.setParameter(2, studentInfo.getID());
        if (deptList.getValue() != null) {
            test = query.list();
            for (Object[] x : test) {
                CourseGUI courseGUI = new CourseGUI(x[0].toString(), x[1].toString(), x[2].toString(),
                        x[3].toString(), x[4].toString(), x[5].toString());
                courseGUI.setButton(new Button("Register"));
                courseGUI.getButton().setOnAction(this::add_course);
                list_course.add(courseGUI);
            }
            list1 = FXCollections.observableArrayList(list_course);
            courseTable.setItems(list1);
        }
    }

    private void add_course(ActionEvent actionEvent) {
        CourseGUI course = courseTable.getSelectionModel().getSelectedItem();
        if (course != null) {
            list_course.remove(course);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery("INSERT INTO classmanagement.studentlist (course, student_ID) VALUES (?,?)");
            query.setParameter(1, course.getClassID());
            query.setParameter(2, studentInfo.getID());
            query.executeUpdate();
            transaction.commit();
            refresh();
        }
    }

    private void setSearchBtn(ActionEvent actionEvent) {
        if (deptList.getValue().equals("Find your department")) {
            return;
        }
        list_course.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("SELECT S.ID, S.Name, S.CourseName, S.Room, S.TimeRanges, S.Days\n" +
                "FROM classmanagement.student_schedule as S\n" +
                "where S.Department = ? and (S.ID,?) not in ( select * from classmanagement.studentlist)");
        query.setParameter(1, deptList.getValue());
        query.setParameter(2, studentInfo.getID());
        List<Object[]> test = query.list();

        for (Object[] x : test) {
            CourseGUI courseGUI = new CourseGUI(x[0].toString(), x[1].toString(), x[2].toString(),
                    x[3].toString(), x[4].toString(), x[5].toString());
            courseGUI.setButton(new Button("Register"));
            courseGUI.getButton().setOnAction(this::add_course);
            list_course.add(courseGUI);
        }
        ObservableList<CourseGUI> list = FXCollections.observableArrayList(list_course);
        courseTable.setItems(list);
    }
}
