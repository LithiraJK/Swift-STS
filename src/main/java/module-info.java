module lk.ijse.gdse72.swiftsts {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens lk.ijse.gdse72.swiftsts.controller to javafx.fxml;
    exports lk.ijse.gdse72.swiftsts;
}