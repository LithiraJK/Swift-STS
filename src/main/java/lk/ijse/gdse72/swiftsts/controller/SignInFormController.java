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
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        if (validateCredentials(username, password)) {
            loadDashboard("/view/DashBoardForm.fxml");
        } else {
            showError("Invalid username or password");
        }
    }

    private boolean validateCredentials(String username, String password) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM User WHERE username=? AND password=?", username, password);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loadDashboard(String fxmlPath) throws IOException {
        Window window = signInPage.getScene().getWindow();
        window.hide();
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setTitle("Swift Student Transfort Service");
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
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