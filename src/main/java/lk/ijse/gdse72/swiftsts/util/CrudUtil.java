// src/main/java/lk/ijse/gdse72/swiftsts/util/CrudUtil.java
package lk.ijse.gdse72.swiftsts.util;

import lk.ijse.gdse72.swiftsts.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String sql, Object... obj) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            pst.setObject((i + 1), obj[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;
        } else {
            int i = pst.executeUpdate();
            boolean isSaved = i > 0;
            return (T) ((Boolean) isSaved);
        }
    }

    public static void startTransaction() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

    }

    public static void commitTransaction() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.commit();
        connection.setAutoCommit(true);
    }

    public static void rollbackTransaction() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.rollback();
        connection.setAutoCommit(true);
    }
}