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

    public ObservableList<StudentRegistrationDetailsTM> getAllStudentRegistrationDetails() {
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

    public boolean insertStudentRegistration(String studentRegId, String studentId, double distance, double dayPrice, String registrationDate, String routeId, String vehicleId) throws SQLException {
        return CrudUtil.execute("INSERT INTO StudentRegistration (StudentRegistrationId, StudentId, Distance, DayPrice, Date, RouteId, VehicleId) VALUES (?, ?, ?, ?, ?, ?, ?)",
                studentRegId, studentId, distance, dayPrice, registrationDate, routeId, vehicleId);
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

    public ArrayList<String> getStudentIdsByVehicleId(String vehicleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT StudentId FROM StudentRegistration WHERE VehicleId = ?", vehicleId);
        ArrayList<String> studentIds = new ArrayList<>();
        while (rst.next()) {
            studentIds.add(rst.getString(1));
        }
        return studentIds;
    }


}
