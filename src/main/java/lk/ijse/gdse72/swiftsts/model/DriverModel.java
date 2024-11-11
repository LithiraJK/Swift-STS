package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.DriverDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverModel {

    public boolean saveDriver(DriverDto dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Driver (DriverId, Name, LicenseNo, NIC, ContactNo, Address, Email) VALUES (?, ?, ?, ?, ?, ?, ?)",
                dto.getDriverId(),
                dto.getName(),
                dto.getLicenseNo(),
                dto.getNic(),
                dto.getContactNo(),
                dto.getAddress(),
                dto.getEmail()
        );
    }

    public ArrayList<DriverDto> getAllDrivers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Driver");
        ArrayList<DriverDto> driverList = new ArrayList<>();

        while (rst.next()) {
            DriverDto dto = new DriverDto(
                    rst.getString("DriverId"),
                    rst.getString("Name"),
                    rst.getString("LicenseNo"),
                    rst.getString("NIC"),
                    rst.getString("ContactNo"),
                    rst.getString("Address"),
                    rst.getString("Email")
            );
            driverList.add(dto);
        }

        return driverList;
    }

    public boolean updateDriver(DriverDto dto) throws SQLException {
        return CrudUtil.execute("UPDATE Driver SET Name=?, LicenseNo=?, NIC=?, ContactNo=?, Address=?, Email=? WHERE DriverId=?",
                dto.getName(),
                dto.getLicenseNo(),
                dto.getNic(),
                dto.getContactNo(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getDriverId()
        );
    }

    public String getNextDriverId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT DriverId FROM Driver ORDER BY DriverId DESC LIMIT 1");

        if (rst.next()){
            String lastiD = rst.getString(1);
            String substring = lastiD.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);

        }
        return "D001";
    }

    public boolean deleteDriver(String driverId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Driver WHERE DriverId=?", driverId);
    }
}