package lk.ijse.gdse72.swiftsts.controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.model.StudentRegistrationModel;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class StudentRegistrationController implements Initializable {
    @FXML
    public Label lblManageStudentOnClick;
    @FXML
    public JFXComboBox<String> cmbDestination;
    @FXML
    public JFXComboBox<String> cmbVehicle;
    @FXML
    public JFXButton btnReset;
    @FXML
    public ImageView viewTable;

    @FXML
    private JFXButton btnNewRoute;

    @FXML
    private JFXButton btnNewStudent;

    @FXML
    private JFXButton btnNewVehicle;

    @FXML
    private JFXButton btnRegister;

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
    public TableColumn<?, ?> colStudentName;

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

    StudentRegistrationModel studentRegistrationModel = new StudentRegistrationModel();

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
    void btnRegisterOnAction(ActionEvent event) {
        String studentId = txtStudentId.getSelectionModel().getSelectedItem();
        String studentRegId = lblRegistrationId.getText();
        String routeId = cmbRoute.getSelectionModel().getSelectedItem();
        String vehicleId = cmbVehicle.getSelectionModel().getSelectedItem();
        double dayPrice = Double.parseDouble(txtDayPrice.getText());
        String registrationDate = lblDate.getText();
        double distance = Double.parseDouble(txtDistance.getText());

        try {
            CrudUtil.startTransaction();

            boolean isStudentInserted = StudentRegistrationModel.insertStudentRegistration(studentRegId, studentId, distance, dayPrice, registrationDate, routeId, vehicleId);
            if (!isStudentInserted) throw new SQLException("Failed to insert into StudentRegistration");

            boolean isVehicleUpdated = StudentRegistrationModel.updateVehicleSeatCount(vehicleId, 1);
            if (!isVehicleUpdated) throw new SQLException("Failed to update Vehicle seat count");

            CrudUtil.commitTransaction();
            new Alert(Alert.AlertType.INFORMATION, "Student registered successfully!").show();
        } catch (SQLException e) {
            try {
                CrudUtil.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            new Alert(Alert.AlertType.ERROR, "Failed to register student: " + e.getMessage()).show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        // Table click action implementation
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        // Reset action implementation
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
            String nextRegistrationId = studentRegistrationModel.getNextRegistrationId();
            lblRegistrationId.setText(nextRegistrationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lblDate.setText(currentDate.format(formatter));

        txtStudentId.setOnAction(event -> {
            String selectedStudentId = txtStudentId.getSelectionModel().getSelectedItem();
            if (selectedStudentId != null) {
                try {
                    lableStudentName.setText(StudentRegistrationModel.getStudentNameById(selectedStudentId));
                    lblPickupLocation.setText(StudentRegistrationModel.getPickupLocationById(selectedStudentId));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        cmbVehicle.setOnAction(event -> {
            String selectedVehicleId = cmbVehicle.getSelectionModel().getSelectedItem();
            if (selectedVehicleId != null) {
                try {
                    int availableSeats = StudentRegistrationModel.getAvailableSeatCountByVehicleId(selectedVehicleId);
                    lblAvailableSeat.setText(String.valueOf(availableSeats));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadStudentIds() throws SQLException {
        List<String> studentIds = StudentRegistrationModel.getAllStudentIds();
        txtStudentId.getItems().addAll(studentIds);
    }

    private void loadVehicleIds() throws SQLException {
        List<String> vehicleIds = StudentRegistrationModel.getAllVehicleIds();
        cmbVehicle.getItems().addAll(vehicleIds);
    }

    private void loadRoutes() throws SQLException {
        List<String> routes = StudentRegistrationModel.getAllRoutes();
        cmbRoute.getItems().addAll(routes);
    }

    private void loadDestinations() throws SQLException {
        List<String> destinations = StudentRegistrationModel.getAllDestinations();
        cmbDestination.getItems().addAll(destinations);
    }
    @FXML
    public void viewTableOnClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StudentRegistrations.fxml"));
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
}