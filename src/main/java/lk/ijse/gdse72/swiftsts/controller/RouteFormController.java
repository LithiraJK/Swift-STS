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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse72.swiftsts.dto.RouteDto;
import lk.ijse.gdse72.swiftsts.dto.tm.RouteTM;
import lk.ijse.gdse72.swiftsts.model.RouteModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class RouteFormController implements Initializable {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<RouteTM, Double> colRouteFee;

    @FXML
    private TableColumn<RouteTM, String> colDestination;

    @FXML
    private TableColumn<RouteTM, String> colRouteId;

    @FXML
    private TableColumn<RouteTM, String> colRouteName;

    @FXML
    private TableColumn<RouteTM, String> colStartPoint;

    @FXML
    private Label lblRouteId;

    @FXML
    private AnchorPane paneManageRoute;

    @FXML
    private TableView<RouteTM> tblRoute;

    @FXML
    private JFXTextField txtRouteFee;

    @FXML
    private JFXTextField txtDestination;

    @FXML
    private JFXTextField txtRouteName;

    @FXML
    private JFXTextField txtStartPoint;

    private RouteModel routeModel = new RouteModel();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String routeId = lblRouteId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this route?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = routeModel.deleteRoute(routeId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Route deleted successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete route!").show();
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
        String routeId = lblRouteId.getText();
        String routeName = txtRouteName.getText();
        String startPoint = txtStartPoint.getText();
        String destination = txtDestination.getText();
        double routeFee = Double.parseDouble(txtRouteFee.getText());

        // Define regex patterns for validation
        String routeNamePattern = "^[A-Za-z ]+$";
        String startPointPattern = "^[A-Za-z ]+$";
        String destinationPattern = "^[A-Za-z ]+$";
        String dayFeePattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidRouteName = routeName.matches(routeNamePattern);
        boolean isValidStartPoint = startPoint.matches(startPointPattern);
        boolean isValidDestination = destination.matches(destinationPattern);
        boolean isValidDayFee = String.valueOf(routeFee).matches(dayFeePattern);

        txtRouteName.setFocusColor(Paint.valueOf("black"));
        txtStartPoint.setFocusColor(Paint.valueOf("black"));
        txtDestination.setFocusColor(Paint.valueOf("black"));
        txtRouteFee.setFocusColor(Paint.valueOf("black"));

        if (!isValidRouteName) {
            txtRouteName.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidStartPoint) {
            txtStartPoint.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidDestination) {
            txtDestination.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidDayFee) {
            txtRouteFee.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidRouteName && isValidStartPoint && isValidDestination && isValidDayFee) {
            RouteDto routeDto = new RouteDto(routeId, routeName, startPoint, destination, routeFee);

            try {
                boolean isSaved = routeModel.saveRoute(routeDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Route saved successfully!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save route!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while saving the route: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String routeId = lblRouteId.getText();
        String routeName = txtRouteName.getText();
        String startPoint = txtStartPoint.getText();
        String destination = txtDestination.getText();
        double routeFee = Double.parseDouble(txtRouteFee.getText());

        // Define regex patterns for validation
        String routeNamePattern = "^[A-Za-z ]+$";
        String startPointPattern = "^[A-Za-z ]+$";
        String destinationPattern = "^[A-Za-z ]+$";
        String dayFeePattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidRouteName = routeName.matches(routeNamePattern);
        boolean isValidStartPoint = startPoint.matches(startPointPattern);
        boolean isValidDestination = destination.matches(destinationPattern);
        boolean isValidDayFee = String.valueOf(routeFee).matches(dayFeePattern);

        txtRouteName.setFocusColor(Paint.valueOf("black"));
        txtStartPoint.setFocusColor(Paint.valueOf("black"));
        txtDestination.setFocusColor(Paint.valueOf("black"));
        txtRouteFee.setFocusColor(Paint.valueOf("black"));

        if (!isValidRouteName) {
            txtRouteName.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidStartPoint) {
            txtStartPoint.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidDestination) {
            txtDestination.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidDayFee) {
            txtRouteFee.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidRouteName && isValidStartPoint && isValidDestination && isValidDayFee) {
            RouteDto routeDto = new RouteDto(routeId, routeName, startPoint, destination, routeFee);
            boolean isUpdated = routeModel.updateRoute(routeDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Route updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update route!").show();
            }
        }
    }

    @FXML
    void tblRouteOnClick(MouseEvent event) {
        RouteTM selectedItem = tblRoute.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblRouteId.setText(selectedItem.getRouteId());
            txtRouteName.setText(selectedItem.getRouteName());
            txtStartPoint.setText(selectedItem.getStartPoint());
            txtDestination.setText(selectedItem.getDestination());
            txtRouteFee.setText(String.valueOf(selectedItem.getRouteFee()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRouteId.setCellValueFactory(new PropertyValueFactory<>("routeId"));
        colRouteName.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        colStartPoint.setCellValueFactory(new PropertyValueFactory<>("startPoint"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colRouteFee.setCellValueFactory(new PropertyValueFactory<>("routeFee"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addValidationListeners();
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextRouteId = routeModel.getNextRouteId();
        lblRouteId.setText(nextRouteId);

        txtRouteName.setText("");
        txtStartPoint.setText("");
        txtDestination.setText("");
        txtRouteFee.setText("");

        txtRouteName.setFocusColor(Paint.valueOf("black"));
        txtStartPoint.setFocusColor(Paint.valueOf("black"));
        txtDestination.setFocusColor(Paint.valueOf("black"));
        txtRouteFee.setFocusColor(Paint.valueOf("black"));

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<RouteDto> routeDtos = routeModel.getAllRoutes();
        ObservableList<RouteTM> routeTMS = FXCollections.observableArrayList();

        for (RouteDto routeDto : routeDtos) {
            RouteTM routeTM = new RouteTM(
                    routeDto.getRouteId(),
                    routeDto.getRouteName(),
                    routeDto.getStartPoint(),
                    routeDto.getDestination(),
                    routeDto.getRouteFee()
            );
            routeTMS.add(routeTM);
        }
        tblRoute.setItems(routeTMS);
    }

    private void addValidationListeners() {
        // Define regex patterns
        String routeNamePattern = "^[A-Za-z ]+$";
        String startPointPattern = "^[A-Za-z ]+$";
        String destinationPattern = "^[A-Za-z ]+$";
        String routeFeePattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        // Add listener for each field
        txtRouteName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(routeNamePattern)) {
                txtRouteName.setFocusColor(Paint.valueOf("red"));
            } else {
                txtRouteName.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtStartPoint.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(startPointPattern)) {
                txtStartPoint.setFocusColor(Paint.valueOf("red"));
            } else {
                txtStartPoint.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtDestination.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(destinationPattern)) {
                txtDestination.setFocusColor(Paint.valueOf("red"));
            } else {
                txtDestination.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtRouteFee.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(routeFeePattern)) {
                txtRouteFee.setFocusColor(Paint.valueOf("red"));
            } else {
                txtRouteFee.setFocusColor(Paint.valueOf("black"));
            }
        });
    }
}