package ControllerFile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import memberclass.GradeGUI;
import memberclass.Student_GUI;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    static Student_GUI studentInfo;
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
    private TableView<GradeGUI> gradeTable;

    @FXML
    private TableColumn<GradeGUI, String> idCol, nameCol,
            midCol, finalCol, assignCol, attendCol, gradeCol;

    @FXML
    private Button signout;

    @FXML
    private Label status;

    @FXML
    private Button registerBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setText(studentInfo.getID());
        name.setText(studentInfo.getName());
        department.setText(studentInfo.getDepartment());
        birthday.setText(String.valueOf(studentInfo.getBirthday()));
        email.setText(studentInfo.getEmail());
        phone.setText(studentInfo.getPhone());

//        idCol.setCellValueFactory(new PropertyValueFactory<>("classID"));
//        nameCol.setCellValueFactory(new PropertyValueFactory<>("className"));
//        midCol.setCellValueFactory(new PropertyValueFactory<>("string_mid"));
//        finalCol.setCellValueFactory(new PropertyValueFactory<>("string_final"));
//        assignCol.setCellValueFactory(new PropertyValueFactory<>("string_assign"));
//        attendCol.setCellValueFactory(new PropertyValueFactory<>("string_attend"));
//        gradeCol.setCellValueFactory(new PropertyValueFactory<>("string_average"));
        signout.setOnAction(this::backBtn);
        updateBtn.setOnAction(this::update_info);
        registerBtn.setOnAction(this::register);
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
            CourseRegisterController.studentInfo = studentInfo;
            CourseRegisterController controller = new CourseRegisterController();

            loader.setController(controller);

            // load Fxml
            Parent gradeWindow = loader.load();
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);

            newStage.initOwner(currentStage);
            newStage.setScene(new Scene(gradeWindow));
            newStage.setResizable(false);
            newStage.setTitle("Course Register Window");
            newStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
