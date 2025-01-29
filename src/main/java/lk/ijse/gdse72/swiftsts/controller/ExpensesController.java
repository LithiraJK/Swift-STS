package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import lk.ijse.gdse72.swiftsts.dto.ExpenseDto;
import lk.ijse.gdse72.swiftsts.dto.tm.ExpenseTM;
import lk.ijse.gdse72.swiftsts.model.ExpenseModel;
import lk.ijse.gdse72.swiftsts.model.UserModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExpensesController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbUserID;

    @FXML
    private TableColumn<ExpenseTM, Double> colAmount;

    @FXML
    private TableColumn<ExpenseTM, Date> colDate;

    @FXML
    private TableColumn<ExpenseTM, String> colDescription;

    @FXML
    private TableColumn<ExpenseTM, String> colExpenseId;

    @FXML
    private TableColumn<ExpenseTM, String> colUserId;

    @FXML
    private Label lblExpenseId;

    @FXML
    private TableView<ExpenseTM> tblExpenses;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtDescription;


    private ExpenseModel expenseModel = new ExpenseModel();
    UserModel userModel = new UserModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colExpenseId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            refreshPage();
            loadUserIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addValidationListeners();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String expenseId = lblExpenseId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this expense?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = expenseModel.deleteExpense(expenseId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Expense deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete expense!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String expenseId = lblExpenseId.getText();
        Date date = Date.valueOf(LocalDate.now());
        double amount = Double.parseDouble(txtAmount.getText());
        String description = txtDescription.getText();
        String userId = cmbUserID.getValue();

        // Define regex patterns for validation
        String descriptionPattern = "^[A-Za-z0-9 ]+$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidDescription = description.matches(descriptionPattern);
        boolean isValidAmount = String.valueOf(amount).matches(amountPattern);

        txtDescription.setFocusColor(Paint.valueOf("black"));
        txtAmount.setFocusColor(Paint.valueOf("black"));

        if (!isValidDescription) {
            txtDescription.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidAmount) {
            txtAmount.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidDescription && isValidAmount) {
            ExpenseDto expenseDto = new ExpenseDto(expenseId, date, amount, description, userId);

            try {
                boolean isSaved = expenseModel.saveExpense(expenseDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Expense saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save expense!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while saving the expense: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String expenseId = lblExpenseId.getText();
        Date date = Date.valueOf(LocalDate.now());
        double amount = Double.parseDouble(txtAmount.getText());
        String description = txtDescription.getText();
        String userId = cmbUserID.getValue();

        // Define regex patterns for validation
        String descriptionPattern = "^[A-Za-z0-9 ]+$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidDescription = description.matches(descriptionPattern);
        boolean isValidAmount = String.valueOf(amount).matches(amountPattern);

        txtDescription.setFocusColor(Paint.valueOf("black"));
        txtAmount.setFocusColor(Paint.valueOf("black"));

        if (!isValidDescription) {
            txtDescription.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidAmount) {
            txtAmount.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidDescription && isValidAmount) {
            ExpenseDto expenseDto = new ExpenseDto(expenseId, date, amount, description, userId);
            boolean isUpdated = expenseModel.updateExpense(expenseDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Expense updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update expense!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        ExpenseTM selectedItem = tblExpenses.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblExpenseId.setText(selectedItem.getExpenseId());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            txtDescription.setText(selectedItem.getDescription());
            cmbUserID.setValue(selectedItem.getUserId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void loadUserIds() throws SQLException {
        ArrayList<String> userIds = userModel.getAllUserIds();
        cmbUserID.setItems(FXCollections.observableArrayList(userIds));
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextExpenseId = expenseModel.getNextExpenseId();
        lblExpenseId.setText(nextExpenseId);

        txtAmount.setText("");
        txtDescription.setText("");
        cmbUserID.setValue(null);

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ExpenseDto> expenseDtos = expenseModel.getAllExpenses();
        ObservableList<ExpenseTM> expenseTMS = FXCollections.observableArrayList();

        for (ExpenseDto expenseDto : expenseDtos) {
            ExpenseTM expenseTM = new ExpenseTM(
                    expenseDto.getExpenseId(),
                    expenseDto.getDate(),
                    expenseDto.getAmount(),
                    expenseDto.getDescription(),
                    expenseDto.getUserId()
            );
            expenseTMS.add(expenseTM);
        }
        tblExpenses.setItems(expenseTMS);
    }

    private void addValidationListeners() {
        // Define regex patterns
        String descriptionPattern = "^[A-Za-z0-9 ]+$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        // Add listener for each field
        txtDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(descriptionPattern)) {
                txtDescription.setFocusColor(Paint.valueOf("red"));
            } else {
                txtDescription.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(amountPattern)) {
                txtAmount.setFocusColor(Paint.valueOf("red"));
            } else {
                txtAmount.setFocusColor(Paint.valueOf("black"));
            }
        });
    }
}