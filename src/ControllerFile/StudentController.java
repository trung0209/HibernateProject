package ControllerFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import memberclass.CourseGUI;
import memberclass.StudentGUI;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    private StudentGUI studentInfo;
    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    @FXML
    private TextField department;

    @FXML
    private TextField birthday;

    @FXML
    private TableView<CourseGUI> courseListTable;

    @FXML
    private TableColumn<CourseGUI, String> idCol;

    @FXML
    private TableColumn<CourseGUI, String> nameCol;

    @FXML
    private TableColumn<CourseGUI, String> proNameCol;

    @FXML
    private TableColumn<CourseGUI, String> chatCol;


    @FXML
    private Button signout;

    @FXML
    private Label status;

    @FXML
    private Button registerBtn;

    @FXML
    private ImageView pictureView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setText(studentInfo.getID());
        name.setText(studentInfo.getName());
        department.setText(studentInfo.getDepartment());
        birthday.setText(String.valueOf(studentInfo.getBirthday()));
        email.setText(studentInfo.getEmail());
        phone.setText(studentInfo.getPhone());
        if (studentInfo.getImage() != null) {
            ByteArrayInputStream is = new ByteArrayInputStream(studentInfo.getImage());
            pictureView.setImage(new Image(is));
        }

        loadTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("classID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("className"));
        proNameCol.setCellValueFactory(new PropertyValueFactory<>("professor"));
        chatCol.setCellValueFactory(new PropertyValueFactory<>("button"));

        signout.setOnAction(this::backBtn);
        updateBtn.setOnAction(this::update_info);
        registerBtn.setOnAction(this::register);
    }

    private void loadTable() {
        courseListTable.getItems().clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("select C.ID,C.CourseName,P.Name,C.Professor_ID from classmanagement.studentlist as T, classmanagement.course as C, classmanagement.professor as P\n" +
                "where T.course = C.ID and T.student_ID = ? and P.ID = C.Professor_ID");
        query.setParameter(1, studentInfo.getID());

        List<Object[]> test = query.list();
        ArrayList<CourseGUI> courseList = new ArrayList<>();
        for (Object[] x : test) {
            if (x[1] == null) {
                continue;
            }
            CourseGUI course = new CourseGUI(x[0].toString(), x[1].toString(), x[2].toString(), x[3].toString());
            course.getButton().setOnAction(this::openChat);
            courseList.add(course);
        }
        courseListTable.getItems().addAll(courseList);
    }

    private void update_info(ActionEvent actionEvent) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String new_name = name.getText();
        String new_birthday = birthday.getText();
        String new_email = email.getText();
        String new_phone = phone.getText();
        NativeQuery query = session.createNativeQuery("call update_student(? , ?, ? , ?, ?)");
        query.setParameter(1, id.getText());
        query.setParameter(2, new_name);
        query.setParameter(3, new_phone);
        query.setParameter(4, new_email);
        query.setParameter(5, new_birthday);
        Transaction transaction = session.beginTransaction();
        query.executeUpdate();
        transaction.commit();
        status.setText("Success");
    }

    public void setStudentInfo(StudentGUI studentInfo) {
        this.studentInfo = studentInfo;
    }

    private void backBtn(ActionEvent event) {
        Stage current = (Stage) ((Node) event.getSource()).getScene().getWindow();
        current.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../GUI/MainMenu.fxml"));
            current.setTitle("Class Management System");
            current.setScene(new Scene(root));
            current.setResizable(false);
            current.show();
        } catch (IOException e) {
            System.out.println("File error");
        }
    }

    private void register(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/StudentCourse.fxml"));
            // Map to controller manually
            StudentGUI studentInfo = this.studentInfo;

            CourseRegisterController controller = new CourseRegisterController();
            controller.setStudentInfo(studentInfo);
            loader.setController(controller);

            // load Fxml
            Parent gradeWindow = loader.load();
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    loadTable();
                }
            });
            newStage.initOwner(currentStage);
            newStage.setScene(new Scene(gradeWindow));
            newStage.setResizable(false);
            newStage.setTitle("Course Register Window");
            newStage.show();


        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void openChat(ActionEvent actionEvent) {
        try {
            CourseGUI course = courseListTable.getSelectionModel().getSelectedItem();
            if (course == null) {
                return;
            }
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/MessageMenu.fxml"));
            // Map to controller manually
            MessageController controller = new MessageController();
            controller.setUserID(studentInfo.getID());

            controller.setTargetID(course.getProfessorID());
            loader.setController(controller);

            // load Fxml
            Parent gradeWindow = loader.load();
            Stage newStage = new Stage();
            newStage.initModality(Modality.NONE);
            newStage.initOwner(currentStage);
            newStage.setScene(new Scene(gradeWindow));
            newStage.setResizable(false);
            newStage.setTitle("Message Window");
            newStage.show();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
