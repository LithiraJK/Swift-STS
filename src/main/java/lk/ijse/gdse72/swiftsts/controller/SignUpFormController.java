package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse72.swiftsts.util.SendMailUtil;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {
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
    private Label lblAlert;

    @FXML
    private JFXButton btnNext;

    @FXML
    private JFXButton btnSendOTP;

    @FXML
    private AnchorPane paneSignUp;

    private String generatedOTP;

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        String inputOTP = txtEnterOTP.getText();

        if (inputOTP.equals(generatedOTP)) {
//        if(true){
            paneSignUp.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/SignUpSecondForm.fxml"));
            paneSignUp.getChildren().add(pane);
        } else {
            lblAlert.setText("Invalid OTP code. Try Again!");
        }
    }

    @FXML
    void btnSendOTPOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String email = txtEmail.getText();

        if (username.isEmpty() || email.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter both username and email.").show();
            return;
        }

        if (!isValidEmail(email)) {
            txtEmail.setFocusColor(Paint.valueOf("red"));
            new Alert(Alert.AlertType.ERROR, "Invalid email format.").show();
            return;
        }

        generatedOTP = generateOTP();
        String subject = "Your OTP Code";
        String messageBody = "Hello " + username + ",\n\nYour OTP code is: " + generatedOTP + "\n\nThank you.";

        try {
            SendMailUtil.sendEmail(email, subject, messageBody);
            new Alert(Alert.AlertType.INFORMATION, "OTP sent successfully!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to send OTP: " + e.getMessage()).show();
        }
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isValidEmail(newValue)) {
                    txtEmail.setFocusColor(Paint.valueOf("#ffd600"));
                } else {
                    txtEmail.setFocusColor(Paint.valueOf("red"));
                }
            }
        });
    }
}