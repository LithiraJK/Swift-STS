package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StudentEnrollController {

    @FXML
    public Label lblManageStudentOnClick;

    @FXML
    private JFXButton btnNewRoute;

    @FXML
    private JFXButton btnNewStudent;

    @FXML
    private JFXButton btnNewVehicle;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<?> cmbRoute;

    @FXML
    private TableColumn<?, ?> colDayPrice;

    @FXML
    private TableColumn<?, ?> colDestination;

    @FXML
    private TableColumn<?, ?> colDistance;

    @FXML
    private TableColumn<?, ?> colEnrollId;

    @FXML
    private TableColumn<?, ?> colPickupLocation;

    @FXML
    private TableColumn<?, ?> colRegistrationDate;

    @FXML
    private TableColumn<?, ?> colRouteId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colVehicleID;

    @FXML
    private Label lableStudentName;

    @FXML
    private Label lblAvailableSeat;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblPickupLocation;

    @FXML
    private Label lblRegistrationId;

    @FXML
    private TableView<?> tblStudentRegistration;

    @FXML
    private JFXToggleButton trbtnCalculateDistance;

    @FXML
    private JFXTextField txtDayPrice;

    @FXML
    private JFXComboBox<?> txtDestination;

    @FXML
    private JFXTextField txtDistance;

    @FXML
    private JFXComboBox<?> txtStudentId;

    @FXML
    private JFXComboBox<?> txtVehicle;

    @FXML
    private AnchorPane paneRegistration;

    @FXML
    void btnNewRouteOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewRouteForm.fxml"));
        AnchorPane anchorPane = loader.load();

        // Create a popup overlay (if not already part of the FXML file)
        AnchorPane overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
        overlayPane.setPrefSize(paneRegistration.getWidth(), paneRegistration.getHeight());

        // Center the anchorPane in the overlayPane (this will be your popup form)
        anchorPane.setLayoutX((overlayPane.getPrefWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((overlayPane.getPrefHeight() - anchorPane.getPrefHeight()) / 2);

        // Add the form to the overlay
        overlayPane.getChildren().add(anchorPane);

        // Add the overlayPane to paneStudent and make it visible
        paneRegistration.getChildren().add(overlayPane);

        // Get the controller for StudentRegisterForm
        NewRouteFormController controller = loader.getController();

        // Pass the overlayPane reference to allow the controller to close it
        controller.setOverlayPane(overlayPane, paneRegistration);

    }

    @FXML
    void btnNewStudentOnAction(ActionEvent event) throws IOException {
        // Load the NewStudentForm.fxml as the popup content
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewStudentForm.fxml"));
        AnchorPane anchorPane = loader.load();

        // Create a popup overlay (if not already part of the FXML file)
        AnchorPane overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
        overlayPane.setPrefSize(paneRegistration.getWidth(), paneRegistration.getHeight());

        // Center the anchorPane in the overlayPane (this will be your popup form)
        anchorPane.setLayoutX((overlayPane.getPrefWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((overlayPane.getPrefHeight() - anchorPane.getPrefHeight()) / 2);

        // Add the form to the overlay
        overlayPane.getChildren().add(anchorPane);

        // Add the overlayPane to paneStudent and make it visible
        paneRegistration.getChildren().add(overlayPane);

        // Get the controller for StudentRegisterForm
        NewStudentFormController controller = loader.getController();

        // Pass the overlayPane reference to allow the controller to close it
        controller.setOverlayPane(overlayPane, paneRegistration);
    }

    @FXML
    void btnNewVehicleOnAction(ActionEvent event) throws IOException {
        // Load the NewStudentForm.fxml as the popup content
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewVehicleForm.fxml"));
        AnchorPane anchorPane = loader.load();

        // Create a popup overlay (if not already part of the FXML file)
        AnchorPane overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
        overlayPane.setPrefSize(paneRegistration.getWidth(), paneRegistration.getHeight());

        // Center the anchorPane in the overlayPane (this will be your popup form)
        anchorPane.setLayoutX((overlayPane.getPrefWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((overlayPane.getPrefHeight() - anchorPane.getPrefHeight()) / 2);

        // Add the form to the overlay
        overlayPane.getChildren().add(anchorPane);

        // Add the overlayPane to paneStudent and make it visible
        paneRegistration.getChildren().add(overlayPane);

        // Get the controller for StudentRegisterForm
        NewVehicleFormController controller = loader.getController();

        // Pass the overlayPane reference to allow the controller to close it
        controller.setOverlayPane(overlayPane, paneRegistration);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {


    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @FXML
    public void lblManageStudentOnClick(MouseEvent mouseEvent) throws IOException {
        paneRegistration.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml"));
        paneRegistration.getChildren().add(anchorPane);
    }
}
