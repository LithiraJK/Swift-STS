module lk.ijse.gdse72.swiftsts {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires static lombok;
    requires java.sql;
    requires net.sf.jasperreports.core;
//    requires com.google.api.client.json.gson;
//    requires com.google.api.client;
//    requires com.google.api.services.gmail;
//    requires google.api.client;
//    requires com.google.api.client.auth;
//    requires org.apache.commons.codec;
//    requires com.google.api.client.extensions.java6.auth;
//    requires java.mail;
//    requires com.google.api.client.extensions.jetty.auth;

    opens lk.ijse.gdse72.swiftsts.dto to javafx.base;
    opens lk.ijse.gdse72.swiftsts.dto.tm to javafx.base;
    opens lk.ijse.gdse72.swiftsts.controller to javafx.fxml;
    exports lk.ijse.gdse72.swiftsts;
}