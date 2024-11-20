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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.dto.tm.PaymentTM;
import lk.ijse.gdse72.swiftsts.model.PaymentModel;
import lk.ijse.gdse72.swiftsts.model.StudentModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    @FXML
    public Label lblStudentName112;
    @FXML
    public Label lblStudentName11;
    @FXML
    public Label lblStudentName2;

    @FXML
    public JFXComboBox<String> cmbStudentId;
    @FXML
    public JFXButton btnMakePayment;

    @FXML
    private JFXButton btnCalculatePayment;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, Double> colBalance;

    @FXML
    private TableColumn<PaymentTM, String> colDate;

    @FXML
    private TableColumn<PaymentTM, Double> colMonthlyFee;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    @FXML
    private TableColumn<PaymentTM, String> colStudentId;

    @FXML
    private TableView<PaymentTM> tblPayments;

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
    private JFXTextField txtPayAmount;

    private PaymentModel paymentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paymentModel = new PaymentModel();
        lblPaymentDate.setText(LocalDate.now().toString());
        try {
            loadPaymentData();
            loadStudentIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadStudentIds() throws SQLException {
        ArrayList<String> studentIds = StudentModel.getAllStudentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(studentIds);
        cmbStudentId.setItems(observableList);
    }

    @FXML
    private void cmbStudentIdOnAction(ActionEvent event) {
        String studentId = cmbStudentId.getValue();
        try {
            int dayCount = paymentModel.getAttendanceDayCount(studentId, LocalDate.now().getYear(), LocalDate.now().getMonthValue());
            double monthlyFee = paymentModel.calculateMonthlyFee(studentId, dayCount);
            lblMonthlyFee.setText(String.format("%.2f", monthlyFee));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to calculate monthly fee: " + e.getMessage()).show();
        }
    }

    @FXML
    public void btnMakePaymentOnAction(ActionEvent actionEvent) {
        try {
            String studentId = cmbStudentId.getValue();
            double paymentAmount = Double.parseDouble(txtPayAmount.getText());
            double monthlyFee = Double.parseDouble(lblMonthlyFee.getText());
            double creditBalance = Double.parseDouble(lblCreditBalance.getText());

            double totalBalance = monthlyFee + creditBalance;
            double remainingBalance = totalBalance - paymentAmount;

            if (remainingBalance < 0) {
                lblCreditBalance.setText(String.format("%.2f", -remainingBalance));
                new Alert(Alert.AlertType.INFORMATION, "Return Balance: " + String.format("%.2f", -remainingBalance)).show();
            } else {
                lblCreditBalance.setText(String.format("%.2f", remainingBalance));
            }

            PaymentDto paymentDto = new PaymentDto(
                    null, // Assuming paymentId is auto-generated
                    studentId,
                    null, // Assuming studentName is not needed here
                    monthlyFee,
                    paymentAmount,
                    Double.parseDouble(lblCreditBalance.getText()),
                    remainingBalance,
                    remainingBalance <= 0 ? "Paid" : "Pending",
                    Date.valueOf(LocalDate.now())
            );

            boolean isPaymentUpdated = paymentModel.updatePayment(paymentDto);

            if (!isPaymentUpdated) {
                new Alert(Alert.AlertType.ERROR, "Failed to update payment.").show();
                return;
            }

            loadPaymentData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating the payment: " + e.getMessage()).show();
        }
    }

    private void loadPaymentData() throws SQLException {
        List<PaymentDto> paymentData = paymentModel.getPaymentData();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDto paymentDto : paymentData) {
            paymentTMS.add(new PaymentTM(
                    paymentDto.getPaymentId(),
                    paymentDto.getStudentId(),
                    paymentDto.getMonthlyFee(),
                    paymentDto.getAmount(),
                    paymentDto.getCreditBalance(),
                    paymentDto.getBalance(),
                    paymentDto.getStatus(),
                    paymentDto.getDate()
            ));
        }

        tblPayments.setItems(paymentTMS);
    }

    @FXML
    void tblPaymentsOnClicked(MouseEvent event) {
        // Handle table row click event if needed
    }
}