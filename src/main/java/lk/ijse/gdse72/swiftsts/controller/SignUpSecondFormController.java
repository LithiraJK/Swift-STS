package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignUpSecondFormController {

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private AnchorPane paneSignUpSecond;

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        paneSignUpSecond.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
        paneSignUpSecond.getChildren().add(anchorPane);
    }

}
