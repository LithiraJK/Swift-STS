package lk.ijse.gdse72.swiftsts.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private AnchorPane pageLoginForm;

    @FXML
    private Label txtSignIn;

    @FXML
    private Label txtSignUp;

    @FXML
    private Pane yellowPane;

    @FXML
    void txtSignInOnMouseClicked(MouseEvent event) throws IOException {
        loginPane.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
        loginPane.getChildren().add(anchorPane);

    }

    @FXML
    void txtSignUpOnMouseClicked(MouseEvent event) throws IOException {
        loginPane.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignUpForm.fxml"));
        loginPane.getChildren().add(anchorPane);
    }

}
