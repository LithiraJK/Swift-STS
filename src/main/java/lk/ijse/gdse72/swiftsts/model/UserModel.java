package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.UserDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public boolean saveUser(final UserDto dto) throws SQLException {
        return CrudUtil.execute("INSERT into User(UserId,UserName,Password,RoleType,Email,Name) VALUES (?,?,?,?,?,?)",
                dto.getId(),
                dto.getUsername(),
                dto.getPassword(),
                dto.getRole(),
                dto.getEmail(),
                dto.getName()
        );

    }

    public String getUserRole(String username, String password) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT RoleType FROM User WHERE UserName=? AND Password=?", username, password);
        if (resultSet.next()) {
            return resultSet.getString("role");
        }
        return null;
    }

    public String getUserId(String username,String password) throws SQLException {
        System.out.println("Ia am come");
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM User WHERE Username=? AND Password=?", username,password);
        if (resultSet.next())return resultSet.getString(1);
        return null;
    }

    public String getUserName(String uname, String pword) throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT Name FROM User WHERE Username=? AND Password=?", uname,pword);
        if (resultSet.next())return resultSet.getString(1);
        return null;
    }

    public String generateNextUserId() throws SQLException {

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM User ORDER BY UserId DESC LIMIT 1");
        String current = null;

        if (resultSet.next()) {
            current = resultSet.getString(1);
            return splitId(current);
        }
        return splitId(null);

    }

    private String splitId(String current) {
        if (current != null) {
            String[] split = current.split("U");
            int id = Integer.parseInt(split[1]);
            id++;
            if (9 >= id && id > 0) return "U00" + id;
            else if (99 >= id && id > 9) return "U0" + id;
            else if (id > 99) return "U" + id;
        }
        return "U001";
    }
}