package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.db.DBConnection;
import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.dto.tm.PaymentTM;
import lk.ijse.gdse72.swiftsts.model.PaymentModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public Label lblStudentName111;

    @FXML
    private JFXButton btnCalculatePayment;

    @FXML
    private JFXComboBox<String> cbStudentId;

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

    private PaymentModel paymentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            paymentModel = new PaymentModel(DBConnection.getInstance().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblPaymentDate.setText(LocalDate.now().toString());
        loadPaymentData();
    }

    private void loadPaymentData() {
        List<PaymentDto> paymentData = paymentModel.getPaymentData();
        ObservableList<PaymentTM> paymentList = FXCollections.observableArrayList();

        for (PaymentDto dto : paymentData) {
            PaymentTM tm = new PaymentTM(
                    dto.getPaymentId(),
                    dto.getStudentId(),
                    dto.getStudentName(),
                    dto.getMonthlyFee(),
                    dto.getAmount(),
                    dto.getBalance(),
                    dto.getStatus(),
                    dto.getDate()
            );
            paymentList.add(tm);
        }

        tblPayments.setItems(paymentList);
    }

    @FXML
    void btnCalculatePayment(ActionEvent event) {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String studentId = cbStudentId.getValue();
            int dayCount = Integer.parseInt(txtPayAmount.getText());
            double monthlyFee = paymentModel.calculateMonthlyFee(studentId, dayCount);

            PaymentDto paymentDto = new PaymentDto(
                    null, // Assuming paymentId is auto-generated
                    studentId,
                    null, // Assuming studentName is not needed here
                    monthlyFee,
                    0, // Assuming amount is not needed here
                    0, // Assuming balance is not needed here
                    "Pending", // Assuming status is "Pending"
                    LocalDate.now().toString()
            );

            boolean isPaymentUpdated = paymentModel.updatePayment(connection, paymentDto);

            if (!isPaymentUpdated) {
                connection.rollback();
                return;
            }

            connection.commit();
            loadPaymentData();
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

    @FXML
    void tblPaymentsOnClicked(MouseEvent event) {

    }
}