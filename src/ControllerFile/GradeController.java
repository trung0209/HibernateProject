package ControllerFile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import memberclass.GradeGUI;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GradeController implements Initializable {
    static String className;
    static String classID;
    private ArrayList<GradeGUI> studentList;
    @FXML
    private TableView<GradeGUI> gradeTable;

    @FXML
    private TableColumn<GradeGUI, String> nameCol, idCol, midCol, finalCol, assignCol, attendCol, gradeCol;

    @FXML
    private Button applyBtn;

    @FXML
    private TextField classnameField;

    @FXML
    private TextField classidField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentList = new ArrayList<>();
        classnameField.setText(className);
        classidField.setText(classID);
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("call classmanagement.student_list(?)");
        query.setParameter(1, classID);
        List<Object[]> list = query.list();

        for (Object[] x : list) {
            GradeGUI gradeGUI = new GradeGUI(x[0].toString(), x[1].toString());
            studentList.add(gradeGUI);
        }
        ObservableList<GradeGUI> observableList = FXCollections.observableList(studentList);
        gradeTable.setItems(observableList);
    }
//        // set the col and row to editable and change data
//        midCol.setCelglValueFactory(new PropertyValueFactory<>("strin_mid"));
//        midCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        midCol.setOnEditCommit(e->{
//            StudentGUI row = e.getTableView().getItems().get(e.getTablePosition().getRow());
//            row.setString_mid(e.getNewValue());
//            try {
//                row.setMidterm(Float.parseFloat(row.getString_mid()));
//            } catch (NumberFormatException except) {
//                System.out.println(e);
//            }
//        });
//
//        finalCol.setCellValueFactory(new PropertyValueFactory<>("string_final"));
//        finalCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        finalCol.setOnEditCommit(e-> {
//            StudentGUI row = e.getTableView().getItems().get(e.getTablePosition().getRow());
//            row.setString_final(e.getNewValue());
//            try {
//                row.setFinalterm(Float.parseFloat(row.getString_final()));
//            } catch (NumberFormatException except) {
//                System.out.println(e);
//            }
//        });
//
//        assignCol.setCellValueFactory(new PropertyValueFactory<>("string_assign"));
//        assignCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        assignCol.setOnEditCommit(e->{
//            StudentGUI row = e.getTableView().getItems().get(e.getTablePosition().getRow());
//            row.setString_assign(e.getNewValue());
//            try {
//                row.setAssignment(Float.parseFloat(row.getString_assign()));
//            } catch (NumberFormatException except) {
//                System.out.println(e);
//            }
//        });
//
//        attendCol.setCellValueFactory(new PropertyValueFactory<>("string_attend"));
//        attendCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        attendCol.setOnEditCommit(e->{
//            StudentGUI row = e.getTableView().getItems().get(e.getTablePosition().getRow());
//            row.setString_attend(e.getNewValue());
//            try {
//                row.setAttendance(Float.parseFloat(row.getString_attend()));
//            } catch (NumberFormatException except) {
//                System.out.println(e);
//            }
//        });
//
//        gradeCol.setCellValueFactory(new PropertyValueFactory<>("string_average"));
//        for (StudentGUI studentGUI : listStudentGUIS) {
//            String grade_list= String.format("../ClassroomProject/Data/StudentDetails/%s/%s/grade.txt", studentGUI.getID(),classID);
//            File file = new File(grade_list);
//            try {
//                Scanner scanner = new Scanner(file);
//                if (scanner.hasNext()) {
//                    String[] temp = scanner.nextLine().split(",");
//                    studentGUI.setString_mid(temp[0]);
//                    studentGUI.setMidterm(Float.parseFloat(temp[0]));
//                    studentGUI.setString_final(temp[1]);
//                    studentGUI.setFinalterm(Float.parseFloat(temp[1]));
//                    studentGUI.setString_assign(temp[2]);
//                    studentGUI.setAssignment(Float.parseFloat(temp[2]));
//                    studentGUI.setString_attend(temp[3]);
//                    studentGUI.setAttendance(Float.parseFloat(temp[3]));
//                    studentGUI.setString_average(temp[4]);
//                    studentGUI.setAverage(Float.parseFloat(temp[4]));
//                }
//            } catch (IOException e) {
//                System.out.println("error");
//            }
//        }
//
//        int i = 0;
//
//        ObservableList<StudentGUI> data = FXCollections.observableArrayList(listStudentGUIS);
//
//        weightMiterm.setText(list_weight[0]);
//        weightFinal.setText(list_weight[1]);
//        weightAssignment.setText(list_weight[2]);
//        weightAttendance.setText(list_weight[3]);
//        gradeTable.setItems(data);
//
//        applyBtn.setOnAction(this::ApplyChange);
//    }
//
//    public void ApplyChange(ActionEvent e) {
//        ObservableList<StudentGUI> data = gradeTable.getItems();
//        try {
//            float midweight = (float) (Float.parseFloat(weightMiterm.getText())/100.0);
//            float finalweight = (float) (Float.parseFloat(weightFinal.getText())/100.0);
//            float assignmentweight = (float) (Float.parseFloat(weightAssignment.getText())/100.0);
//            float attendanceweight = (float) (Float.parseFloat(weightAttendance.getText())/100.0);
//            float total_weight =  midweight + finalweight + assignmentweight + attendanceweight;
//            if (Float.compare(total_weight, (float) 1) != 0) {
//                status.setText("Total weight must be 100%");
//                return;
//            }
//
//            String weight_save = String.format("../ClassroomProject/Data/ProfessorDetails/%s/%s/weight.txt",professor_id,classID);
//            FileWriter fileWriter = new FileWriter(weight_save);
//            String weight_data = String.format("%s %s %s %s", weightMiterm.getText(), weightFinal.getText(),
//                    weightAssignment.getText(), weightAttendance.getText());
//            fileWriter.write(weight_data);
//            fileWriter.close();
//            status.setText("Total weight is 100%");
//
//
//            for (StudentGUI x: data) {
//                float grade1 = x.getMidterm()*midweight + x.getFinalterm()*finalweight;
//                float grade2 = x.getAssignment()*assignmentweight + x.getAttendance()*attendanceweight;
//                x.setAverage(grade1+grade2);
//                String average_mark = String.format("%.2f",x.getAverage());
//
//                String grade = String.format("%.2f,%.2f,%.2f,%.2f,%.2f\n",x.getMidterm(),
//                                             x.getFinalterm(),x.getAssignment(),x.getAttendance(),x.getAverage());
//                FileWriter fileWriter2 = new FileWriter(String.format("../ClassroomProject/Data/" +
//                                                        "StudentDetails/%s/%s/grade.txt",x.getID(),classID));
//                fileWriter2.write(grade);
//                fileWriter2.flush();
//                fileWriter2.close();
//                x.setString_average(average_mark);
//            }
//            gradeTable.setItems(data);
//            gradeTable.refresh();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        } catch (NumberFormatException exception) {
//            status.setText("Invalid weight of score");
//        } catch (NullPointerException e1) {
//            status.setText("Please enter the weight");
//        }
}
