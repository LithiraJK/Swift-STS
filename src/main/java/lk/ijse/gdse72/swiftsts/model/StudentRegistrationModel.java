package lk.ijse.gdse72.swiftsts.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse72.swiftsts.db.DBConnection;
import lk.ijse.gdse72.swiftsts.dto.StudentRegistrationDto;
import lk.ijse.gdse72.swiftsts.dto.tm.StudentRegistrationDetailsTM;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRegistrationModel {

    public static ObservableList<StudentRegistrationDetailsTM> getAllStudentRegistrationDetails() {
        ObservableList<StudentRegistrationDetailsTM> list = FXCollections.observableArrayList();
        String query = "SELECT " +
                "sr.StudentRegistrationId AS 'Registration ID', " +
                "s.StudentId AS 'Student ID', " +
                "s.StudentName AS 'Student Name', " +
                "s.PickupLocation AS 'Pickup Location', " +
                "r.Destination AS 'Destination', " +
                "sr.Distance AS 'Distance', " +
                "sr.DayPrice AS 'Day Price', " +
                "sr.RouteId AS 'Route ID', " +
                "sr.VehicleId AS 'Vehicle ID', " +
                "sr.Date AS 'Registration Date' " +
                "FROM StudentRegistration sr " +
                "JOIN Student s ON sr.StudentId = s.StudentId " +
                "JOIN Route r ON sr.RouteId = r.RouteId " +
                "JOIN Vehicle v ON sr.VehicleId = v.VehicleId";

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new StudentRegistrationDetailsTM(
                        rs.getString("Registration ID"),
                        rs.getString("Student ID"),
                        rs.getString("Student Name"),
                        rs.getString("Pickup Location"),
                        rs.getString("Destination"),
                        rs.getDouble("Distance"),
                        rs.getDouble("Day Price"),
                        rs.getString("Route ID"),
                        rs.getString("Vehicle ID"),
                        rs.getDate("Registration Date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static double getRouteFeeByRouteId(String routeId) throws SQLException {
        String query = "SELECT RouteFee FROM Route WHERE RouteId = ?";
        ResultSet rs = CrudUtil.execute(query, routeId);

        if (rs.next()) {
            return rs.getDouble("RouteFee");
        } else {
            throw new SQLException("Route ID not found in Route table");
        }
    }

    public ArrayList<StudentRegistrationDto> getAllStudentRegistrations() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT StudentRegistrationId, " +
                "StudentId, Distance,DayPrice,RouteName,VehicleId,Date " +
                "FROM StudentRegistration sr " +
                "join Route r " +
                "on sr.RouteId = r.RouteId");
        ArrayList<StudentRegistrationDto> studentRegistrationList = new ArrayList<>();

        while (rst.next()) {
            StudentRegistrationDto dto = new StudentRegistrationDto(
                    rst.getString("StudentRegistrationId"),
                    rst.getString("StudentId"),
                    rst.getDouble("DayPrice"),
                    rst.getDouble("Distance"),
                    rst.getString("RouteName"),
                    rst.getString("VehicleId"),
                    rst.getDate("Date")

            );
            studentRegistrationList.add(dto);
        }

        return studentRegistrationList;
    }

    public static boolean insertStudentRegistration(String studentRegId, String studentId, double distance, double dayPrice, String registrationDate, String routeId, String vehicleId) throws SQLException {
        return CrudUtil.execute("INSERT INTO StudentRegistration (StudentRegistrationId, StudentId, Distance, DayPrice, Date, RouteId, VehicleId) VALUES (?, ?, ?, ?, ?, ?, ?)",
                studentRegId, studentId, distance, dayPrice, registrationDate, routeId, vehicleId);
    }

    public static boolean updateVehicleSeatCount(String vehicleId, int decrementBy) throws SQLException {
        return CrudUtil.execute("UPDATE Vehicle SET AvailableSeatCount = AvailableSeatCount - ? WHERE VehicleId = ?", decrementBy, vehicleId);
    }

    public static String getRouteIdByRouteName(String routeName) throws SQLException {
        String query = "SELECT RouteId FROM Route WHERE RouteName = ?";
        ResultSet rs = CrudUtil.execute(query, routeName);

        if (rs.next()) {
            return rs.getString("RouteId");
        } else {
            throw new SQLException("Route Name not found in Route table");
        }
    }

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
        ResultSet resultSet = CrudUtil.execute("SELECT StudentRegistrationId FROM StudentRegistration ORDER BY StudentRegistrationId DESC LIMIT 1");

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
