package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse72.swiftsts.db.DBConnection;
import lk.ijse.gdse72.swiftsts.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OverViewFormController implements Initializable {

    ExpenseModel expenseModel = new ExpenseModel();
    PaymentModel paymentModel = new PaymentModel();
    StudentModel studentModel = new StudentModel();
    DriverModel driverModel = new DriverModel();
    VehicleModel vehicleModel = new VehicleModel();

    @FXML
    private ImageView btnGetExpenseReport;

    @FXML
    private ImageView btnGetIncomeReport;

    @FXML
    private JFXComboBox<String> cmbMonthEx;

    @FXML
    private JFXComboBox<String> cmbMonthIn;

    @FXML
    private Label lblDriverCount;

    @FXML
    private Label lblExpense;

    @FXML
    private Label lblIncome;

    @FXML
    private Label lblProfit;

    @FXML
    private Label lblStudentCount;

    @FXML
    private Label lblVehicleCount;

    @FXML
    private AnchorPane paneOverView;

    private void updateExpenseLabel() {
        YearMonth currentYearMonth = YearMonth.now();
        String formattedMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        try {
            double expense = expenseModel.getMonthlyExpense(formattedMonth);
            if (expense > 0) {
                lblExpense.setText("-" + String.format("%.2f", expense));
            } else {
                lblExpense.setText("No expense recorded for " + formattedMonth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblExpense.setText("Error fetching expense data");
        }
    }

    private void updateIncomeLabel() {
        YearMonth currentYearMonth = YearMonth.now();
        String formattedMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        try {
            double income = paymentModel.getMonthlyIncome(formattedMonth);
            if (income > 0) {
                lblIncome.setText("+" + String.format("%.2f", income));
            } else {
                lblIncome.setText("No income recorded for " + formattedMonth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblIncome.setText("Error fetching income data");
        }
    }

    @FXML
    void btnGetExpenseReportOnClick(MouseEvent event) {
        String selectedMonth = cmbMonthEx.getSelectionModel().getSelectedItem();
        if (selectedMonth != null) {
            int currentYear = YearMonth.now().getYear();
            YearMonth yearMonth = YearMonth.of(currentYear, cmbMonthEx.getSelectionModel().getSelectedIndex() + 1);
            String formattedMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            try {
                double expense = expenseModel.getMonthlyExpense(formattedMonth);
                if (expense > 0) {
                    lblExpense.setText("-" + String.format("%.2f", expense));
                } else {
                    lblExpense.setText("No expense recorded for " + formattedMonth);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                lblExpense.setText("Error fetching expense data");
            }
        } else {
            lblExpense.setText("Please select a month");
        }
    }

    @FXML
    void btnGetIncomeReportOnClick(MouseEvent event) {
        String selectedMonth = cmbMonthIn.getSelectionModel().getSelectedItem();
        if (selectedMonth != null) {
            int currentYear = YearMonth.now().getYear();
            YearMonth yearMonth = YearMonth.of(currentYear, cmbMonthIn.getSelectionModel().getSelectedIndex() + 1);
            String formattedMonth = yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            try {
                double income = paymentModel.getMonthlyIncome(formattedMonth);
                if (income > 0) {
                    Thread reportThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JasperReport jasperReport = JasperCompileManager.compileReport(
                                        getClass().getResourceAsStream("/reports/IncomeReport.jrxml"));
                                Connection connection = DBConnection.getInstance().getConnection();

                                Map<String, Object> parameters = new HashMap<>();
                                parameters.put("Month", formattedMonth);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(
                                        jasperReport,
                                        parameters,
                                        connection
                                );
                                Platform.runLater(() -> JasperViewer.viewReport(jasperPrint, false));
                            } catch (JRException e) {
                                Platform.runLater(() -> {
                                    new Alert(Alert.AlertType.ERROR, "Failed to generate the report").show();
                                    e.printStackTrace();
                                });
                            } catch (SQLException e) {
                                Platform.runLater(() -> {
                                    new Alert(Alert.AlertType.ERROR, "Failed to connect to the database").show();
                                    e.printStackTrace();
                                });
                            }
                        }
                    });
                    reportThread.start();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "No income for the selected month.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to retrieve income: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a month.").show();
        }
    }

    private void updateProfitLabel() {
        YearMonth currentYearMonth = YearMonth.now();
        String formattedMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        try {
            double income = paymentModel.getMonthlyIncome(formattedMonth);
            double expense = expenseModel.getMonthlyExpense(formattedMonth);
            double profit = income - expense;

            lblProfit.setText(String.format("+%.2f", profit));
        } catch (SQLException e) {
            e.printStackTrace();
            lblProfit.setText("Error calculating profit");
        }
    }

    private void updateStudentCountLabel() {
        try {
            int studentCount = studentModel.getStudentCount();
            lblStudentCount.setText(String.valueOf(studentCount));
        } catch (SQLException e) {
            e.printStackTrace();
            lblStudentCount.setText("Error fetching student count");
        }
    }

    private void updateDriverCountLabel() {
        try {
            int driverCount = driverModel.getDriverCount();
            lblDriverCount.setText(String.valueOf(driverCount));
        } catch (SQLException e) {
            e.printStackTrace();
            lblDriverCount.setText("Error fetching student count");
        }
    }

    private void updateVehicleCountLabel() {
        try {
            int vehicleCount = vehicleModel.getVehicleCount();
            lblVehicleCount.setText(String.valueOf(vehicleCount));
        } catch (SQLException e) {
            e.printStackTrace();
            lblDriverCount.setText("Error fetching student count");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> months = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        cmbMonthEx.setItems(months);
        cmbMonthEx.setVisibleRowCount(4);
        cmbMonthIn.setItems(months);
        cmbMonthIn.setVisibleRowCount(4);

        // Set up a Timeline to update the expense, income, and profit labels every minute
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(30), event -> {
            updateExpenseLabel();
            updateIncomeLabel();
            updateProfitLabel();
            updateStudentCountLabel();
            updateDriverCountLabel();
            updateVehicleCountLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Initial update
        updateExpenseLabel();
        updateIncomeLabel();
        updateProfitLabel();
        updateStudentCountLabel();
        updateDriverCountLabel();
        updateVehicleCountLabel();
    }
}