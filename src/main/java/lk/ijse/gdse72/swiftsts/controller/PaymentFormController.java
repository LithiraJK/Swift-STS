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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.dto.tm.PaymentTM;
import lk.ijse.gdse72.swiftsts.model.AttendanceModel;
import lk.ijse.gdse72.swiftsts.model.PaymentModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    @FXML
    private JFXButton btnCalculatepayment;

    @FXML
    private JFXButton btnMakePayment;

    @FXML
    private JFXComboBox<String> cmbAttendanceId;

    @FXML
    private JFXComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colStudentId;

    @FXML
    private TableColumn<PaymentTM, Double> colMonthlyFee;

    @FXML
    private TableColumn<PaymentTM, Double> colCreditBalance;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, Double> colBalance;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    @FXML
    private TableColumn<PaymentTM, String> colDate;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblCreditBalance;

    @FXML
    private Label lblMonthlyFee;

    @FXML
    private Label lblPaymentDate;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblStudentName;

    @FXML
    private AnchorPane panePayment;

    @FXML
    private TableView<PaymentTM> tblPayments;

    @FXML
    private JFXTextField txtPayAmount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
            colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            colMonthlyFee.setCellValueFactory(new PropertyValueFactory<>("monthlyFee"));
            colCreditBalance.setCellValueFactory(new PropertyValueFactory<>("creditBalance"));
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

            setNextPaymentId();
            loadStudentIds();
            loadAttendanceIds((String) cmbStudentId.getValue());
            loadPaymentData();
            lblPaymentDate.setText(LocalDate.now().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStudentIds() throws SQLException {
        ArrayList<String> studentIds = StudentModel.getAllStudentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(studentIds);
        cmbStudentId.setItems(observableList);
        cmbStudentId.setOnAction(event -> {
            try {
                String selectedStudentId = cmbStudentId.getValue();
                loadAttendanceIds(selectedStudentId);
                String studentName = StudentModel.getStudentNameById(selectedStudentId);
                lblStudentName.setText(studentName);
                double creditBalance = StudentModel.getCreditBalanceById(selectedStudentId);
                lblCreditBalance.setText(String.format("%.2f", creditBalance));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void setNextPaymentId() throws SQLException {
        String nextPaymentId = PaymentModel.getNextPaymentId();
        lblPaymentId.setText(nextPaymentId);
    }

    private void loadAttendanceIds(String studentId) throws SQLException {
        ArrayList<String> attendanceIds = AttendanceModel.getAttendanceIdsByStudentId(studentId);
        ObservableList<String> observableList = FXCollections.observableArrayList(attendanceIds);
        cmbAttendanceId.setItems(observableList);
    }


    private void loadPaymentData() throws SQLException {
        List<PaymentDto> paymentData = new PaymentModel().getPaymentData();
        ObservableList<PaymentTM> paymentTMs = FXCollections.observableArrayList();

        for (PaymentDto dto : paymentData) {
            paymentTMs.add(new PaymentTM(
                    dto.getPaymentId(),
                    dto.getStudentId(),
                    dto.getMonthlyFee(),
                    dto.getCreditBalance(),
                    dto.getAmount(),
                    dto.getBalance(),
                    dto.getStatus(),
                    java.sql.Date.valueOf(dto.getDate())
            ));
        }

        tblPayments.setItems(paymentTMs);
    }

    @FXML
    void btnCalculatepaymentOnAction(ActionEvent event) {
        try {
            String attendanceId = (String) cmbAttendanceId.getValue();
            if (attendanceId == null) {
                new Alert(Alert.AlertType.ERROR, "Please select an attendance ID.").show();
                return;
            }

            int dayCount = AttendanceModel.getDayCountByAttendanceId(attendanceId);
            double monthlyFee = PaymentModel.calculateMonthlyFee(cmbStudentId.getValue(), dayCount);

            lblMonthlyFee.setText(String.format("%.2f", monthlyFee));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while calculating the monthly fee: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnMakePaymentOnAction(ActionEvent event) {
        try {
            String studentId = (String) cmbStudentId.getValue();
            String attendanceId = (String) cmbAttendanceId.getValue();
            double payAmount = Double.parseDouble(txtPayAmount.getText());
            double creditBalance = Double.parseDouble(lblCreditBalance.getText());

            if (studentId == null || attendanceId == null || payAmount <= 0) {
                new Alert(Alert.AlertType.ERROR, "Please fill in all fields correctly.").show();
                return;
            }

            int dayCount = AttendanceModel.getDayCountByAttendanceId(attendanceId);
            double monthlyFee = PaymentModel.calculateMonthlyFee(cmbStudentId.getValue(), dayCount);
            double balance = (monthlyFee+creditBalance) - payAmount;



            PaymentDto paymentDto = new PaymentDto(
                    lblPaymentId.getText(),
                    studentId,
                    monthlyFee,
                    0.0,
                    payAmount,
                    balance,
                    balance <= 0 ? "Paid" : "Pending",
                    LocalDate.now().toString()
            );

            boolean isPaymentInserted = PaymentModel.insertPayment(paymentDto);
            if (isPaymentInserted) {
                new Alert(Alert.AlertType.INFORMATION, "Payment made successfully!").show();
                lblBalance.setText(String.format("%.2f", balance));
                loadPaymentData(); // Refresh the table data
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to make payment.").show();
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while making the payment: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblPaymentsOnClicked(MouseEvent event) {
        // Handle table row click event if needed
    }
}