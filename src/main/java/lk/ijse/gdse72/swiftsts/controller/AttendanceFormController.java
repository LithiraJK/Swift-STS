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
import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.dto.tm.AttendanceTM;
import lk.ijse.gdse72.swiftsts.model.AttendanceModel;
import lk.ijse.gdse72.swiftsts.model.DriverModel;
import lk.ijse.gdse72.swiftsts.model.PaymentModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private TableColumn<AttendanceTM, String> colDriverId;

    @FXML
    public TableColumn<AttendanceTM, String> colStudentId;

    @FXML
    private TableColumn<AttendanceTM, String> colMonth;

    @FXML
    private TableColumn<AttendanceTM, Integer> colYear;

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
    private Label lblStudentName;

    @FXML
    private Label lblDriverName;

    @FXML
    private AnchorPane paneAttendence;

    @FXML
    private TableView<AttendanceTM> tblAttendance;

    @FXML
    private JFXTextField txtDayCount;

    private AttendanceModel attendanceModel = new AttendanceModel();
    private final StudentModel studentModel = new StudentModel();

    @FXML
    void btnGoBackOnMouseClicked(MouseEvent event) throws IOException {
        paneAttendence.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml"));
        paneAttendence.getChildren().add(anchorPane);
    }

    private void loadDriverIds() throws SQLException {
        ArrayList<String> driverIds = DriverModel.getAllDriverIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(driverIds);
        cbDriverId.setItems(observableList);
        cbDriverId.setOnAction(event -> {
            try {
                String selectedDriverId = cbDriverId.getValue();
                loadStudentIds(selectedDriverId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadStudentIds(String driverId) throws SQLException {
        ArrayList<String> studentIds = StudentModel.getStudentIdsByDriverId(driverId);
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentIds);
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
                    dto.getDriverId(),
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
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colDayCount.setCellValueFactory(new PropertyValueFactory<>("dayCount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("actionBox"));

        try {
            loadStudentIds( (String) cbDriverId.getValue());
            loadDriverIds();
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
                    String studentName = studentModel.getStudentNameById(newValue);
                    lblStudentName.setText(studentName);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cbDriverId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    String driverName = DriverModel.getDriverNameById(newValue);
                    lblDriverName.setText(driverName);
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
        cbDriverId.setDisable(false);
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
        cbDriverId.getSelectionModel().clearSelection();
        cbYear.getSelectionModel().clearSelection();
        cbMonth.getSelectionModel().clearSelection();
        txtDayCount.clear();
        lblStudentName.setText("Student Name");
        lblDriverName.setText("Driver Name");

    }

    @FXML
    public void btnMakeAttendenceOnAction(ActionEvent actionEvent) {
        try {
            AttendanceDto attendanceDto = new AttendanceDto(
                    lblAttendenceId.getText(),
                    cbStudentId.getValue(),
                    cbDriverId.getValue(),
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