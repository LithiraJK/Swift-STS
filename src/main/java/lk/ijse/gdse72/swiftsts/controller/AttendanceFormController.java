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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lk.ijse.gdse72.swiftsts.dto.AttendanceDto;
import lk.ijse.gdse72.swiftsts.dto.tm.AttendanceTM;
import lk.ijse.gdse72.swiftsts.model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttendanceFormController implements Initializable {
    @FXML
    public JFXButton btnReset;
    @FXML
    public JFXButton btnMakeAttendance;
    @FXML
    public TableColumn<AttendanceTM, HBox> colAction;

    @FXML
    public TableColumn<AttendanceTM, String> colAttendanceId;

    @FXML
    public JFXButton btnCalculateFees;

    @FXML
    private ImageView btnGoBack;

    @FXML
    private TableColumn<AttendanceTM, Integer> colDayCount;

    @FXML
    private TableColumn<AttendanceTM, String> colVehicleId;

    @FXML
    public TableColumn<AttendanceTM, String> colStudentId;

    @FXML
    private TableColumn<AttendanceTM, String> colMonth;

    @FXML
    private TableColumn<AttendanceTM, Integer> colYear;

    @FXML
    private JFXComboBox<String> cbVehicleId;

    @FXML
    private JFXComboBox<String> cbMonth;

    @FXML
    private JFXComboBox<String> cbStudentId;

    @FXML
    private JFXComboBox<String> cbYear;

    @FXML
    private Label lblAttendenceId;

    @FXML
    private Label lblStudentId;

    @FXML
    private Label lblVehicleRegistrationNo;

    @FXML
    private AnchorPane paneAttendence;

    @FXML
    private TableView<AttendanceTM> tblAttendance;

    @FXML
    private JFXTextField txtDayCount;

    AttendanceModel attendanceModel = new AttendanceModel();
    StudentModel studentModel = new StudentModel();
    DriverModel driverModel = new DriverModel();
    VehicleModel vehicleModel = new VehicleModel();
    StudentRegistrationModel studentRegistrationModel = new StudentRegistrationModel();

    @FXML
    void btnGoBackOnMouseClicked(MouseEvent event) throws IOException {
        paneAttendence.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml"));
        paneAttendence.getChildren().add(anchorPane);
    }

    private boolean validateDayCount() {
        try {
            int dayCount = Integer.parseInt(txtDayCount.getText());
            if (dayCount < 30 && dayCount >= 0) {
                return true;
            } else {
                new Alert(Alert.AlertType.ERROR, "Day count must be less than 30.").show();
                return false;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid number for day count.").show();
            return false;
        }
    }

    private void loadVehicleIds() throws SQLException {
        List<String> vehicleIds = vehicleModel.getAllVehicleIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(vehicleIds);
        cbVehicleId.setItems(observableList);
        cbVehicleId.setOnAction(event -> {
            try {
                String selectedVehicleId = cbVehicleId.getValue();
                loadStudentNames(selectedVehicleId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadStudentNames(String vehicleId) throws SQLException {
        ArrayList<String> studentIds = studentRegistrationModel.getStudentIdsByVehicleId(vehicleId);
        ArrayList<String> studentNames = new ArrayList<>();
        for (String studentId : studentIds) {
            studentNames.add(studentModel.getStudentNameById(studentId));
        }
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentNames);
        cbStudentId.setItems(observableList);
    }

    private void loadYears() {
        ObservableList<String> years = FXCollections.observableArrayList("2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040");
        cbYear.setItems(years);
    }

    private void loadMonths() {
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cbMonth.setItems(months);
    }

    private void refreshTable() throws SQLException {
        ArrayList<AttendanceDto> attendenceList = attendanceModel.getAllAttendances();
        ObservableList<AttendanceTM> attendanceTMList = FXCollections.observableArrayList();
        for (AttendanceDto dto : attendenceList) {
            ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/assets/icons/icons8-edit-90.png")));
            editIcon.setFitWidth(24);
            editIcon.setFitHeight(24);
            editIcon.getStyleClass().add("image");
            editIcon.setOnMouseClicked(event -> editAttendance(dto));

            ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/assets/icons/icons8-delete-90.png")));
            deleteIcon.setFitWidth(24);
            deleteIcon.setFitHeight(24);
            editIcon.getStyleClass().add("image");
            deleteIcon.setOnMouseClicked(event -> deleteAttendance(dto));

            HBox actionBox = new HBox(editIcon, deleteIcon);
            actionBox.setSpacing(20);

            AttendanceTM attendanceTM = new AttendanceTM(
                    dto.getAttendanceId(),
                    dto.getStudentId(),
                    dto.getVehicleId(),
                    dto.getYear(),
                    dto.getMonth(),
                    dto.getDayCount(),
                    actionBox
            );

            attendanceTMList.add(attendanceTM);
        }
        tblAttendance.setItems(attendanceTMList);
    }

    private void editAttendance(AttendanceDto dto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateAttendance.fxml"));
            AnchorPane pane = loader.load();

            AnchorPane overlayPane = new AnchorPane();
            overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
            overlayPane.setPrefSize(paneAttendence.getWidth(), paneAttendence.getHeight());

            UpdateAttendanceController controller = loader.getController();
            controller.setAttendanceData(dto);
            controller.setOverlayPane(overlayPane, paneAttendence);

            overlayPane.getChildren().add(pane);
            paneAttendence.getChildren().add(overlayPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAttendance(AttendanceDto dto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this attendance record?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = attendanceModel.deleteAttendence(dto.getAttendanceId());
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Attendance record deleted successfully!").show();
                    refreshTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete attendance record!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the attendance record: " + e.getMessage()).show();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colDayCount.setCellValueFactory(new PropertyValueFactory<>("dayCount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("actionBox"));

        try {
            loadStudentNames(cbVehicleId.getValue());
            loadVehicleIds();
            loadYears();
            loadMonths();
            refreshTable();
            String nextAttendanceId = attendanceModel.getNextAttendanceId();
            lblAttendenceId.setText(nextAttendanceId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        cbStudentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    String studentId = studentModel.getStudentIdByName(newValue);
                    lblStudentId.setText(studentId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cbVehicleId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    String registrationNo = vehicleModel.getRegistrationNoById(newValue);
                    lblVehicleRegistrationNo.setText(registrationNo);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Add listener to enable the Calculate Fees button when a record is selected
        tblAttendance.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnCalculateFees.setDisable(newValue == null);
        });
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        cbStudentId.setDisable(false);
        cbVehicleId.setDisable(false);
        cbYear.setDisable(false);
        cbMonth.setDisable(false);
        txtDayCount.setDisable(false);

        btnMakeAttendance.setDisable(false);
        btnReset.setDisable(false);
        btnCalculateFees.setDisable(false);

        refreshPage();
    }
    private void refreshPage() throws SQLException {
        refreshTable();
        String nextAttendanceId = attendanceModel.getNextAttendanceId();
        lblAttendenceId.setText(nextAttendanceId);
        cbStudentId.getSelectionModel().clearSelection();
        cbVehicleId.getSelectionModel().clearSelection();
        cbYear.getSelectionModel().clearSelection();
        cbMonth.getSelectionModel().clearSelection();
        txtDayCount.clear();
        lblStudentId.setText("Student Name");
        lblVehicleRegistrationNo.setText("Vehicle No");

    }

    @FXML
    public void btnMakeAttendenceOnAction(ActionEvent actionEvent) {
        if (!validateDayCount()) {
            return;
        }
        try {
            String studentName = cbStudentId.getValue();
            String studentId = studentModel.getStudentIdByName(studentName); // Add this method to StudentModel

            AttendanceDto attendanceDto = new AttendanceDto(
                    lblAttendenceId.getText(),
                    studentId,
                    cbVehicleId.getValue(),
                    Integer.parseInt(cbYear.getValue()),
                    cbMonth.getValue(),
                    Integer.parseInt(txtDayCount.getText())
            );

            boolean isAttendanceSaved = attendanceModel.saveAttendance(attendanceDto);

            if (isAttendanceSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Attendance saved successfully!").show();
                refreshTable();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save attendance!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving the attendance: " + e.getMessage()).show();
        }
    }

}