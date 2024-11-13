package lk.ijse.gdse72.swiftsts.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.gdse72.swiftsts.dto.StudentDto;
import lk.ijse.gdse72.swiftsts.dto.tm.StudentTM;
import lk.ijse.gdse72.swiftsts.model.StudentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentFormController implements Initializable {
    @FXML
    public JFXButton btnRegister;
    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblStudentId;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtParentName;

    @FXML
    private JFXTextField txtPhoneNo;

    @FXML
    private JFXTextField txtStudentGrade;

    @FXML
    private JFXTextField txtStudentName;

    @FXML
    private TableColumn<StudentTM,String> colAddress;

    @FXML
    private TableColumn<StudentTM, String> colEmail;

    @FXML
    private TableColumn<StudentTM,String> colGrade;

    @FXML
    private TableColumn<StudentTM,String> colParentName;

    @FXML
    private TableColumn<StudentTM,String> colPhoneNo;

    @FXML
    private TableColumn<StudentTM,String> colStudentId;

    @FXML
    private TableColumn<StudentTM,String> colStudentName;

    @FXML
    private TableView<StudentTM> tblStudent;

    @FXML
    private TableColumn<StudentTM,String> colUserID;

    @FXML
    private AnchorPane paneStudent;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXComboBox<String> cbUserID;

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
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
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save student!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while saving the student: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException {
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
            boolean isUpdated = studentModel.updateStudent(studentDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Student updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update student!").show();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) {
        String studentId = lblStudentId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this student?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES){
            try {
                boolean isDeleted = studentModel.deleteStudent(studentId);
                if (isDeleted){
                    new Alert(Alert.AlertType.INFORMATION, "Student deleted successfully!").show();
                    refreshPage();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Failed to delete student!").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent mouseEvent) {
        StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            lblStudentId.setText(selectedItem.getStudentId());
            txtStudentName.setText(selectedItem.getStudentName());
            txtParentName.setText(selectedItem.getParentName());
            txtAddress.setText(selectedItem.getAddress());
            txtEmail.setText(selectedItem.getEmail());
            txtStudentGrade.setText(selectedItem.getStudentGrade());
            txtPhoneNo.setText(selectedItem.getPhoneNo());
            cbUserID.setValue(selectedItem.getUserId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    StudentModel studentModel = new StudentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colParentName.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("studentGrade"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            refreshPage();
            loadUserIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        addValidationListeners();
    }

    private void loadUserIds() throws SQLException {
        ArrayList<String> userIds = studentModel.getAllUserIds();
        cbUserID.setItems(FXCollections.observableArrayList(userIds));
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextStudentId = studentModel.getNextStudentId();
        lblStudentId.setText(nextStudentId);

        txtStudentName.setText("");
        txtParentName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtStudentGrade.setText("");
        txtPhoneNo.setText("");
        cbUserID.setValue(null);

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<StudentDto> studentDtos = studentModel.getAllStudents();
        ObservableList<StudentTM> studentTMS = FXCollections.observableArrayList();

        for(StudentDto studentDto : studentDtos){
            StudentTM studentTM = new StudentTM(
                    studentDto.getStudentId(),
                    studentDto.getStudentName(),
                    studentDto.getParentName(),
                    studentDto.getAddress(),
                    studentDto.getEmail(),
                    studentDto.getStudentGrade(),
                    studentDto.getPhoneNo(),
                    studentDto.getUserId()
            );
            studentTMS.add(studentTM);
        }
        txtStudentName.setFocusColor(Paint.valueOf("black"));
        txtParentName.setFocusColor(Paint.valueOf("black"));
        txtAddress.setFocusColor(Paint.valueOf("black"));
        txtEmail.setFocusColor(Paint.valueOf("black"));
        txtPhoneNo.setFocusColor(Paint.valueOf("black"));
        txtStudentGrade.setFocusColor(Paint.valueOf("black"));
        tblStudent.setItems(studentTMS);
    }

    @FXML
    public void btnRegisterOnAction(ActionEvent actionEvent) throws IOException {
        // Load the StudentRegisterForm.fxml as the popup content
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StudentRegisterForm.fxml"));
        AnchorPane anchorPane = loader.load();

        // Create a popup overlay (if not already part of the FXML file)
        AnchorPane overlayPane = new AnchorPane();
        overlayPane.setStyle("-fx-background-color: rgba(255,255,255, 0.5);");
        overlayPane.setPrefSize(paneStudent.getWidth(), paneStudent.getHeight());

        // Center the anchorPane in the overlayPane (this will be your popup form)
        anchorPane.setLayoutX((overlayPane.getPrefWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((overlayPane.getPrefHeight() - anchorPane.getPrefHeight()) / 2);

        // Add the form to the overlay
        overlayPane.getChildren().add(anchorPane);

        // Add the overlayPane to paneStudent and make it visible
        paneStudent.getChildren().add(overlayPane);

        // Get the controller for StudentRegisterForm
        StudentRegisterFormController controller = loader.getController();

        // Pass the overlayPane reference to allow the controller to close it
        controller.setOverlayPane(overlayPane, paneStudent);
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