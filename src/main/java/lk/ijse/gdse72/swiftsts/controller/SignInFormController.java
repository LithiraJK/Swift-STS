package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SignInFormController {

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private Label lblSignUp;

    @FXML
    private AnchorPane signInPage;

    @FXML
    private JFXPasswordField txtpassword;

    @FXML
    private JFXTextField txtusername;


    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        Window window = signInPage.getScene().getWindow();
        window.hide();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

}
