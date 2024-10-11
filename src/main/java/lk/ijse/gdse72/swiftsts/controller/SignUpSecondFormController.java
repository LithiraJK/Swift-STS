package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignUpSecondFormController {

    @FXML
    private Label txtSignIn;

    @FXML
    private Label txtSignUp;
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

    public void txtSignInOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        paneSignUpSecond.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
        paneSignUpSecond.getChildren().add(anchorPane);
    }

    public void txtSignUpOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        paneSignUpSecond.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignUpForm.fxml"));
        paneSignUpSecond.getChildren().add(anchorPane);
    }
}
