package ControllerFile;

import Mapping.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import memberclass.Classroom;
import memberclass.ProfessorGUI;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ProfessorMenuController implements Initializable {
    private ProfessorGUI professorInfo;

    private ArrayList<Classroom> class_data;

    @FXML
    private TableColumn<Classroom, String> deleteCol, nameCol, idCol, gradeCol, creditCol;

    @FXML
    private TableView<Classroom> classTable;

    @FXML
    private TextField nameField, classInput, currentShow, totalClass;

    @FXML
    private Label status;

    @FXML
    private TextArea addInput;

    @FXML
    private Button addButton, backBtn, applyBtn;

    @FXML
    private TextField creditInput, weightMiterm, weightFinal, weightAttendance, weightAssignment;

    @FXML
    private Button sectionBtn;

    @FXML
    private TableColumn<Classroom, String> listCol;

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("select classmanagement.count_class(?)");
        NativeQuery query1 = session.createNativeQuery("select * from classmanagement.course where Professor_ID = ?");
        query.setParameter(1, professorInfo.getID());
        query1.setParameter(1, professorInfo.getID());
        query1.addEntity(Course.class);
        List<Course> class_list = query1.list();
        class_data = new ArrayList<>();
        for (Course x : class_list) {
            Classroom classroom = new Classroom(x.getId(), x.getCourseName(), String.valueOf(x.getCredit()));
            classroom.getDelete_btn().setOnAction(this::DeleteButton);
            classroom.getGrade_btn().setOnAction(this::GradeButton);
            classroom.getButton2().setOnAction(this::StudentList);
            class_data.add(classroom);
        }

        deleteCol.setCellValueFactory(new PropertyValueFactory<>("delete_btn"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade_btn"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("class_ID"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        listCol.setCellValueFactory(new PropertyValueFactory<>("button2"));
        creditCol.setCellFactory(TextFieldTableCell.forTableColumn());
        creditCol.setOnEditCommit(e -> {
            Classroom row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setCredit(e.getNewValue());
            Session session1 = HibernateUtil.getSessionFactory().openSession();
            NativeQuery query2 = session1.createNativeQuery("UPDATE classmanagement.course\n" +
                    "SET\n" +
                    "Credit = ?" +
                    "WHERE ID = ?;");
            query2.setParameter(1, row.getCredit());
            query2.setParameter(2, row.getClass_ID());
            Transaction transaction = session1.beginTransaction();
            query2.executeUpdate();
            transaction.commit();
            session1.close();
        });
        nameCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e -> {
            Classroom row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setClass_name(e.getNewValue());
            Session session3 = HibernateUtil.getSessionFactory().openSession();
            NativeQuery query3 = session3.createNativeQuery("UPDATE classmanagement.course\n" +
                    "SET\n" +
                    "CourseName = ?" +
                    "WHERE ID = ?;");
            query3.setParameter(1, row.getClass_name());
            query3.setParameter(2, row.getClass_ID());
            Transaction transaction = session3.beginTransaction();
            query3.executeUpdate();
            transaction.commit();
            session3.close();
        });
        ObservableList<Classroom> data = FXCollections.observableArrayList(class_data);

        nameField.setText(professorInfo.getName());
        totalClass.setText(String.valueOf(query.uniqueResult()));
        classTable.setItems(data);
        classTable.setEditable(true);
//        classTable.setOnMouseClicked(this::Show);
        addButton.setOnAction(this::addClass);
        applyBtn.setOnAction(this::applyButton);
        backBtn.setOnAction(this::backBtn);
        sectionBtn.setOnAction(this::SectionButton);
        session.close();

    }

    public void setProfessorInfo(ProfessorGUI professorInfo) {
        this.professorInfo = professorInfo;
    }

    private void StudentList(ActionEvent actionEvent) {
        try {
            Classroom classroom = classTable.getSelectionModel().getSelectedItem();
            if (classroom == null) {
                return;
            }

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/StudentList.fxml"));
            // Map to controller manually

            StudentListController controller = new StudentListController();
            controller.setCourseID(classroom.getClass_ID());
            controller.setProfessorID(professorInfo.getID());
            loader.setController(controller);

            // load Fxml
            Parent gradeWindow = loader.load();
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);

            newStage.initOwner(currentStage);
            newStage.setScene(new Scene(gradeWindow));
            newStage.setResizable(false);
            newStage.setTitle("Student List Window");
            newStage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private String ID_generator() {
        Random random = new Random();
        StringBuilder temp = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            temp.append(random.nextInt(9) + 1);
        }
        return temp.toString();
    }

    private void update() {
        professorInfo.setTotal_class(class_data.size());
        totalClass.setText(String.valueOf(professorInfo.getTotal_class()));
        ObservableList<Classroom> data = FXCollections.observableArrayList(class_data);
        classTable.setItems(data);
    }

    private void DeleteButton(ActionEvent actionEvent) {
        Classroom class_detail = classTable.getSelectionModel().getSelectedItem();
        if (class_detail != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            NativeQuery query = session.createNativeQuery("call classmanagement.delete_course(?)");
            query.setParameter(1, class_detail.getClass_ID());
            NativeQuery query1 = session.createNativeQuery("DELETE FROM classmanagement.section\n" +
                    "WHERE courseid = ?");
            query1.setParameter(1, class_detail.getClass_ID());

            NativeQuery query2 = session.createNativeQuery("DELETE FROM `classmanagement`.`studentlist`\n" +
                    "WHERE course = ?");
            query2.setParameter(1, class_detail.getClass_ID());
            Transaction transaction = session.beginTransaction();
            query.executeUpdate();
            query1.executeUpdate();
            query2.executeUpdate();
            transaction.commit();
            session.close();
            class_data.remove(class_detail);
            update();
        }
    }

    private void addClass(ActionEvent actionEvent) {
        String class_name = classInput.getText();
        if (class_name.isEmpty() ||
                weightAssignment.getText().isEmpty() || weightFinal.getText().isEmpty() ||
                weightMiterm.getText().isEmpty() || weightAttendance.getText().isEmpty() ||
                creditInput.getText().isEmpty()) {
            status.setText("Please enter all field");
            return;
        }
        float midweight = (float) (Float.parseFloat(weightMiterm.getText()) / 100.0);
        float finalweight = (float) (Float.parseFloat(weightFinal.getText()) / 100.0);
        float assignmentweight = (float) (Float.parseFloat(weightAssignment.getText()) / 100.0);
        float attendanceweight = (float) (Float.parseFloat(weightAttendance.getText()) / 100.0);

        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("SELECT EXISTS(SELECT * FROM classmanagement.course WHERE strcmp(ID, ?))");
        String id;
        do {
            id = ID_generator();
            query.setParameter(1, id);

        } while (Objects.equals(query.uniqueResult(), 1));
        Transaction transaction = session.beginTransaction();
        query = session.createNativeQuery("call classmanagement.create_course(?, ? , ? , ?, ?, ?, ?, ?)");
        query.setParameter(1, id);
        query.setParameter(2, professorInfo.getID());
        query.setParameter(3, class_name);
        query.setParameter(4, Integer.parseInt(creditInput.getText()));
        query.setParameter(5, midweight);
        query.setParameter(6, finalweight);
        query.setParameter(7, attendanceweight);
        query.setParameter(8, assignmentweight);
        query.executeUpdate();
        transaction.commit();
        session.close();
        Classroom new_classroom = new Classroom(id, class_name, creditInput.getText());
        new_classroom.getGrade_btn().setOnAction(this::GradeButton);
        new_classroom.getDelete_btn().setOnAction(this::DeleteButton);
        class_data.add(new_classroom);
        update();
    }

    private void SectionButton(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/Section.fxml"));
            // Map to controller manually

            SectionController controller = new SectionController();
            SectionController.professorInfo = professorInfo;
            loader.setController(controller);

            // load Fxml
            Parent gradeWindow = loader.load();
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);

            newStage.initOwner(currentStage);
            newStage.setScene(new Scene(gradeWindow));
            newStage.setResizable(false);
            newStage.setTitle("Section Window");
            newStage.show();
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    private void GradeButton(ActionEvent actionEvent) {
        Classroom class_detail = classTable.getSelectionModel().getSelectedItem();
        if (class_detail != null) {
            try {

                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/GradeMenu.fxml"));
                // Map to controller manually
                GradeController.classID = class_detail.getClass_ID();
                GradeController.className = class_detail.getClass_name();
                GradeController controller = new GradeController();
                loader.setController(controller);

                // load Fxml
                Parent gradeWindow = loader.load();
                Stage newStage = new Stage();
                newStage.initModality(Modality.APPLICATION_MODAL);

                newStage.initOwner(currentStage);
                newStage.setScene(new Scene(gradeWindow));
                newStage.setResizable(false);
                newStage.setTitle("Grade Window");
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void applyButton(ActionEvent actionEvent) {
//        String[] id_list = addInput.getText().split("\n");
//        String parent = String.format("../ClassroomProject/Data/ProfessorDetails/%s", professorInfo.getID());
//        if (current_detail.getClass_ID() == null) {
//            return;
//        }
//        String file_name = parent + "/" + current_detail.getClass_ID() + ".txt";
//        try {
//            FileWriter fileWriter = new FileWriter(file_name);
//            for (String value : id_list) {
//                fileWriter.write(value);
//                fileWriter.write("\n");
//            }
//            fileWriter.close();
//
//            for (String s : id_list) {
//                if (check(s)) {
//                    String student_path = String.format("..//ClassroomProject/Data/StudentDetails/%s/%s", s, current_detail.getClass_ID());
//                    File file = new File(student_path);
//                    File file2 = new File(student_path + "/grade.txt");
//                    if (!file.exists()) {
//                        file.mkdir();
//                        file2.createNewFile();
//                        FileWriter writer = new FileWriter(String.format("../ClassroomProject/Data/StudentDetails/%s/class_history_data.txt", s),true);
//                        writer.write(current_detail.getClass_ID() + "," + current_detail.getClass_name() + "\n");
//                        writer.close();
//                    }
//                }
//            }
//        } catch (IOException ignored) {
//        }
    }

//    private void Show(MouseEvent mouseEvent) {
//        Classroom class_detail = classTable.getSelectionModel().getSelectedItem();
//        if (class_detail != null) {
//
//        }
//    }

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
}
