package ControllerFile;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import memberclass.Professor_GUI;
import memberclass.SectionGUI;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SectionController implements Initializable {
    static Professor_GUI professorInfo;
    private ArrayList<SectionGUI> sectionlist;
    @FXML
    private TableView<SectionGUI> ownSectionTable, otherSectionTable;

    @FXML
    private TableColumn<SectionGUI, String> idCol1;

    @FXML
    private TableColumn<SectionGUI, String> nameCol1;

    @FXML
    private TableColumn<SectionGUI, String> timeCol1;

    @FXML
    private TableColumn<SectionGUI, String> dayCol1;

    @FXML
    private TableColumn<SectionGUI, String> roomCol1;

    @FXML
    private TableColumn<SectionGUI, String> nameCol, idCol;

    @FXML
    private TableColumn<SectionGUI, String> timeCol;

    @FXML
    private TableColumn<SectionGUI, String> dayCol;

    @FXML
    private TableColumn<SectionGUI, String> roomCol;

    @FXML
    private Button applyBtn;

    @FXML
    private ComboBox<String> startH, startM, endH, endM;

    @FXML
    private ComboBox<String> classList, roomList;

    @FXML
    private Label status;

    @FXML
    private ComboBox<String> dayList;

    @FXML
    private Button addDayBtn;

    @FXML
    private TextField dayInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        classList.setPromptText("Class List");
        String[] hour =
                {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] minutesArray = {
                "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        };
        String[] daysOfWeek = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        ObservableList<String> hourslist = FXCollections.observableArrayList(hour);
        ObservableList<String> minutelist = FXCollections.observableArrayList(minutesArray);
        ObservableList<String> day = FXCollections.observableArrayList(daysOfWeek);
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery query1 = session.createNativeQuery("select CourseName from classmanagement.course where Professor_ID = ?");
        query1.setParameter(1, professorInfo.getID());

        ObservableList list = FXCollections.observableArrayList(query1.list());

        query1 = session.createNativeQuery("select T.Name " +
                "from classmanagement.room as T inner join classmanagement.department as V " +
                "where T.Building = V.ID and V.ID = ?");
        query1.setParameter(1, professorInfo.getDept_id());
        ObservableList list2 = FXCollections.observableArrayList(query1.list());

        roomList.getItems().addAll(list2);
        classList.getItems().addAll(list);
        dayList.getItems().addAll(day);
        startH.getItems().addAll(hourslist);
        startM.getItems().addAll(minutelist);
        endH.getItems().addAll(hourslist);
        endM.getItems().addAll(minutelist);
        query1 = session.createSQLQuery("select C.ID, C.CourseName, S.Room_name, S.start, S.end, S.Day\n" +
                "from classmanagement.course as C inner join classmanagement.section as S\n" +
                "where C.ID = S.courseid and C.Professor_ID = ?");
        query1.setParameter(1, professorInfo.getID());
        List<Object[]> test = query1.list();
        sectionlist = new ArrayList<>();
        for (Object[] x : test) {
            SectionGUI sectionGUI = new SectionGUI();
            sectionGUI.setClass_id(x[0].toString());
            sectionGUI.setClassName(x[1].toString());
            sectionGUI.setRoom(x[2].toString());
            sectionGUI.setTime(x[3].toString() + "-" + x[4].toString());
            sectionGUI.setDay(x[5].toString());
            sectionlist.add(sectionGUI);
        }
        ObservableList<SectionGUI> section_list = FXCollections.observableArrayList(sectionlist);
        idCol.setCellValueFactory(new PropertyValueFactory<>("class_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("className"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));

        ownSectionTable.getItems().addAll(section_list);
        applyBtn.setOnAction(this::Create_Section);
    }

    private void update() {
        ObservableList<SectionGUI> list = FXCollections.observableArrayList(sectionlist);
        ownSectionTable.setItems(list);
    }

    private void Create_Section(ActionEvent actionEvent) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query1 = session.createNativeQuery("call classmanagement.create_section(?, ?, ?, ?, ?)");
        NativeQuery query = session.createNativeQuery("select ID from classmanagement.course where CourseName = ?");
        NativeQuery query3 = session.createNativeQuery("call classmanagement.check_overlaping(?,?,?,?)");
        String startTime = startH.getValue() + ":" + startM.getValue() + ":" + "00";
        String endTime = endH.getValue() + ":" + endM.getValue() + ":" + "00";
        String class_name = classList.getValue();
        String room_name = roomList.getValue();
        String day_conduct = dayList.getValue();
        query3.setParameter(1, room_name);
        query3.setParameter(2, startTime);
        query3.setParameter(3, endTime);
        query3.setParameter(4, day_conduct);
        if (query3.uniqueResult() == null) {
            status.setText("Time not valid");
            return;
        }
        query.setParameter(1, class_name);
        String class_id = String.valueOf(query.uniqueResult());
        query1.setParameter(1, class_id);
        query1.setParameter(2, room_name);
        query1.setParameter(3, startTime);
        query1.setParameter(4, endTime);
        query1.setParameter(5, day_conduct);
        query1.executeUpdate();

        transaction.commit();
        session.close();
        SectionGUI sectionGUI = new SectionGUI(class_id, class_name, room_name, startTime + "-" + endTime, day_conduct);
        sectionlist.add(sectionGUI);
        update();
    }

    private void construct_day(ActionEvent actionEvent) {
        if (dayInput.getText().compareTo("") == 1) {
            dayInput.appendText(dayList.getValue());
            return;
        }
        dayInput.appendText(dayList.getValue() + ",");
    }
}
