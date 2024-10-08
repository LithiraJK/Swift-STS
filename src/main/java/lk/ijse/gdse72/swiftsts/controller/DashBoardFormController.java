package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashBoardFormController {

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
    void btnDriverOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Window window = paneDashBoard.getScene().getWindow();
        window.hide();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.show();
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
    void btnVehicleOnAction(ActionEvent event) {

    }

}
