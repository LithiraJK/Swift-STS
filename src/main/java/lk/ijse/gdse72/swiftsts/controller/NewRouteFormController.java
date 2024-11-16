package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class NewRouteFormController {

    @FXML
    private JFXButton btnDiscard;

    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblRouteId;

    @FXML
    private AnchorPane paneNewRoute;

    @FXML
    private JFXTextField txtDayFee;

    @FXML
    private JFXTextField txtDestination;

    @FXML
    private JFXTextField txtRouteName;

    @FXML
    private JFXTextField txtStartPoint;

    private AnchorPane overlayPane;
    private AnchorPane paneStudent;

    public void setOverlayPane(AnchorPane overlayPane, AnchorPane paneStudent) {
        this.overlayPane = overlayPane;
        this.paneStudent = paneStudent;
    }

    @FXML
    void btnDiscardOnAction(ActionEvent event) {
        paneStudent.getChildren().remove(overlayPane);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        paneStudent.getChildren().remove(overlayPane);
    }

}
