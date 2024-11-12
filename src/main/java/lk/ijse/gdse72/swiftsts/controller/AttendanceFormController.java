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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import lk.ijse.gdse72.swiftsts.db.DBConnection;
import lk.ijse.gdse72.swiftsts.dto.AttendanceDto;
import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.model.AttendanceModel;
import lk.ijse.gdse72.swiftsts.model.DriverModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;
import lk.ijse.gdse72.swiftsts.model.PaymentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttendanceFormController implements Initializable {
    @FXML
    public JFXButton btnReset;
    @FXML
    public JFXButton btnMakeAttendence;
    @FXML
    public JFXButton btnCalculatePayment;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private ImageView btnGoBack;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<AttendanceDto, Void> colAction;

    @FXML
    private JFXComboBox<String> cbDriverId;

    @FXML
    private JFXComboBox<String> cbMonth;

    @FXML
    private JFXComboBox<String> cbStudentId;

    @FXML
    private JFXComboBox<String> cbYear;

    @FXML
    private TableColumn<AttendanceDto, String> colAttendenceId;

    @FXML
    private TableColumn<AttendanceDto, Integer> colDayCount;

    @FXML
    private TableColumn<AttendanceDto, String> colDriverId;

    @FXML
    private TableColumn<AttendanceDto, String> colDriverName;

    @FXML
    private TableColumn<AttendanceDto, String> colMonth;

    @FXML
    private TableColumn<AttendanceDto, Integer> colYear;

    @FXML
    private Label lblAttendenceId;

    @FXML
    private Label lblStudentName;

    @FXML
    private Label lblDriverName;

    @FXML
    private AnchorPane paneAttendence;

    @FXML
    private TableView<AttendanceDto> tblAttendence;

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
        ObservableList<String> years = FXCollections.observableArrayList("2021", "2022", "2023", "2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040");
        cbYear.setItems(years);
    }

    private void loadMonths() {
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cbMonth.setItems(months);
    }

    private void refreshTable() throws SQLException {
        ArrayList<AttendanceDto> attendenceList = attendanceModel.getAllAttendances();
        tblAttendence.getItems().setAll(attendenceList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadStudentIds();
            loadDriverIds();
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

        // Add listener to cbStudentId
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

        // Add listener to cbDriverId
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

        addButtonToTable();
    }

    private void addButtonToTable() {
        Callback<TableColumn<AttendanceDto, Void>, TableCell<AttendanceDto, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<AttendanceDto, Void> call(final TableColumn<AttendanceDto, Void> param) {
                final TableCell<AttendanceDto, Void> cell = new TableCell<>() {

                    private final Button editButton = new Button();
                    private final Button deleteButton = new Button();

                    {
                        editButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/view/assets/icons/icons8-edit-64.png"))));
                        deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/view/assets/icons/icons8-remove-24 (1).png"))));

                        editButton.setOnAction(event -> {
                            AttendanceDto data = getTableView().getItems().get(getIndex());
                            AttendanceDto dto = new AttendanceDto(
                                    lblAttendenceId.getText(),
                                    cbStudentId.getValue(),
                                    cbDriverId.getValue(),
                                    Integer.parseInt(cbYear.getValue()),
                                    cbMonth.getValue(),
                                    Integer.parseInt(txtDayCount.getText())
                            );
                            try {
                                boolean isUpdated = attendanceModel.updateAttendence(dto);
                                if (isUpdated) {
                                    refreshTable();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                        deleteButton.setOnAction(event -> {
                            AttendanceDto data = getTableView().getItems().get(getIndex());
                            String attendenceId = lblAttendenceId.getText();
                            try {
                                boolean isDeleted = attendanceModel.deleteAttendence(attendenceId);
                                if (isDeleted) {
                                    refreshTable();
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(editButton, deleteButton);
                            hbox.setSpacing(10);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        };

        colAction.setCellFactory(cellFactory);
    }

    @FXML
    public void btnResetOnAction(ActionEvent actionEvent) {

    }
    @FXML
    public void btnMakeAttendenceOnAction(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Step 1: Save the Attendance
            AttendanceDto attendanceDto = new AttendanceDto(
                    lblAttendenceId.getText(),
                    cbStudentId.getValue(),
                    cbDriverId.getValue(),
                    Integer.parseInt(cbYear.getValue()),
                    cbMonth.getValue(),
                    Integer.parseInt(txtDayCount.getText())
            );
            boolean isAttendanceSaved = attendanceModel.saveAttendance(attendanceDto);

            if (!isAttendanceSaved) {
                connection.rollback();
                return;
            }

            // Step 2: Calculate the Payment
            int dayCount = attendanceModel.getMonthlyDayCount(connection, cbStudentId.getValue(), cbYear.getValue(), cbMonth.getValue());
            double paymentAmount = calculatePayment(dayCount);

            // Step 3: Update the Payment
            PaymentDto paymentDto = new PaymentDto(
                    null, // Assuming paymentId is auto-generated
                    cbStudentId.getValue(),
                    null, // Assuming studentName is not needed here
                    paymentAmount,
                    0, // Assuming amount is not needed here
                    0, // Assuming balance is not needed here
                    "Pending", // Assuming status is "Pending"
                    LocalDate.now().toString()
            );
            boolean isPaymentUpdated = PaymentModel.updatePayment(connection, paymentDto);

            if (!isPaymentUpdated) {
                connection.rollback();
                return;
            }

            // Step 4: Commit the Transaction
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private double calculatePayment(int dayCount) {
        // Implement your payment calculation logic here
        return dayCount * 10.0; // Example calculation
    }

    @FXML
    public void btnCalculatePaymentOnAction(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            // Retrieve the monthly day count from the attendance table
            int dayCount = attendanceModel.getMonthlyDayCount(connection, cbStudentId.getValue(), cbYear.getValue(), cbMonth.getValue());

            // Calculate the payment based on the day count
            double paymentAmount = calculatePayment(dayCount);

            // Update the payment table with the calculated payment
            boolean isPaymentUpdated = PaymentModel.updatePayment(connection, new PaymentDto(
                    null, // Assuming paymentId is auto-generated
                    cbStudentId.getValue(),
                    null, // Assuming studentName is not needed here
                    paymentAmount,
                    0, // Assuming amount is not needed here
                    0, // Assuming balance is not needed here
                    "Pending", // Assuming status is "Pending"
                    LocalDate.now().toString()
            ));

            if (!isPaymentUpdated) {
                connection.rollback();
                return;
            }

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}