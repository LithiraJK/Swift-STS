package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {
    @FXML
    public JFXButton btnAttendence;
    @FXML
    public Label lblLocalDate;
    @FXML
    public Label lblLocalTime;
    @FXML
    public ImageView iconCalculator;
    @FXML
    public JFXButton btnRoute;
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
    private JFXButton btnPayment;

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
    void btnRouteOnAction(ActionEvent event) throws IOException {
        try {
            paneBody.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/RouteForm.fxml"));
            paneBody.getChildren().add(anchorPane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnScheduleOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        paneBody.getChildren().clear();
        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/PaymentForm.fxml"));
            paneBody.getChildren().add(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        paneBody.getChildren().clear();
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/StudentEnroll.fxml"));
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

        updateDateTime();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    private void updateDateTime() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E MMM dd yyyy", Locale.ENGLISH);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        lblLocalTime.setText(currentTime.format(timeFormatter));
        lblLocalDate.setText(currentDate.format(dateFormatter));
    }

    @FXML
    public void btnAttendenceOnAction(ActionEvent actionEvent) throws IOException {
        paneBody.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/AttendanceForm.fxml"));
        paneBody.getChildren().add(anchorPane);
    }
}
