package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.model.StudentEnrollModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class StudentEnrollController implements Initializable {

    @FXML
    public Label lblManageStudentOnClick;
    @FXML
    public JFXComboBox<String> cmbDestination;
    @FXML
    public JFXComboBox<String> cmbVehicle;

    @FXML
    private JFXButton btnNewRoute;

    @FXML
    private JFXButton btnNewStudent;

    @FXML
    private JFXButton btnNewVehicle;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXComboBox<String> cmbRoute;

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
    private JFXTextField txtDistance;

    @FXML
    private JFXComboBox<String> txtStudentId;

    @FXML
    private AnchorPane paneRegistration;

    StudentEnrollModel studentEnrollModel = new StudentEnrollModel();

    @FXML
    void btnNewRouteOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewRouteForm.fxml"));
        AnchorPane anchorPane = loader.load();

        AnchorPane overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
        overlayPane.setPrefSize(paneRegistration.getWidth(), paneRegistration.getHeight());

        anchorPane.setLayoutX((overlayPane.getPrefWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((overlayPane.getPrefHeight() - anchorPane.getPrefHeight()) / 2);

        overlayPane.getChildren().add(anchorPane);
        paneRegistration.getChildren().add(overlayPane);

        NewRouteFormController controller = loader.getController();
        controller.setOverlayPane(overlayPane, paneRegistration);
    }

    @FXML
    void btnNewStudentOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewStudentForm.fxml"));
        AnchorPane anchorPane = loader.load();

        AnchorPane overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
        overlayPane.setPrefSize(paneRegistration.getWidth(), paneRegistration.getHeight());

        anchorPane.setLayoutX((overlayPane.getPrefWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((overlayPane.getPrefHeight() - anchorPane.getPrefHeight()) / 2);

        overlayPane.getChildren().add(anchorPane);
        paneRegistration.getChildren().add(overlayPane);

        NewStudentFormController controller = loader.getController();
        controller.setOverlayPane(overlayPane, paneRegistration);
    }

    @FXML
    void btnNewVehicleOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewVehicleForm.fxml"));
        AnchorPane anchorPane = loader.load();

        AnchorPane overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
        overlayPane.setPrefSize(paneRegistration.getWidth(), paneRegistration.getHeight());

        anchorPane.setLayoutX((overlayPane.getPrefWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((overlayPane.getPrefHeight() - anchorPane.getPrefHeight()) / 2);

        overlayPane.getChildren().add(anchorPane);
        paneRegistration.getChildren().add(overlayPane);

        NewVehicleFormController controller = loader.getController();
        controller.setOverlayPane(overlayPane, paneRegistration);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        // Save action implementation
    }

    @FXML
    void onClickTable(MouseEvent event) {
        // Table click action implementation
    }

    @FXML
    public void lblManageStudentOnClick(MouseEvent mouseEvent) throws IOException {
        paneRegistration.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml"));
        paneRegistration.getChildren().add(anchorPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadStudentIds();
            loadRoutes();
            loadDestinations();
            loadVehicleIds();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String nextRegistrationId = studentEnrollModel.getNextRegistrationId();
            lblRegistrationId.setText(nextRegistrationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtStudentId.setOnAction(event -> {
            String selectedStudentId = txtStudentId.getSelectionModel().getSelectedItem();
            if (selectedStudentId != null) {
                try {
                    lableStudentName.setText(StudentEnrollModel.getStudentNameById(selectedStudentId));
                    lblPickupLocation.setText(StudentEnrollModel.getPickupLocationById(selectedStudentId));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cmbVehicle.setOnAction(event -> {
            String selectedVehicleId = cmbVehicle.getSelectionModel().getSelectedItem();
            if (selectedVehicleId != null) {
                try {
                    int availableSeats = StudentEnrollModel.getAvailableSeatCountByVehicleId(selectedVehicleId);
                    lblAvailableSeat.setText(String.valueOf(availableSeats));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadStudentIds() throws SQLException {
        List<String> studentIds = StudentEnrollModel.getAllStudentIds();
        txtStudentId.getItems().addAll(studentIds);
    }

    private void loadVehicleIds() throws SQLException {
        List<String> vehicleIds = StudentEnrollModel.getAllVehicleIds();
        cmbVehicle.getItems().addAll(vehicleIds);
    }

    private void loadRoutes() throws SQLException {
        List<String> routes = StudentEnrollModel.getAllRoutes();
        cmbRoute.getItems().addAll(routes);
    }

    private void loadDestinations() throws SQLException {
        List<String> destinations = StudentEnrollModel.getAllDestinations();
        cmbDestination.getItems().addAll(destinations);
    }
}