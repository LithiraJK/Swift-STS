package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StudentFormController {

    @FXML
    private JFXButton btnAttendence;

    @FXML
    private JFXButton btnSave;

    @FXML
    private AnchorPane paneStudent;

    @FXML
    void btnAttendenceOnAction(ActionEvent event) throws IOException {
        paneStudent.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/AttendenceForm.fxml"));
        paneStudent.getChildren().add(anchorPane);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

}
