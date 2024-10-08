package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignUpFormController {


    @FXML
    private JFXButton btnNext;

    @FXML
    private JFXButton btnSendOTP;

    @FXML
    private AnchorPane paneSignUp;

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        paneSignUp.getChildren().clear();
        AnchorPane anchorPane =FXMLLoader.load(getClass().getResource("/view/SignUpSecondForm.fxml"));
        paneSignUp.getChildren().add(anchorPane);
    }

    @FXML
    void btnSendOTPOnAction(ActionEvent event) {

    }

}
