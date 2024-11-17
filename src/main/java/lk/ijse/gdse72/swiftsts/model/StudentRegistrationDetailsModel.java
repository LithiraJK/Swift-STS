package lk.ijse.gdse72.swiftsts.model;

import javafx.collections.FXCollections;
import lk.ijse.gdse72.swiftsts.dto.tm.StudentRegistrationDetailsTM;

//import javafx.collections.ObservableList;
//import lk.ijse.gdse72.swiftsts.db.DBConnection;
//import lk.ijse.gdse72.swiftsts.dto.StudentRegistrationDetailsDto;
//import lk.ijse.gdse72.swiftsts.util.CrudUtil;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
public class StudentRegistrationDetailsModel {

//    public static ObservableList<StudentRegistrationDetailsTM> getAllStudentRegistrations() throws SQLException {
//        ObservableList<StudentRegistrationDetailsTM> list = FXCollections.observableArrayList();
//        String query = "SELECT " +
//                "sr.StudentRegistrationId AS 'Registration ID', " +
//                "s.StudentId AS 'Student ID', " +
//                "s.StudentName AS 'Student Name', " +
//                "s.PickupLocation AS 'Pickup Location', " +
//                "r.Destination AS 'Destination', " +
//                "sr.Distance AS 'Distance', " +
//                "sr.DayPrice AS 'Day Price', " +
//                "sr.RouteId AS 'Route ID', " +
//                "sr.VehicleId AS 'Vehicle ID', " +
//                "sr.Date AS 'Registration Date' " +
//                "FROM StudentRegistration sr " +
//                "JOIN Student s ON sr.StudentId = s.StudentId " +
//                "JOIN Route r ON sr.RouteId = r.RouteId " +
//                "JOIN Vehicle v ON sr.VehicleId = v.VehicleId";
//
//        try (Connection connection = DBConnection.getInstance().getConnection();
//             PreparedStatement pst = connection.prepareStatement(query);
//             ResultSet rs = pst.executeQuery()) {
//
//            while (rs.next()) {
//                list.add(new StudentRegistrationDetailsTM(
//                        rs.getString("Registration ID"),
//                        rs.getString("Student ID"),
//                        rs.getString("Student Name"),
//                        rs.getString("Pickup Location"),
//                        rs.getString("Destination"),
//                        rs.getDouble("Distance"),
//                        rs.getDouble("Day Price"),
//                        rs.getString("Route ID"),
//                        rs.getString("Vehicle ID"),
//                        rs.getDate("Registration Date")
//                ));
//            }
//        }
//        return list;
//    }
//
//    public static boolean insertStudentRegistration(String studentRegId, String studentId, double distance, double dayPrice, String registrationDate, String routeId, String vehicleId) throws SQLException {
//        return CrudUtil.execute("INSERT INTO StudentRegistration (StudentRegistrationId, StudentId, Distance, DayPrice, Date, RouteId, VehicleId) VALUES (?, ?, ?, ?, ?, ?, ?)",
//                studentRegId, studentId, distance, dayPrice, registrationDate, routeId, vehicleId);
//    }
//
//    public static boolean updateVehicleSeatCount(String vehicleId, int decrementBy) throws SQLException {
//        return CrudUtil.execute("UPDATE Vehicle SET AvailableSeatCount = AvailableSeatCount - ? WHERE VehicleId = ?", decrementBy, vehicleId);
//    }
//
//    public static List<String> getAllStudentIds() throws SQLException {
//        List<String> studentIds = new ArrayList<>();
//        ResultSet resultSet = CrudUtil.execute("SELECT StudentId FROM Student");
//        while (resultSet.next()) {
//            studentIds.add(resultSet.getString(1));
//        }
//        return studentIds;
//    }
//
//    public static List<String> getAllRoutes() throws SQLException {
//        List<String> routes = new ArrayList<>();
//        ResultSet resultSet = CrudUtil.execute("SELECT RouteId FROM Route");
//        while (resultSet.next()) {
//            routes.add(resultSet.getString("RouteId"));
//        }
//        return routes;
//    }
//
//    public static List<String> getAllDestinations() throws SQLException {
//        List<String> destinations = new ArrayList<>();
//        ResultSet resultSet = CrudUtil.execute("SELECT Destination FROM Route");
//        while (resultSet.next()) {
//            destinations.add(resultSet.getString("Destination"));
//        }
//        return destinations;
//    }
//
//    public static String getStudentNameById(String studentId) throws SQLException {
//        ResultSet resultSet = CrudUtil.execute("SELECT StudentName FROM Student WHERE StudentId = ?", studentId);
//        if (resultSet.next()) {
//            return resultSet.getString("StudentName");
//        }
//        return null;
//    }
//
//    public static String getPickupLocationById(String studentId) throws SQLException {
//        ResultSet resultSet = CrudUtil.execute("SELECT PickupLocation FROM Student WHERE StudentId = ?", studentId);
//        if (resultSet.next()) {
//            return resultSet.getString("PickupLocation");
//        }
//        return null;
//    }
//
//    public static List<String> getAllVehicleIds() throws SQLException {
//        List<String> vehicleIds = new ArrayList<>();
//        ResultSet resultSet = CrudUtil.execute("SELECT VehicleId FROM Vehicle");
//        while (resultSet.next()) {
//            vehicleIds.add(resultSet.getString("VehicleId"));
//        }
//        return vehicleIds;
//    }
//
//    public static int getAvailableSeatCountByVehicleId(String vehicleId) throws SQLException {
//        ResultSet resultSet = CrudUtil.execute("SELECT AvailableSeatCount FROM Vehicle WHERE VehicleId = ?", vehicleId);
//        if (resultSet.next()) {
//            return resultSet.getInt("AvailableSeatCount");
//        }
//        return 0;
//    }
//
//    public String getNextRegistrationId() throws SQLException {
//        ResultSet resultSet = CrudUtil.execute("SELECT StudentRegistrationId FROM StudentRegistration ORDER BY StudentRegistrationId DESC LIMIT 1");
//
//        if (resultSet.next()) {
//            String lastId = resultSet.getString(1);
//            String substring = lastId.substring(2); // Remove "SR" prefix
//            int i = Integer.parseInt(substring);
//            int newIdIndex = i + 1;
//            return String.format("SR%03d", newIdIndex);
//        }
//        return "SR001";
//    }
}