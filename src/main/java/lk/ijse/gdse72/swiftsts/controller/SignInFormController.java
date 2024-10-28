package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SignInFormController {

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private Label lblSignUp;

    @FXML
    private Label txtSignIn;

    @FXML
    private Label txtSignUp;

    @FXML
    private AnchorPane signInPage;

    @FXML
    private JFXPasswordField txtpassword;

    @FXML
    private JFXTextField txtusername;

    @FXML
    void lblSignUpOnClicked(MouseEvent event) throws IOException {
        signInPage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignUpForm.fxml"));
        signInPage.getChildren().add(anchorPane);
    }


    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        String username = txtusername.getText();
        String password = txtpassword.getText();

        // Validate credentials (this example assumes a method `validateCredentials` exists)
        if (validateCredentials(username, password)) {
            Window window = signInPage.getScene().getWindow();
            window.hide();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashBoardForm.fxml"));
            Stage stage = new Stage();
            stage.setMaximized(true);
            stage.setTitle("swift STS");
            stage.setScene(new Scene(anchorPane));
            stage.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password!").show();
        }
    }

    // Example method to validate credentials
    private boolean validateCredentials(String username, String password) {
        // Replace with actual validation logic, e.g., database query
        return "admin".equals(username) && "1234".equals(password);
    }

    public void txtSignInOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        signInPage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
        signInPage.getChildren().add(anchorPane);
    }

    public void txtSignUpOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        signInPage.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignUpForm.fxml"));
        signInPage.getChildren().add(anchorPane);
    }
}
