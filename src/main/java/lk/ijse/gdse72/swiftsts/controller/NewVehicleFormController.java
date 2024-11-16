package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class NewVehicleFormController {

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

    public void setOverlayPane(AnchorPane overlayPane, AnchorPane paneStudent) {
        this.overlayPane = overlayPane;
        this.paneStudent = paneStudent;
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) {
        // Add a close button functionality to the overlay pane
        paneStudent.getChildren().remove(overlayPane);
    }

    @FXML
    void btnDiscardOnAction(ActionEvent event) {
        paneStudent.getChildren().remove(overlayPane);
    }

}
