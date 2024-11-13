package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.gdse72.swiftsts.dto.VehicleDto;
import lk.ijse.gdse72.swiftsts.dto.tm.VehicleTM;
import lk.ijse.gdse72.swiftsts.model.VehicleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class VehicleFormController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<VehicleTM, String> colAvailableSeatCount;

    @FXML
    private TableColumn<VehicleTM, String> colEngineCapacity;

    @FXML
    private TableColumn<VehicleTM, String> colFuelType;

    @FXML
    private TableColumn<VehicleTM, String> colModel;

    @FXML
    private TableColumn<VehicleTM, String> colRegistrationNo;

    @FXML
    private TableColumn<VehicleTM, String> colSeatCount;

    @FXML
    private TableColumn<VehicleTM, String> colVehicleID;

    @FXML
    private TableColumn<VehicleTM, String> colVehicleType;

    @FXML
    private Label lblVehicleID;

    @FXML
    private TableView<VehicleTM> tblVehicle;

    @FXML
    private JFXTextField txtAvailableSeatCount;

    @FXML
    private JFXTextField txtEngineCapacity;

    @FXML
    private JFXTextField txtFuelType;

    @FXML
    private JFXTextField txtModel;

    @FXML
    private JFXTextField txtRegistrationNo;

    @FXML
    private JFXTextField txtSeatCount;

    @FXML
    private JFXTextField txtVehicleType;

    private VehicleModel vehicleModel = new VehicleModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String vehicleId = lblVehicleID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this vehicle?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = vehicleModel.deleteVehicle(vehicleId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete vehicle!").show();
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
        String vehicleId = lblVehicleID.getText();
        String registrationNo = txtRegistrationNo.getText();
        String vehicleType = txtVehicleType.getText();
        double engineCapacity = Double.parseDouble(txtEngineCapacity.getText());
        String fuelType = txtFuelType.getText();
        String model = txtModel.getText();
        int seatCount = Integer.parseInt(txtSeatCount.getText());
        int availableSeatCount = Integer.parseInt(txtAvailableSeatCount.getText());

        // Define regex patterns for validation
        String registrationNoPattern = "^[A-Za-z0-9-]+$";
        String vehicleTypePattern = "^[A-Za-z ]+$";
        String fuelTypePattern = "^[A-Za-z ]+$";
        String modelPattern = "^[A-Za-z0-9-]+$";
        String numberPattern = "^[0-9]+$";

        boolean isValidRegistrationNo = registrationNo.matches(registrationNoPattern);
        boolean isValidVehicleType = vehicleType.matches(vehicleTypePattern);
        boolean isValidFuelType = fuelType.matches(fuelTypePattern);
        boolean isValidModel = model.matches(modelPattern);
        boolean isValidEngineCapacity = String.valueOf(engineCapacity).matches(numberPattern);
        boolean isValidSeatCount = String.valueOf(seatCount).matches(numberPattern);
        boolean isValidAvailableSeatCount = String.valueOf(availableSeatCount).matches(numberPattern);

        txtRegistrationNo.setFocusColor(Paint.valueOf("black"));
        txtVehicleType.setFocusColor(Paint.valueOf("black"));
        txtFuelType.setFocusColor(Paint.valueOf("black"));
        txtModel.setFocusColor(Paint.valueOf("black"));
        txtEngineCapacity.setFocusColor(Paint.valueOf("black"));
        txtSeatCount.setFocusColor(Paint.valueOf("black"));
        txtAvailableSeatCount.setFocusColor(Paint.valueOf("black"));

        if (!isValidRegistrationNo) {
            txtRegistrationNo.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidVehicleType) {
            txtVehicleType.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidFuelType) {
            txtFuelType.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidModel) {
            txtModel.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidEngineCapacity) {
            txtEngineCapacity.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidSeatCount) {
            txtSeatCount.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidAvailableSeatCount) {
            txtAvailableSeatCount.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidRegistrationNo && isValidVehicleType && isValidFuelType && isValidModel && isValidEngineCapacity && isValidSeatCount && isValidAvailableSeatCount) {
            VehicleDto vehicleDto = new VehicleDto(vehicleId, registrationNo, vehicleType, engineCapacity, fuelType, model, seatCount, availableSeatCount);

            try {
                boolean isSaved = vehicleModel.saveVehicle(vehicleDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save vehicle!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while saving the vehicle: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String vehicleId = lblVehicleID.getText();
        String registrationNo = txtRegistrationNo.getText();
        String vehicleType = txtVehicleType.getText();
        double engineCapacity = Double.parseDouble(txtEngineCapacity.getText());
        String fuelType = txtFuelType.getText();
        String model = txtModel.getText();
        int seatCount = Integer.parseInt(txtSeatCount.getText());
        int availableSeatCount = Integer.parseInt(txtAvailableSeatCount.getText());

        // Define regex patterns for validation
        String registrationNoPattern = "^[A-Za-z0-9-]+$";
        String vehicleTypePattern = "^[A-Za-z ]+$";
        String fuelTypePattern = "^[A-Za-z ]+$";
        String modelPattern = "^[A-Za-z0-9-]+$";
        String numberPattern = "^[0-9]+$";

        boolean isValidRegistrationNo = registrationNo.matches(registrationNoPattern);
        boolean isValidVehicleType = vehicleType.matches(vehicleTypePattern);
        boolean isValidFuelType = fuelType.matches(fuelTypePattern);
        boolean isValidModel = model.matches(modelPattern);
        boolean isValidEngineCapacity = String.valueOf(engineCapacity).matches(numberPattern);
        boolean isValidSeatCount = String.valueOf(seatCount).matches(numberPattern);
        boolean isValidAvailableSeatCount = String.valueOf(availableSeatCount).matches(numberPattern);

        txtRegistrationNo.setFocusColor(Paint.valueOf("black"));
        txtVehicleType.setFocusColor(Paint.valueOf("black"));
        txtFuelType.setFocusColor(Paint.valueOf("black"));
        txtModel.setFocusColor(Paint.valueOf("black"));
        txtEngineCapacity.setFocusColor(Paint.valueOf("black"));
        txtSeatCount.setFocusColor(Paint.valueOf("black"));
        txtAvailableSeatCount.setFocusColor(Paint.valueOf("black"));

        if (!isValidRegistrationNo) {
            txtRegistrationNo.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidVehicleType) {
            txtVehicleType.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidFuelType) {
            txtFuelType.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidModel) {
            txtModel.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidEngineCapacity) {
            txtEngineCapacity.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidSeatCount) {
            txtSeatCount.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidAvailableSeatCount) {
            txtAvailableSeatCount.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidRegistrationNo && isValidVehicleType && isValidFuelType && isValidModel && isValidEngineCapacity && isValidSeatCount && isValidAvailableSeatCount) {
            VehicleDto vehicleDto = new VehicleDto(vehicleId, registrationNo, vehicleType, engineCapacity, fuelType, model, seatCount, availableSeatCount);
            boolean isUpdated = vehicleModel.updateVehicle(vehicleDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Vehicle updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update vehicle!").show();
            }
        }
    }

    @FXML
    void tblVehicleOnClick(MouseEvent event) {
        VehicleTM selectedItem = tblVehicle.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblVehicleID.setText(selectedItem.getVehicleId());
            txtRegistrationNo.setText(selectedItem.getRegistrationNo());
            txtVehicleType.setText(selectedItem.getVehicleType());
            txtEngineCapacity.setText(String.valueOf(selectedItem.getEngineCapacity()));
            txtFuelType.setText(selectedItem.getFuelType());
            txtModel.setText(selectedItem.getModel());
            txtSeatCount.setText(String.valueOf(selectedItem.getSeatCount()));
            txtAvailableSeatCount.setText(String.valueOf(selectedItem.getAvailableSeatCount()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colVehicleID.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colRegistrationNo.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
        colVehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colEngineCapacity.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));
        colFuelType.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colSeatCount.setCellValueFactory(new PropertyValueFactory<>("seatCount"));
        colAvailableSeatCount.setCellValueFactory(new PropertyValueFactory<>("availableSeatCount"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addValidationListeners();
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextVehicleId = vehicleModel.getNextVehicleId();
        lblVehicleID.setText(nextVehicleId);

        txtRegistrationNo.setText("");
        txtVehicleType.setText("");
        txtEngineCapacity.setText("");
        txtFuelType.setText("");
        txtModel.setText("");
        txtSeatCount.setText("");
        txtAvailableSeatCount.setText("");

        txtRegistrationNo.setFocusColor(Paint.valueOf("black"));
        txtVehicleType.setFocusColor(Paint.valueOf("black"));
        txtFuelType.setFocusColor(Paint.valueOf("black"));
        txtModel.setFocusColor(Paint.valueOf("black"));
        txtEngineCapacity.setFocusColor(Paint.valueOf("black"));
        txtSeatCount.setFocusColor(Paint.valueOf("black"));
        txtAvailableSeatCount.setFocusColor(Paint.valueOf("black"));

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<VehicleDto> vehicleDtos = vehicleModel.getAllVehicles();
        ObservableList<VehicleTM> vehicleTMS = FXCollections.observableArrayList();

        for (VehicleDto vehicleDto : vehicleDtos) {
            VehicleTM vehicleTM = new VehicleTM(
                    vehicleDto.getVehicleId(),
                    vehicleDto.getRegistrationNo(),
                    vehicleDto.getVehicleType(),
                    vehicleDto.getEngineCapacity(),
                    vehicleDto.getFuelType(),
                    vehicleDto.getModel(),
                    vehicleDto.getSeatCount(),
                    vehicleDto.getAvailableSeatCount()
            );
            vehicleTMS.add(vehicleTM);
        }
        tblVehicle.setItems(vehicleTMS);
    }

    private void addValidationListeners() {
        // Define regex patterns
        String registrationNoPattern = "^[A-Za-z0-9-]+$";
        String vehicleTypePattern = "^[A-Za-z ]+$";
        String fuelTypePattern = "^[A-Za-z ]+$";
        String modelPattern = "^[A-Za-z0-9-]+$";
        String numberPattern = "^[0-9]+$";

        // Add listener for each field
        txtRegistrationNo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(registrationNoPattern)) {
                txtRegistrationNo.setFocusColor(Paint.valueOf("red"));
            } else {
                txtRegistrationNo.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtVehicleType.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(vehicleTypePattern)) {
                txtVehicleType.setFocusColor(Paint.valueOf("red"));
            } else {
                txtVehicleType.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtFuelType.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(fuelTypePattern)) {
                txtFuelType.setFocusColor(Paint.valueOf("red"));
            } else {
                txtFuelType.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtModel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(modelPattern)) {
                txtModel.setFocusColor(Paint.valueOf("red"));
            } else {
                txtModel.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtEngineCapacity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(numberPattern)) {
                txtEngineCapacity.setFocusColor(Paint.valueOf("red"));
            } else {
                txtEngineCapacity.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtSeatCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(numberPattern)) {
                txtSeatCount.setFocusColor(Paint.valueOf("red"));
            } else {
                txtSeatCount.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtAvailableSeatCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(numberPattern)) {
                txtAvailableSeatCount.setFocusColor(Paint.valueOf("red"));
            } else {
                txtAvailableSeatCount.setFocusColor(Paint.valueOf("black"));
            }
        });
    }
}