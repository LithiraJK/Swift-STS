package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SignUpFormController {
    @FXML
    public JFXTextField txtUsername;

    @FXML
    public JFXTextField txtEnterOTP;

    @FXML
    public JFXTextField txtEmail;

    @FXML
    private Label txtSignIn;

    @FXML
    private Label txtSignUp;

    @FXML
    private JFXButton btnNext;

    @FXML
    private JFXButton btnSendOTP;

    @FXML
    private AnchorPane paneSignUp;

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        paneSignUp.getChildren().clear();
        AnchorPane pane  =FXMLLoader.load(getClass().getResource("/view/SignUpSecondForm.fxml"));
        paneSignUp.getChildren().add(pane);
    }


    @FXML
    void btnSendOTPOnAction(ActionEvent event) {

    }


    public void txtSignInOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        paneSignUp.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
        paneSignUp.getChildren().add(anchorPane);
    }

    public void txtSignUpOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        paneSignUp.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignUpForm.fxml"));
        paneSignUp.getChildren().add(anchorPane);
    }
}
