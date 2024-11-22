package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import lk.ijse.gdse72.swiftsts.db.DBConnection;
import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.dto.tm.PaymentTM;
import lk.ijse.gdse72.swiftsts.dto.tm.StudentTM;
import lk.ijse.gdse72.swiftsts.model.AttendanceModel;
import lk.ijse.gdse72.swiftsts.model.PaymentModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PaymentFormController implements Initializable {

    @FXML
    public JFXButton btnPaymentReceipt;

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
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            colCreditBalance.setCellValueFactory(new PropertyValueFactory<>("creditBalance"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

            setNextPaymentId();
            loadStudentIds();
            loadAttendanceIds((String) cmbStudentId.getValue());
            loadPaymentData();
            lblPaymentDate.setText(LocalDate.now().toString());
            btnPaymentReceipt.setDisable(true);
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
                    dto.getAmount(),
                    dto.getBalance(),
                    dto.getCreditBalance(),
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
            double balance = payAmount - (monthlyFee + creditBalance);

            if (balance >= 0) {
                lblBalance.setText(String.format("%.2f", balance));
                lblCreditBalance.setText("0.00");
                creditBalance = 0;
            } else {
                lblBalance.setText("0.00");
                lblCreditBalance.setText(String.format("%.2f", -balance));
                creditBalance += balance;
            }

            PaymentDto paymentDto = new PaymentDto(
                    lblPaymentId.getText(),
                    studentId,
                    monthlyFee,
                    payAmount,
                    balance,
                    creditBalance,
                    creditBalance <= 0 ? "Paid" : "Pending",
                    LocalDate.now().toString()
            );

            CrudUtil.startTransaction();

            boolean isPaymentInserted = PaymentModel.insertPayment(paymentDto);
            if (!isPaymentInserted) throw new SQLException("Failed to insert into Payment");

            boolean isCreditBalanceUpdated = StudentModel.updateCreditBalance(studentId, creditBalance);
            if (!isCreditBalanceUpdated) throw new SQLException("Failed to update credit balance");

            CrudUtil.commitTransaction();
            new Alert(Alert.AlertType.INFORMATION, "Payment made successfully!").show();
            loadPaymentData(); // Refresh the table data
        } catch (SQLException | NumberFormatException e) {
            try {
                CrudUtil.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while making the payment: " + e.getMessage()).show();
        }
    }

    @FXML
    private void tblPaymentsOnClicked(MouseEvent event) {
        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            lblPaymentId.setText(selectedPayment.getPaymentId());
            btnPaymentReceipt.setDisable(false);
        } else {
            btnPaymentReceipt.setDisable(true);
        }
    }

    @FXML
    public void btnPaymentReceipt(ActionEvent actionEvent) {
        String selectedPaymentId = lblPaymentId.getText();

        if (selectedPaymentId == null || selectedPaymentId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a payment from the table.").show();
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/reports/PaymentReceipt.jrxml"));
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("PaymentId", selectedPaymentId);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate the report").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to connect to the database").show();
            e.printStackTrace();
        }
    }

}