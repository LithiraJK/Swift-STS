package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class VehicleFormController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colEngineCapacity;

    @FXML
    private TableColumn<?, ?> colFuelType;

    @FXML
    private TableColumn<?, ?> colModelNo;

    @FXML
    private TableColumn<?, ?> colRegistrationNo;

    @FXML
    private TableColumn<?, ?> colVehicleID;

    @FXML
    private TableColumn<?, ?> colVehicleNo;

    @FXML
    private TableColumn<?, ?> colVehicleType;

    @FXML
    private Label lblVehicleID;

    @FXML
    private AnchorPane paneVehicle;

    @FXML
    private TableView<?> tblVehicle;

    @FXML
    private JFXTextField txtEngineCapacity;

    @FXML
    private JFXTextField txtFuelType;

    @FXML
    private JFXTextField txtModel;

    @FXML
    private JFXTextField txtRegistrationNo;

    @FXML
    private JFXTextField txtVehicleNo;

    @FXML
    private JFXTextField txtVehicleType;

}
