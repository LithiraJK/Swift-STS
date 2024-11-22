package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.UserDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public boolean saveUser(final UserDto dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO User(UserId, UserName, Password, RoleType, Email) VALUES (?, ?, ?, ?, ?)",
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getRole(),
                dto.getEmail()
        );
    }

    public String getUserRole(String username, String password) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT RoleType FROM User WHERE UserName=? AND Password=?", username, password);
        if (resultSet.next()) {
            return resultSet.getString("RoleType");
        }
        return null;
    }

    public String getUserId(String username, String password) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT UserId FROM User WHERE UserName=? AND Password=?", username, password);
        if (resultSet.next()) {
            return resultSet.getString("UserId");
        }
        return null;
    }


    public String generateNextUserId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT UserId FROM User ORDER BY UserId DESC LIMIT 1");
        if (resultSet.next()) {
            String currentId = resultSet.getString("UserId");
            return splitId(currentId);
        }
        return splitId(null);
    }

    private String splitId(String currentId) {
        if (currentId != null) {
            int id = Integer.parseInt(currentId.substring(1)) + 1;
            return String.format("U%03d", id);
        }
        return "U001";
    }
}