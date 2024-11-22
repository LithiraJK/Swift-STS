module lk.ijse.gdse72.swiftsts {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires static lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;
    requires jakarta.mail;

    opens lk.ijse.gdse72.swiftsts.dto to javafx.base;
    opens lk.ijse.gdse72.swiftsts.dto.tm to javafx.base;
    opens lk.ijse.gdse72.swiftsts.controller to javafx.fxml;
    exports lk.ijse.gdse72.swiftsts;
}