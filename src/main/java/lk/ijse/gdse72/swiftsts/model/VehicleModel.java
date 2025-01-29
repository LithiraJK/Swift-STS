package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.VehicleDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleModel {

    public ArrayList<VehicleDto> getAllVehicles() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Vehicle");
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();

        while (rst.next()) {
            VehicleDto vehicleDto = new VehicleDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7),
                    rst.getInt(8)
            );
            vehicleDtos.add(vehicleDto);
        }

        return vehicleDtos;
    }

    public String getNextVehicleId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT vehicleId FROM Vehicle ORDER BY vehicleId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("V%03d", newIdIndex);
        }
        return "V001";
    }

    public boolean saveVehicle(VehicleDto vehicleDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Vehicle VALUES (?,?,?,?,?,?,?,?)",
                vehicleDto.getVehicleId(),
                vehicleDto.getRegistrationNo(),
                vehicleDto.getVehicleType(),
                vehicleDto.getEngineCapacity(),
                vehicleDto.getFuelType(),
                vehicleDto.getModel(),
                vehicleDto.getSeatCount(),
                vehicleDto.getAvailableSeatCount()
        );
    }



    public boolean updateVehicle(VehicleDto vehicleDto) throws SQLException {
        return CrudUtil.execute("UPDATE Vehicle SET registrationNo=?, vehicleType=?, engineCapacity=?, fuelType=?, model=?, seatCount=?, availableSeatCount=? WHERE vehicleId=?",
                vehicleDto.getRegistrationNo(),
                vehicleDto.getVehicleType(),
                vehicleDto.getEngineCapacity(),
                vehicleDto.getFuelType(),
                vehicleDto.getModel(),
                vehicleDto.getSeatCount(),
                vehicleDto.getAvailableSeatCount(),
                vehicleDto.getVehicleId()
        );
    }

    public boolean deleteVehicle(String vehicleId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Vehicle WHERE vehicleId=?", vehicleId);
    }

    public boolean updateVehicleSeatCount(String vehicleId, int decrementBy) throws SQLException {
        return CrudUtil.execute("UPDATE Vehicle SET AvailableSeatCount = AvailableSeatCount - ? WHERE VehicleId = ?", decrementBy, vehicleId);
    }
    public List<String> getAllVehicleIds() throws SQLException {
        List<String> vehicleIds = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT VehicleId FROM Vehicle");
        while (resultSet.next()) {
            vehicleIds.add(resultSet.getString("VehicleId"));
        }
        return vehicleIds;
    }

    public int getAvailableSeatCountByVehicleId(String vehicleId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT AvailableSeatCount FROM Vehicle WHERE VehicleId = ?", vehicleId);
        if (resultSet.next()) {
            return resultSet.getInt("AvailableSeatCount");
        }
        return 0;
    }

    public String getRegistrationNoById(String vehicleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT RegistrationNo FROM Vehicle WHERE VehicleId=?", vehicleId);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
    public int getVehicleCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Vehicle";
        ResultSet resultSet = CrudUtil.execute(query);
        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}