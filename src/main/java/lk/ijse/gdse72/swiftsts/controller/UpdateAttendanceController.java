package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.dto.AttendanceDto;
import lk.ijse.gdse72.swiftsts.model.AttendanceModel;
import lk.ijse.gdse72.swiftsts.model.DriverModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateAttendanceController implements Initializable {

    @FXML
    private JFXButton btnDiscard;

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
    private Label lblAttendenceId;

    @FXML
    private Label lblDriverName;

    @FXML
    private Label lblStudentName;

    @FXML
    private AnchorPane paneEditAttendance;

    private AnchorPane overlayPane;
    private AnchorPane paneAttendance;

    @FXML
    private JFXTextField txtDayCount;

    private final AttendanceModel attendanceModel = new AttendanceModel();
    DriverModel driverModel =  new DriverModel();
    StudentModel studentModel = new StudentModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadStudentIds();
            loadDriverIds();
            loadYears();
            loadMonths();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentIds() throws SQLException {
        ArrayList<String> studentNames = studentModel.getAllStudentNames();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentNames);
        cbStudentId.setItems(observableList);
    }

    private void loadDriverIds() throws SQLException {
        ArrayList<String> driverIds = driverModel.getAllDriverIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(driverIds);
        cbDriverId.setItems(observableList);
    }

    private void loadYears() {
        ObservableList<String> years = FXCollections.observableArrayList("2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040");
        cbYear.setItems(years);
    }

    private void loadMonths() {
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cbMonth.setItems(months);
    }

    public void setAttendanceData(AttendanceDto dto) {
        lblAttendenceId.setText(dto.getAttendanceId());
        cbStudentId.setValue(dto.getStudentId());
        cbDriverId.setValue(dto.getVehicleId());
        cbYear.setValue(String.valueOf(dto.getYear()));
        cbMonth.setValue(dto.getMonth());
        txtDayCount.setText(String.valueOf(dto.getDayCount()));
    }

    @FXML
    void btnDiscardOnAction(ActionEvent event) {
        paneAttendance.getChildren().remove(overlayPane);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            AttendanceDto updatedAttendance = new AttendanceDto(
                    lblAttendenceId.getText(),
                    cbStudentId.getValue(),
                    cbDriverId.getValue(),
                    Integer.parseInt(cbYear.getValue()),
                    cbMonth.getValue(),
                    Integer.parseInt(txtDayCount.getText())
            );

            boolean isUpdated = attendanceModel.updateAttendance(updatedAttendance);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Attendance updated successfully!").show();
                paneAttendance.getChildren().remove(overlayPane);
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update attendance!").show();
                paneAttendance.getChildren().remove(overlayPane);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the attendance: " + e.getMessage()).show();
            paneAttendance.getChildren().remove(overlayPane);
        }
    }

    public void setOverlayPane(AnchorPane overlayPane, AnchorPane paneAttendence) {
        this.overlayPane = overlayPane;
        this.paneAttendance = paneAttendence;
    }
}