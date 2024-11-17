package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse72.swiftsts.dto.VehicleDto;
import lk.ijse.gdse72.swiftsts.model.VehicleModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewVehicleFormController implements Initializable {

    @FXML
    private JFXButton btnDiscard;

    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblVehicleID;

    @FXML
    private AnchorPane paneNewVehicle;

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

    private AnchorPane overlayPane;
    private AnchorPane paneStudent;

    private final VehicleModel vehicleModel = new VehicleModel();

    public void setOverlayPane(AnchorPane overlayPane, AnchorPane paneStudent) {
        this.overlayPane = overlayPane;
        this.paneStudent = paneStudent;
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) {
        String vehicleId = lblVehicleID.getText();
        String model = txtModel.getText();
        String registrationNo = txtRegistrationNo.getText();
        String vehicleType = txtVehicleType.getText();
        String fuelType = txtFuelType.getText();
        int seatCount = Integer.parseInt(txtSeatCount.getText());
        int availableSeatCount = Integer.parseInt(txtAvailableSeatCount.getText());
        double engineCapacity = Double.parseDouble(txtEngineCapacity.getText());

        // Define regex patterns for validation
        String modelPattern = "^[A-Za-z0-9 ]+$";
        String registrationNoPattern = "^[A-Za-z0-9-]+$";
        String vehicleTypePattern = "^[A-Za-z ]+$";
        String fuelTypePattern = "^[A-Za-z ]+$";
        String seatCountPattern = "^[0-9]+$";
        String engineCapacityPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidModel = model.matches(modelPattern);
        boolean isValidRegistrationNo = registrationNo.matches(registrationNoPattern);
        boolean isValidVehicleType = vehicleType.matches(vehicleTypePattern);
        boolean isValidFuelType = fuelType.matches(fuelTypePattern);
        boolean isValidSeatCount = String.valueOf(seatCount).matches(seatCountPattern);
        boolean isValidAvailableSeatCount = String.valueOf(availableSeatCount).matches(seatCountPattern);
        boolean isValidEngineCapacity = String.valueOf(engineCapacity).matches(engineCapacityPattern);

        txtModel.setFocusColor(Paint.valueOf("black"));
        txtRegistrationNo.setFocusColor(Paint.valueOf("black"));
        txtVehicleType.setFocusColor(Paint.valueOf("black"));
        txtFuelType.setFocusColor(Paint.valueOf("black"));
        txtSeatCount.setFocusColor(Paint.valueOf("black"));
        txtAvailableSeatCount.setFocusColor(Paint.valueOf("black"));
        txtEngineCapacity.setFocusColor(Paint.valueOf("black"));

        if (!isValidModel) {
            txtModel.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidRegistrationNo) {
            txtRegistrationNo.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidVehicleType) {
            txtVehicleType.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidFuelType) {
            txtFuelType.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidSeatCount) {
            txtSeatCount.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidAvailableSeatCount) {
            txtAvailableSeatCount.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidEngineCapacity) {
            txtEngineCapacity.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidModel && isValidRegistrationNo && isValidVehicleType && isValidFuelType && isValidSeatCount && isValidAvailableSeatCount && isValidEngineCapacity) {
            VehicleDto vehicleDto = new VehicleDto(vehicleId, registrationNo, vehicleType,engineCapacity, fuelType,model,seatCount, availableSeatCount);

            try {
                boolean isSaved = vehicleModel.saveVehicle(vehicleDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Vehicle saved successfully!").show();
                    paneStudent.getChildren().remove(overlayPane);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save vehicle!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while saving the vehicle: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnDiscardOnAction(ActionEvent event) {
        paneStudent.getChildren().remove(overlayPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String nextVehicleId = vehicleModel.getNextVehicleId();
            lblVehicleID.setText(nextVehicleId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while generating the vehicle ID: " + e.getMessage()).show();
        }

        addValidationListeners();
    }

    private void addValidationListeners() {
        // Define regex patterns
        String modelPattern = "^[A-Za-z0-9 ]+$";
        String registrationNoPattern = "^[A-Za-z0-9-]+$";
        String vehicleTypePattern = "^[A-Za-z ]+$";
        String fuelTypePattern = "^[A-Za-z ]+$";
        String seatCountPattern = "^[0-9]+$";
        String engineCapacityPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        // Add listener for each field
        txtModel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(modelPattern)) {
                txtModel.setFocusColor(Paint.valueOf("red"));
            } else {
                txtModel.setFocusColor(Paint.valueOf("black"));
            }
        });

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

        txtSeatCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(seatCountPattern)) {
                txtSeatCount.setFocusColor(Paint.valueOf("red"));
            } else {
                txtSeatCount.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtAvailableSeatCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(seatCountPattern)) {
                txtAvailableSeatCount.setFocusColor(Paint.valueOf("red"));
            } else {
                txtAvailableSeatCount.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtEngineCapacity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(engineCapacityPattern)) {
                txtEngineCapacity.setFocusColor(Paint.valueOf("red"));
            } else {
                txtEngineCapacity.setFocusColor(Paint.valueOf("black"));
            }
        });
    }
}