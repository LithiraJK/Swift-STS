package lk.ijse.gdse72.swiftsts.db;


import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;

    @Getter
    private final Connection connection; //package sql athule tyena Connection interface eka

    private DBConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost:3306/SwiftSTS";
        String USER = "root";
        String PASSWORD = "mysql";
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    // Lombok usage
//    public Connection getConnection(){
//        return connection;
//    }

}