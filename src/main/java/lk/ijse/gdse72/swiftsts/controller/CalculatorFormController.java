package lk.ijse.gdse72.swiftsts.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CalculatorFormController {

    @FXML
    private TextField textField;

    @FXML
    private Text savedNumbers;

    private double currentResult = 0;  // To store the cumulative result
    private String currentNumber = "";
    private String lastOperation = ""; // To store the last operation

    @FXML
    void addAction(ActionEvent event) {
        performCalculation();
        lastOperation = "+";
        savedNumbers.setText(currentResult + " + ");
    }

    @FXML
    void minusAction(ActionEvent event) {
        performCalculation();
        lastOperation = "-";
        savedNumbers.setText(currentResult + " - ");
    }

    @FXML
    void divideAction(ActionEvent event) {
        performCalculation();
        lastOperation = "/";
        savedNumbers.setText(currentResult + " / ");
    }

    @FXML
    void multiplicationAction(ActionEvent event) {
        performCalculation();
        lastOperation = "*";
        savedNumbers.setText(currentResult + " * ");
    }

    @FXML
    void calculate(ActionEvent event) {
        performCalculation();
        savedNumbers.setText(String.valueOf(currentResult)); // Display the final result
        lastOperation = ""; // Reset the operation after the calculation
    }

    private void performCalculation() {
        if (!currentNumber.isEmpty()) {
            double number = Double.parseDouble(currentNumber);
            switch (lastOperation) {
                case "+" -> currentResult += number;
                case "-" -> currentResult -= number;
                case "/" -> currentResult /= number;
                case "*" -> currentResult *= number;
                case "" -> currentResult = number; // First entry case
            }
            textField.setText(String.valueOf(currentResult));
            currentNumber = ""; // Clear the current number after operation
        }
    }

    @FXML
    void clearTextField(ActionEvent event) {
        currentNumber = "";
        currentResult = 0;
        lastOperation = "";
        textField.setText("");
        savedNumbers.setText("");
    }

    // Number buttons
    @FXML
    void button0Clicked(ActionEvent event) {
        addNumber("0");
    }

    @FXML
    void button1Clicked(ActionEvent event) {
        addNumber("1");
    }

    @FXML
    void button2Clicked(ActionEvent event) {
        addNumber("2");
    }

    @FXML
    void button3Clicked(ActionEvent event) {
        addNumber("3");
    }

    @FXML
    void button4Clicked(ActionEvent event) {
        addNumber("4");
    }

    @FXML
    void button5Clicked(ActionEvent event) {
        addNumber("5");
    }

    @FXML
    void button6Clicked(ActionEvent event) {
        addNumber("6");
    }

    @FXML
    void button7Clicked(ActionEvent event) {
        addNumber("7");
    }

    @FXML
    void button8Clicked(ActionEvent event) {
        addNumber("8");
    }

    @FXML
    void button9Clicked(ActionEvent event) {
        addNumber("9");
    }

    public void updateTextField() {
        textField.setText(currentNumber);
    }

    public void addNumber(String number) {
        currentNumber += number;
        updateTextField();
    }
}
