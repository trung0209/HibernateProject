package ControllerFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import memberclass.StudentGUI;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StudentListController implements Initializable {
    private String courseID;
    private String professorID;
    @FXML
    private TableColumn<StudentGUI, String> idCol;

    @FXML
    private TableColumn<StudentGUI, String> nameCol;

    @FXML
    private TableColumn<StudentGUI, String> chatBtn;

    @FXML
    private TableView<StudentGUI> studentTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<StudentGUI> studentList = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("select S.ID, S.name from classmanagement.studentlist as L, classmanagement.student as S \n" +
                "where L.course = ? and L.student_ID = S.ID");
        query.setParameter(1, courseID);
        List<Object[]> templist = query.list();
        for (Object[] x : templist) {
            StudentGUI current = new StudentGUI(x[0].toString(), x[1].toString());
            current.getBtn().setOnAction(this::openChat);
            studentList.add(current);
        }
        ObservableList<StudentGUI> list = FXCollections.observableArrayList(studentList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chatBtn.setCellValueFactory(new PropertyValueFactory<>("btn"));

        studentTable.getItems().setAll(list);
    }

    private void openChat(ActionEvent actionEvent) {
        try {
            StudentGUI studentinfo = studentTable.getSelectionModel().getSelectedItem();
            if (studentinfo == null) {
                return;
            }
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/MessageMenu.fxml"));
            // Map to controller manually
            MessageController controller = new MessageController();
            controller.setUserID(professorID);
            controller.setTargetID(studentinfo.getName());
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

    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
}
