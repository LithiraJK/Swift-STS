package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse72.swiftsts.dto.StudentDto;
import lk.ijse.gdse72.swiftsts.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewStudentFormController implements Initializable {
    @FXML
    public AnchorPane paneRegister;
    @FXML
    public JFXButton btnSave;
    @FXML
    public Label lblStudentId;
    @FXML
    public JFXTextField txtStudentGrade;
    @FXML
    public JFXTextField txtPhoneNo;
    @FXML
    public JFXTextField txtStudentName;
    @FXML
    public JFXTextField txtParentName;
    @FXML
    public JFXComboBox<String> cbUserID;
    @FXML
    public JFXButton btnDiscard;
    @FXML
    public JFXTextField txtAddress;
    @FXML
    public JFXTextField txtEmail;
    @FXML
    private AnchorPane overlayPane;
    @FXML
    private AnchorPane paneStudent;

    private final StudentModel studentModel = new StudentModel();

    public void setOverlayPane(AnchorPane overlayPane, AnchorPane paneStudent) {
        this.overlayPane = overlayPane;
        this.paneStudent = paneStudent;
    }

    @FXML
    public void btnSaveOnAction(ActionEvent actionEvent) {
        String studentId = lblStudentId.getText();
        String studentName = txtStudentName.getText();
        String parentName = txtParentName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String studentGrade = txtStudentGrade.getText();
        String phoneNo = txtPhoneNo.getText();
        String userId = cbUserID.getValue();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)$";
        String gradePattern = "^[a-zA-Z0-9]+$";

        boolean isValidName = studentName.matches(namePattern);
        boolean isValidParentName = parentName.matches(namePattern);
        boolean isValidAddress = address.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNo = phoneNo.matches(phonePattern);
        boolean isValidGrade = studentGrade.matches(gradePattern);

        txtStudentName.setFocusColor(Paint.valueOf("black"));
        txtParentName.setFocusColor(Paint.valueOf("black"));
        txtAddress.setFocusColor(Paint.valueOf("black"));
        txtEmail.setFocusColor(Paint.valueOf("black"));
        txtPhoneNo.setFocusColor(Paint.valueOf("black"));
        txtStudentGrade.setFocusColor(Paint.valueOf("black"));

        if (!isValidName) {
            txtStudentName.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidParentName) {
            txtParentName.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidAddress) {
            txtAddress.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidEmail) {
            txtEmail.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidPhoneNo) {
            txtPhoneNo.setFocusColor(Paint.valueOf("red"));
        }
        if (!isValidGrade) {
            txtStudentGrade.setFocusColor(Paint.valueOf("red"));
        }

        if (isValidName && isValidParentName && isValidAddress && isValidEmail && isValidPhoneNo && isValidGrade) {
            StudentDto studentDto = new StudentDto(studentId, studentName, parentName, address, email, studentGrade, phoneNo, userId);

            try {
                boolean isSaved = studentModel.saveStudent(studentDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Student saved successfully!").show();
                    paneStudent.getChildren().remove(overlayPane);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save student!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while saving the student: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    public void btnDiscardOnAction(ActionEvent actionEvent) {
        paneStudent.getChildren().remove(overlayPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String nextStudentId = studentModel.getNextStudentId();
            lblStudentId.setText(nextStudentId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while generating the student ID: " + e.getMessage()).show();
        }

        try {
            cbUserID.getItems().addAll(studentModel.getAllUserIds());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred while loading user IDs: " + e.getMessage()).show();
        }

        addValidationListeners();
    }

    private void addValidationListeners() {
        // Define regex patterns
        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)$";
        String gradePattern = "^[a-zA-Z0-9]+$";

        // Add listener for each field
        txtStudentName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(namePattern)) {
                txtStudentName.setFocusColor(Paint.valueOf("red"));
            } else {
                txtStudentName.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtParentName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(namePattern)) {
                txtParentName.setFocusColor(Paint.valueOf("red"));
            } else {
                txtParentName.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtAddress.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(namePattern)) {
                txtAddress.setFocusColor(Paint.valueOf("red"));
            } else {
                txtAddress.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(emailPattern)) {
                txtEmail.setFocusColor(Paint.valueOf("red"));
            } else {
                txtEmail.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtPhoneNo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(phonePattern)) {
                txtPhoneNo.setFocusColor(Paint.valueOf("red"));
            } else {
                txtPhoneNo.setFocusColor(Paint.valueOf("black"));
            }
        });

        txtStudentGrade.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(gradePattern)) {
                txtStudentGrade.setFocusColor(Paint.valueOf("red"));
            } else {
                txtStudentGrade.setFocusColor(Paint.valueOf("black"));
            }
        });
    }
}