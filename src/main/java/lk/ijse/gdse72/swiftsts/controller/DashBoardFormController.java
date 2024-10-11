package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

    @FXML
    private JFXButton btnDashBoard;

    @FXML
    private JFXButton btnDriver;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnReports;

    @FXML
    private JFXButton btnSchedule;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnVehicle;

    @FXML
    private AnchorPane paneBody;

    @FXML
    private AnchorPane paneDashBoard;

    @FXML
    void btnDashBoard(ActionEvent event) throws IOException {
        paneBody.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/OverViewForm.fxml"));
        paneBody.getChildren().add(pane);
    }

    @FXML
    void btnDriverOnAction(ActionEvent event) throws IOException {
        paneBody.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DriverForm.fxml"));
        paneBody.getChildren().add(anchorPane);

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        paneDashBoard.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        paneDashBoard.getChildren().add(anchorPane);
    }

    @FXML
    void btnReportsOnAction(ActionEvent event) {

    }

    @FXML
    void btnScheduleOnAction(ActionEvent event) {

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        paneBody.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml"));
        paneBody.getChildren().add(pane);
    }

    @FXML
    void btnVehicleOnAction(ActionEvent event) throws IOException {
        paneBody.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/VehicleForm.fxml"));
        paneBody.getChildren().add(anchorPane);

    }

    public void calculatorOnClicked(MouseEvent mouseEvent) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CalculatorForm.fxml"));
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setTitle("Calculator");
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       try {
           paneBody.getChildren().clear();
           AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/OverViewForm.fxml"));
           paneBody.getChildren().add(anchorPane);
       }catch (IOException e) {
           e.printStackTrace();
       }
    }
}
