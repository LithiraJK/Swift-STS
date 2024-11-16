package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentEnrollModel {

    public static List<String> getAllStudentIds() throws SQLException {
        List<String> studentIds = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT StudentId FROM Student");
        while (resultSet.next()) {
            studentIds.add(resultSet.getString(1));
        }
        return studentIds;
    }

    public static List<String> getAllRoutes() throws SQLException {
        List<String> routes = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT RouteName FROM Route");
        while (resultSet.next()) {
            routes.add(resultSet.getString("RouteName"));
        }
        return routes;
    }

    public static List<String> getAllDestinations() throws SQLException {
        List<String> destinations = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT Destination FROM Route");
        while (resultSet.next()) {
            destinations.add(resultSet.getString("Destination"));
        }
        return destinations;
    }

    public static String getStudentNameById(String studentId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT StudentName FROM Student WHERE StudentId = ?", studentId);
        if (resultSet.next()) {
            return resultSet.getString("StudentName");
        }
        return null;
    }

    public static String getPickupLocationById(String studentId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT PickupLocation FROM Student WHERE StudentId = ?", studentId);
        if (resultSet.next()) {
            return resultSet.getString("PickupLocation");
        }
        return null;
    }

    public static List<String> getAllVehicleIds() throws SQLException {
        List<String> vehicleIds = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT VehicleId FROM Vehicle");
        while (resultSet.next()) {
            vehicleIds.add(resultSet.getString("VehicleId"));
        }
        return vehicleIds;
    }

    public static int getAvailableSeatCountByVehicleId(String vehicleId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT AvailableSeatCount FROM Vehicle WHERE VehicleId = ?", vehicleId);
        if (resultSet.next()) {
            return resultSet.getInt("AvailableSeatCount");
        }
        return 0;
    }

    public String getNextRegistrationId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT StudentRegistrationId FROM StudentRegistrationDetails ORDER BY StudentRegistrationId DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(2); // Remove "SR" prefix
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("SR%03d", newIdIndex);
        }
        return "SR001";
    }
}