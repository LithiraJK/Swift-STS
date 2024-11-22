package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.swiftsts.dto.UserDto;
import lk.ijse.gdse72.swiftsts.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpSecondFormController {

    @FXML
    private Label txtSignIn;

    @FXML
    private Label txtSignUp;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXPasswordField txtPassWord;

    @FXML
    private JFXTextField txtRole;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private AnchorPane paneSignUpSecond;

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String password = txtPassWord.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String role = txtRole.getText();
        String email = txtEmail.getText();

        if (userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match");
            return;
        }

        try {
            UserModel userModel = new UserModel();
            String userId = userModel.generateNextUserId();
            UserDto userDto = new UserDto(userId, userName, password, role, email);

            boolean isUserAdded = userModel.saveUser(userDto);

            if (isUserAdded) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User created successfully");
                paneSignUpSecond.getChildren().clear();
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SignInForm.fxml"));
                paneSignUpSecond.getChildren().add(anchorPane);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create user");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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