package ControllerFile;

import Mapping.Department;
import Mapping.Professor;
import Mapping.Student;
import javafx.application.Platform;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import memberclass.ProfessorGUI;
import memberclass.StudentGUI;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class LoginMenuController implements Initializable {
    @FXML
    private Label pic_path;

    @FXML
    private TextField IdField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Label StatusLable;

    // ProfessorGUI register Tab
    @FXML
    private TextField IdRegister;

    @FXML
    private PasswordField PasswordRegister;

    @FXML
    private PasswordField ConfirmPass;

    @FXML
    private TextField NameRegister;

    @FXML
    private Label StatusLable2;

    // StudentGUI register Tab
    @FXML
    private TextField StudentId;

    @FXML
    private PasswordField StudentPass;

    @FXML
    private PasswordField ConfirmPass2;

    @FXML
    private TextField StudentName;

    @FXML
    private Label StatusLable3;

    @FXML
    private Tab LoginTab, RegisterTab2, RegisterTab;

    @FXML
    private TextField ProfessorEmail;

    @FXML
    private TextField ProfessorPhone;

    @FXML
    private ComboBox<String> listDept, listDept1;

    @FXML
    private TextField StudentEmail;

    @FXML
    private TextField StudentPhone;

    @FXML
    private TextField dateInputStudent;

    @FXML
    private Button upload_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query = session.createNativeQuery("select * from classmanagement.department");
        query.addEntity(Department.class);
        List<Department> list = query.list();
        ArrayList<String> arrayList = new ArrayList<>();
        for (Department x : list) {
            String name = x.getName();
            arrayList.add(name);
        }
        ObservableList<String> all_dept = FXCollections.observableArrayList(arrayList);
        listDept.getItems().addAll(all_dept);
        listDept1.getItems().addAll(all_dept);
        RegisterTab2.setOnSelectionChanged(e -> {
            StatusLable3.setText("");
            ConfirmPass2.setText("");
            StudentPass.setText("");
            StudentId.setText("");
            StudentName.setText("");
        });
        RegisterTab.setOnSelectionChanged(e -> {
            StatusLable2.setText("");
            ConfirmPass.setText("");
            PasswordRegister.setText("");
            IdRegister.setText("");
            NameRegister.setText("");
        });
        LoginTab.setOnSelectionChanged(e -> {
            StatusLable.setText("");
            PasswordField.setText("");
            IdField.setText("");
        });
    }

    @FXML
    void LoginConfirm(ActionEvent event) {

        String login_id = IdField.getText();
        String password = PasswordField.getText();

        if (login_id.isEmpty() || password.isEmpty()) {
            StatusLable.setText("Status: ID or password field cannot be empty");
            return;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();

        NativeQuery query = session.createNativeQuery("select P.ID, P.DepartmentID, P.Name, P.Email, P.Phone, P.DepartmentID\n" +
                "from classmanagement.professor as P \n" +
                "where P.ID = (select L.Login_ID from classmanagement.logininfo as L where L.Login_ID = ?  and L.password = ?)");
        query.setParameter(1, login_id);
        query.setParameter(2, password);
        query.addEntity(Professor.class);
        Professor test = (Professor) query.uniqueResult();
        if (test != null) {
            try {
                session.close();
                Stage current = (Stage) ((Node) event.getSource()).getScene().getWindow();
                current.close();
                // load fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/ProfessorMenu.fxml"));
                // Map to controller manually
                ProfessorGUI professorInfo = new ProfessorGUI(test.getName(), test.getId(), test.getDepartmentId());
                ProfessorMenuController controller = new ProfessorMenuController();
                controller.setProfessorInfo(professorInfo);
                loader.setController(controller);
                // load Fxml
                Parent root = loader.load();
                current.setTitle("ProfessorGUI Page");
                current.setScene(new Scene(root));
                current.setResizable(false);
                current.setOnCloseRequest(event1 -> Platform.exit());
                current.show();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return;
        }
        query = session.createNativeQuery("select P.ID, P.DepartmentID, P.Name, P.Email, P.Phone, P.birthday, P.image, P.Semester\n" +
                "from classmanagement.student as P\n" +
                "where P.ID = (select L.Login_ID from classmanagement.logininfo as L where L.Login_ID = ? and L.password = ?)");
        query.setParameter(1, login_id);
        query.setParameter(2, password);
        query.addEntity(Student.class);
        Student list2 = (Student) query.uniqueResult();
        if (list2 != null) {
            try {
                session.close();
                Stage current = (Stage) ((Node) event.getSource()).getScene().getWindow();
                current.close();
                // load fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../GUI/StudentMenu.fxml"));
                // Map to controller manually

                StudentGUI studentInfo = new StudentGUI(list2.getName(), list2.getId(),
                        list2.getDepartmentByStudent().getName(), list2.getEmail(), list2.getPhone(), list2.getBirthday(), list2.getImage());

                StudentController controller = new StudentController();
                controller.setStudentInfo(studentInfo);
                loader.setController(controller);
                // load Fxml
                Parent root = loader.load();
                current.setTitle("StudentGUI Page");
                current.setScene(new Scene(root));
                current.setResizable(false);
                current.setOnCloseRequest(event1 -> Platform.exit());
                current.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        session.close();
        StatusLable.setText("Status: Wrong password or ID");
    }

    @FXML
    public void RegisterStudent() {
        String name = StudentName.getText();
        String ID = StudentId.getText();
        String password = StudentPass.getText();
        String confirmPass = ConfirmPass2.getText();
        boolean containDigit = ID.matches("[0-9]+");
        boolean checklength = ID.length() >= 8;
        boolean matchPass = password.equals(confirmPass);
        if (!containDigit) {
            StatusLable3.setText("Status: ID can only contain digit");
            return;
        }
        if (!checklength) {
            StatusLable3.setText("Status: ID invalid");
            return;
        }
        if (!matchPass) {
            StatusLable3.setText("Status: Password not match");
            return;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery findDeptID = session.createNativeQuery("select ID from classmanagement.department where Name = ?");
        findDeptID.setParameter(1, listDept1.getValue());

        Transaction transaction = null;
        try {
            byte[] data = null;
            try {
                BufferedImage bImage = ImageIO.read(new File(pic_path.getText()));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos);
                data = bos.toByteArray();

            } catch (IIOException ignored) {
            }

            transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery("CALL classmanagement.insert_student(?,?,?,?,?,?,?,?)");
            query.setParameter(1, name);
            query.setParameter(2, ID);
            query.setParameter(3, password);
            query.setParameter(4, StudentEmail.getText());
            query.setParameter(5, StudentEmail.getText());
            query.setParameter(6, dateInputStudent.getText());
            query.setParameter(8, findDeptID.uniqueResult());
            query.setParameter(7, data);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.close();

        StatusLable3.setText("Success");
    }

    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(upload_btn.getScene().getWindow());
        pic_path.setText(file.getPath());
    }

    @FXML
    public void RegisterProfessor() {
        String name = NameRegister.getText();
        String ID = IdRegister.getText();
        String password = PasswordRegister.getText();
        String confirmPass = ConfirmPass.getText();
        boolean containDigit = ID.matches("[0-9]+");
        boolean checklength = ID.length() >= 5;
        boolean matchPass = password.equals(confirmPass);
        if (!containDigit) {
            StatusLable2.setText("Status: ID can only contain digit");
            return;
        }
        if (!checklength) {
            StatusLable2.setText("Status: ID invalid");
            return;
        }
        if (!matchPass) {
            StatusLable2.setText("Status: Password not match");
            return;
        }
        Session session = HibernateUtil.getSessionFactory().openSession();

        NativeQuery findDeptID = session.createNativeQuery("select ID from classmanagement.department where Name = ?");
        findDeptID.setParameter(1, listDept.getValue());
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery("CALL classmanagement.insert_professor(?,?,?,?,?,?)");
            query.setParameter(1, name);
            query.setParameter(2, ID);
            query.setParameter(3, password);
            query.setParameter(4, ProfessorPhone.getText());
            query.setParameter(5, ProfessorEmail.getText());
            query.setParameter(6, findDeptID.uniqueResult());
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        StatusLable2.setText("Success");
    }
}
