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
import lk.ijse.gdse72.swiftsts.model.AttendanceModel;
import lk.ijse.gdse72.swiftsts.model.DriverModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttendanceFormController implements Initializable {
    @FXML
    public JFXButton btnReset;
    @FXML
    public JFXButton btnMakeAttendance;
    @FXML
    public JFXButton btnCalculatePayment;
    @FXML
    public TableColumn<AttendanceTM, HBox> colAction;

    @FXML
    public TableColumn<AttendanceTM, String> colAttendanceId;
    @FXML
    public Label lblMonthlyfee;

    @FXML
    public JFXButton btnMakePayment;

    @FXML
    private ImageView btnGoBack;

    @FXML
    private TableColumn<AttendanceTM, Integer> colDayCount;

    @FXML
    private TableColumn<AttendanceTM, String> colDriverId;

    @FXML
    public TableColumn<AttendanceTM, String> colStudentName;

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

    private void loadStudentIds() throws SQLException {
        ArrayList<String> studentIds = StudentModel.getAllStudentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentIds);
        cbStudentId.setItems(observableList);
    }

    private void loadDriverIds() throws SQLException {
        ArrayList<String> driverIds = DriverModel.getAllDriverIds();
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
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colDayCount.setCellValueFactory(new PropertyValueFactory<>("dayCount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("actionBox"));

        try {
            loadStudentIds();
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
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
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

    private double calculatePayment(int dayCount) {
        return dayCount * 10.0;
    }


    @FXML
    public void btnMakePaymentOnAction(ActionEvent actionEvent) {
//        Connection connection = null;
//        try {
//            connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//
//            AttendanceDto attendanceDto = new AttendanceDto(
//                    lblAttendenceId.getText(),
//                    cbStudentId.getValue(),
//                    cbDriverId.getValue(),
//                    Integer.parseInt(cbYear.getValue()),
//                    cbMonth.getValue(),
//                    Integer.parseInt(txtDayCount.getText())
//            );
//            boolean isAttendanceSaved = attendanceModel.saveAttendance(attendanceDto);
//
//            if (!isAttendanceSaved) {
//                connection.rollback();
//                return;
//            }
//
//            // Step 2: Calculate the Payment
//            int dayCount = attendanceModel.getMonthlyDayCount(connection, cbStudentId.getValue(), cbYear.getValue(), cbMonth.getValue());
//            double paymentAmount = calculatePayment(dayCount);
//
//            // Step 3: Update the Payment
//            PaymentDto paymentDto = new PaymentDto(
//                    null,
//                    cbStudentId.getValue(),
//                    lblStudentName.getText(),
//                    paymentAmount,
//                    0,
//                    0,
//                    "Pending",
//                    LocalDate.now().toString()
//            );
//            boolean isPaymentUpdated = PaymentModel.updatePayment(connection, paymentDto);
//
//            if (!isPaymentUpdated) {
//                connection.rollback();
//                return;
//            }
//
//            // Step 4: Commit the Transaction
//            connection.commit();
//        } catch (SQLException e) {
//            if (connection != null) {
//                try {
//                    connection.rollback();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.setAutoCommit(true);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}