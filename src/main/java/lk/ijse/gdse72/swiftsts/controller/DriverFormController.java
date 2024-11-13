package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.paint.Paint;
import lk.ijse.gdse72.swiftsts.dto.DriverDto;
import lk.ijse.gdse72.swiftsts.model.DriverModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DriverFormController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<DriverDto, String> colAddress;

    @FXML
    private TableColumn<DriverDto, String> colContactNo;

    @FXML
    private TableColumn<DriverDto, String> colDriverId;

    @FXML
    private TableColumn<DriverDto, String> colDriverName;

    @FXML
    private TableColumn<DriverDto, String> colEmail;

    @FXML
    private TableColumn<DriverDto, String> colLicenseNo;

    @FXML
    private TableColumn<DriverDto, String> colNIC;

    @FXML
    private Label lblDriverId;

    @FXML
    private TableView<DriverDto> tblDriver;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtDriverName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtLicenseNo;

    @FXML
    private JFXTextField txtNIC;

    private DriverModel driverModel = new DriverModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String driverId = lblDriverId.getText();
        boolean isDeleted = driverModel.deleteDriver(driverId);
        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Driver deleted successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to delete driver!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String driverId = lblDriverId.getText();
        String name = txtDriverName.getText();
        String licenseNo = txtLicenseNo.getText();
        String nic = txtNIC.getText();
        String contactNo = txtContactNo.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)$";
        String licensePattern = "^[A-Za-z0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNIC = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNo = contactNo.matches(phonePattern);
        boolean isValidLicenseNo = licenseNo.matches(licensePattern);

        txtDriverName.setFocusColor(Paint.valueOf("black"));
        txtNIC.setFocusColor(Paint.valueOf("black"));
        txtEmail.setFocusColor(Paint.valueOf("black"));
        txtContactNo.setFocusColor(Paint.valueOf("black"));
        txtLicenseNo.setFocusColor(Paint.valueOf("black"));

        if (!isValidName) {
            txtDriverName.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidNIC) {
            txtNIC.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidEmail) {
            txtEmail.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidPhoneNo) {
            txtContactNo.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidLicenseNo) {
            txtLicenseNo.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidName && isValidNIC && isValidEmail && isValidPhoneNo && isValidLicenseNo) {
            DriverDto driverDto = new DriverDto(driverId, name, licenseNo, nic, contactNo, address, email);
            boolean isSaved = driverModel.saveDriver(driverDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Driver saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save driver!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String driverId = lblDriverId.getText();
        String name = txtDriverName.getText();
        String licenseNo = txtLicenseNo.getText();
        String nic = txtNIC.getText();
        String contactNo = txtContactNo.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)$";
        String licensePattern = "^[A-Za-z0-9]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNIC = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNo = contactNo.matches(phonePattern);
        boolean isValidLicenseNo = licenseNo.matches(licensePattern);

        txtDriverName.setFocusColor(Paint.valueOf("black"));
        txtNIC.setFocusColor(Paint.valueOf("black"));
        txtEmail.setFocusColor(Paint.valueOf("black"));
        txtContactNo.setFocusColor(Paint.valueOf("black"));
        txtLicenseNo.setFocusColor(Paint.valueOf("black"));

        if (!isValidName) {
            txtDriverName.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidNIC) {
            txtNIC.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidEmail) {
            txtEmail.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidPhoneNo) {
            txtContactNo.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidLicenseNo) {
            txtLicenseNo.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidName && isValidNIC && isValidEmail && isValidPhoneNo && isValidLicenseNo) {
            DriverDto driverDto = new DriverDto(driverId, name, licenseNo, nic, contactNo, address, email);
            boolean isUpdated = driverModel.updateDriver(driverDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Driver updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update driver!").show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverId"));
        colDriverName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLicenseNo.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addValidationListeners();
    }

    @FXML
    void onClickTable(MouseEvent event) {
        DriverDto selectedDriver = tblDriver.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            lblDriverId.setText(selectedDriver.getDriverId());
            txtDriverName.setText(selectedDriver.getName());
            txtLicenseNo.setText(selectedDriver.getLicenseNo());
            txtNIC.setText(selectedDriver.getNic());
            txtContactNo.setText(selectedDriver.getContactNo());
            txtAddress.setText(selectedDriver.getAddress());
            txtEmail.setText(selectedDriver.getEmail());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextDriverId = driverModel.getNextDriverId();
        lblDriverId.setText(nextDriverId);

        txtDriverName.setText("");
        txtLicenseNo.setText("");
        txtNIC.setText("");
        txtContactNo.setText("");
        txtAddress.setText("");
        txtEmail.setText("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<DriverDto> driverDtos = driverModel.getAllDrivers();
        ObservableList<DriverDto> driverTMS = FXCollections.observableArrayList(driverDtos);
        tblDriver.setItems(driverTMS);
    }

    private void addValidationListeners() {
        // Define regex patterns
        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)$";
        String licensePattern = "^[A-Za-z0-9]+$";

        // Add listener for each field
        txtDriverName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(namePattern)) {
                txtDriverName.setFocusColor(Paint.valueOf("red"));
            } else {
                txtDriverName.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtNIC.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(nicPattern)) {
                txtNIC.setFocusColor(Paint.valueOf("red"));
            } else {
                txtNIC.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(emailPattern)) {
                txtEmail.setFocusColor(Paint.valueOf("red"));
            } else {
                txtEmail.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtContactNo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(phonePattern)) {
                txtContactNo.setFocusColor(Paint.valueOf("red"));
            } else {
                txtContactNo.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtLicenseNo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(licensePattern)) {
                txtLicenseNo.setFocusColor(Paint.valueOf("red"));
            } else {
                txtLicenseNo.setFocusColor(Paint.valueOf("black"));
            }
        });
    }
}