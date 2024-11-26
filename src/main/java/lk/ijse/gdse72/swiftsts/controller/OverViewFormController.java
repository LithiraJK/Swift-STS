package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.gdse72.swiftsts.model.ExpenseModel;
import lk.ijse.gdse72.swiftsts.model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OverViewFormController implements Initializable {

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
            double expense = ExpenseModel.getMonthlyExpense(formattedMonth);
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
            double income = PaymentModel.getMonthlyIncome(formattedMonth);
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
                double expense = ExpenseModel.getMonthlyExpense(formattedMonth);
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
                double income = PaymentModel.getMonthlyIncome(formattedMonth);
                if (income > 0) {
                    lblIncome.setText("+" + String.format("%.2f", income));
                } else {
                    lblIncome.setText("No income recorded for " + formattedMonth);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                lblIncome.setText("Error fetching income data");
            }
        } else {
            lblIncome.setText("Please select a month");
        }
    }
    private void updateProfitLabel() {
        YearMonth currentYearMonth = YearMonth.now();
        String formattedMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        try {
            double income = PaymentModel.getMonthlyIncome(formattedMonth);
            double expense = ExpenseModel.getMonthlyExpense(formattedMonth);
            double profit = income - expense;

            lblProfit.setText(String.format("+%.2f", profit));
        } catch (SQLException e) {
            e.printStackTrace();
            lblProfit.setText("Error calculating profit");
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
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
            updateExpenseLabel();
            updateIncomeLabel();
            updateProfitLabel();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Initial update
        updateExpenseLabel();
        updateIncomeLabel();
        updateProfitLabel();
    }
}