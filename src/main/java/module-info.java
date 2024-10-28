module lk.ijse.gdse72.swiftsts {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires static lombok;
    requires java.sql;

    opens lk.ijse.gdse72.swiftsts.dto.tm to javafx.base;
    opens lk.ijse.gdse72.swiftsts.controller to javafx.fxml;
    exports lk.ijse.gdse72.swiftsts;
}