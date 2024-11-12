package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.AttendanceDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceModel {

    public boolean saveAttendance(AttendanceDto dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Attendance (AttendanceId, StudentId, DriverId, Year, Month, DayCount) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getAttendanceId(),
                dto.getStudentId(),
                dto.getDriverId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount()
        );
    }

    public ArrayList<AttendanceDto> getAllAttendances() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Attendance");
        ArrayList<AttendanceDto> attendenceList = new ArrayList<>();

        while (rst.next()) {
            AttendanceDto dto = new AttendanceDto(
                    rst.getString("AttendanceId"),
                    rst.getString("StudentId"),
                    rst.getString("DriverId"),
                    rst.getInt("Year"),
                    rst.getString("Month"),
                    rst.getInt("DayCount")
            );
            attendenceList.add(dto);
        }

        return attendenceList;
    }

    public boolean updateAttendence(AttendanceDto dto) throws SQLException {
        return CrudUtil.execute("UPDATE Attendance SET StudentId=?, DriverId=?, Year=?, Month=?, DayCount=? WHERE AttendanceId=?",
                dto.getStudentId(),
                dto.getDriverId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount(),
                dto.getAttendanceId()
        );
    }

    public boolean deleteAttendence(String attendenceId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Attendance WHERE AttendanceId=?", attendenceId);
    }

    public int getMonthlyDayCount(Connection connection, String studentId, String year, String month) throws SQLException {
        String query = "SELECT DayCount FROM Attendance WHERE StudentId = ? AND Year = ? AND Month = ?";
        ResultSet resultSet = CrudUtil.execute(query, studentId, year, month);

        int dayCount = 0;
        if (resultSet.next()) {
            dayCount = resultSet.getInt("DayCount");
        }
        return dayCount;
    }

}