package lk.ijse.gdse72.swiftsts.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable{

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

    public void initialize(URL location, ResourceBundle resources) {
        // Load the SignInForm.fxml by default when the login form is initialized
        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
            loginPane.getChildren().clear();  // Clear current content
            loginPane.getChildren().add(anchorPane);  // Add the loaded SignIn form
        } catch (IOException e) {
            e.printStackTrace();  // Print any loading errors to the console
        }
    }


}
