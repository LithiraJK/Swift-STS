package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class NewStudentFormController {
    @FXML
    public AnchorPane paneRegister;
    @FXML
    public JFXButton btnSave;

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
}