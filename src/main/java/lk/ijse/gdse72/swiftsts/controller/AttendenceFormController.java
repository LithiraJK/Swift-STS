package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.dto.AttendenceDto;
import lk.ijse.gdse72.swiftsts.model.AttendenceModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttendenceFormController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private ImageView btnGoBack;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cbDriverId;

    @FXML
    private JFXComboBox<String> cbMonth;

    @FXML
    private JFXComboBox<String> cbStudentId;

    @FXML
    private JFXComboBox<String> cbYear;

    @FXML
    private TableColumn<AttendenceDto, String> colAttendenceId;

    @FXML
    private TableColumn<AttendenceDto, Integer> colDayCount;

    @FXML
    private TableColumn<AttendenceDto, String> colDriverId;

    @FXML
    private TableColumn<AttendenceDto, String> colDriverName;

    @FXML
    private TableColumn<AttendenceDto, String> colMonth;

    @FXML
    private TableColumn<AttendenceDto, Integer> colYear;

    @FXML
    private Label lblAttendenceId;

    @FXML
    private Label lblStudentName;

    @FXML
    private AnchorPane paneAttendence;

    @FXML
    private TableView<AttendenceDto> tblAttendence;

    @FXML
    private JFXTextField txtDayCount;

    private AttendenceModel attendenceModel = new AttendenceModel();
    private final StudentModel customerModel = new StudentModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String attendenceId = lblAttendenceId.getText();
        boolean isDeleted = attendenceModel.deleteAttendence(attendenceId);
        if (isDeleted) {
            refreshTable();
        }
    }

    @FXML
    void btnGoBackOnMouseClicked(MouseEvent event) throws IOException {
        paneAttendence.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml"));
        paneAttendence.getChildren().add(anchorPane);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        AttendenceDto dto = new AttendenceDto(
                lblAttendenceId.getText(),
                cbStudentId.getValue(),
                cbDriverId.getValue(),
                Integer.parseInt(cbYear.getValue()),
                cbMonth.getValue(),
                Integer.parseInt(txtDayCount.getText())
        );
        boolean isSaved = attendenceModel.saveAttendence(dto);
        if (isSaved) {
            refreshTable();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        AttendenceDto dto = new AttendenceDto(
                lblAttendenceId.getText(),
                cbStudentId.getValue(),
                cbDriverId.getValue(),
                Integer.parseInt(cbYear.getValue()),
                cbMonth.getValue(),
                Integer.parseInt(txtDayCount.getText())
        );
        boolean isUpdated = attendenceModel.updateAttendence(dto);
        if (isUpdated) {
            refreshTable();
        }
    }

    private void loadStudentIds() throws SQLException {
        ArrayList<String> studentIds = StudentModel.getAllStudentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentIds);
        cbStudentId.setItems(observableList);
    }

//    private void loadDriverIds() throws SQLException {
//        ArrayList<String> driverIds = DriverModel.getAllDriverIds();
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        observableList.addAll(driverIds);
//        cbDriverId.setItems(observableList);
//    }

    private void loadYears() {
        ObservableList<String> years = FXCollections.observableArrayList("2021", "2022", "2023", "2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040");
        cbYear.setItems(years);
    }

    private void loadMonths() {
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cbMonth.setItems(months);
    }

    private void refreshTable() throws SQLException {
        ArrayList<AttendenceDto> attendenceList = attendenceModel.getAllAttendences();
        tblAttendence.getItems().setAll(attendenceList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadStudentIds();
//            loadDriverIds();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadYears();
        loadMonths();
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}