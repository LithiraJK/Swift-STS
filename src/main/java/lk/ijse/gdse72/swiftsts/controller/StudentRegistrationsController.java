package lk.ijse.gdse72.swiftsts.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.dto.StudentRegistrationDto;
import lk.ijse.gdse72.swiftsts.model.StudentRegistrationModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentRegistrationsController implements Initializable {

    @FXML
    private ImageView btnBack;

    @FXML
    private TableColumn<StudentRegistrationDto, Date> colDate;

    @FXML
    private TableColumn<StudentRegistrationDto, Double> colDayPrice;

    @FXML
    private TableColumn<StudentRegistrationDto, Double> colDistance;

    @FXML
    private TableColumn<StudentRegistrationDto, String> colRouteName; // Changed from colRouteId to colRouteName

    @FXML
    private TableColumn<StudentRegistrationDto, String> colStudentId;

    @FXML
    private TableColumn<StudentRegistrationDto, String> colStudentRegistrationId;

    @FXML
    private TableColumn<StudentRegistrationDto, String> colVehicleId;

    @FXML
    private AnchorPane paneRegistrationDetails;

    @FXML
    private TableView<StudentRegistrationDto> tblStudentRegistration;

    private StudentRegistrationModel studentRegistrationModel = new StudentRegistrationModel();

    @FXML
    void btnBackOnClick(MouseEvent event) throws IOException {
        paneRegistrationDetails.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/StudentRegistration.fxml"));
        paneRegistrationDetails.getChildren().add(anchorPane);
    }

    @FXML
    void tblStudentRegistrationOnClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStudentRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colRouteName.setCellValueFactory(new PropertyValueFactory<>("routeName")); // Changed from routeId to routeName
        colVehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colDayPrice.setCellValueFactory(new PropertyValueFactory<>("dayPrice"));
        colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        try {
            loadStudentRegistrations();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadStudentRegistrations() throws SQLException {
        ArrayList<StudentRegistrationDto> studentRegistrations = studentRegistrationModel.getAllStudentRegistrations();
        ObservableList<StudentRegistrationDto> studentRegistrationList = FXCollections.observableArrayList(studentRegistrations);
        tblStudentRegistration.setItems(studentRegistrationList);
    }
}