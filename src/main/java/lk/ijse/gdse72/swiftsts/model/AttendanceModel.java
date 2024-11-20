package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.AttendanceDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceModel {

    public boolean saveAttendance(AttendanceDto dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Attendance (AttendanceId, StudentId, DriverId, Year, Month, DayCount, MonthlyFee) VALUES (?, ?, ?, ?, ?, ?, ?)",
                dto.getAttendanceId(),
                dto.getStudentId(),
                dto.getDriverId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount(),
                dto.getMonthlyFee()
        );
    }

    public String getNextAttendanceId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT AttendanceId FROM Attendance ORDER BY AttendanceId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("A%03d", newIdIndex);
        }
        return "A001";
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
                    rst.getInt("DayCount"),
                    rst.getDouble("MonthlyFee")
            );
            attendenceList.add(dto);
        }

        return attendenceList;
    }


    public boolean updateAttendance(AttendanceDto dto) throws SQLException {
        return CrudUtil.execute("UPDATE Attendance SET StudentId=?, DriverId=?, Year=?, Month=?, DayCount=?, MonthlyFee=? WHERE AttendanceId=?",
                dto.getStudentId(),
                dto.getDriverId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount(),
                dto.getMonthlyFee(),
                dto.getAttendanceId()
        );
    }


    public boolean deleteAttendence(String attendenceId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Attendance WHERE AttendanceId=?", attendenceId);
    }

    public int getAttendanceDayCount(String studentId, String year, String month) throws SQLException {
        String query = "SELECT DayCount FROM Attendance WHERE StudentId = ? AND Year = ? AND Month = ?";
        ResultSet resultSet = CrudUtil.execute(query, studentId, year, month);

        int dayCount = 0;
        if (resultSet.next()) {
            dayCount = resultSet.getInt("DayCount");
        }
        return dayCount;
    }

    public String getStudentIdByAttendanceId(String attendanceId) throws SQLException {
        String query = "SELECT StudentId FROM Attendance WHERE AttendanceId = ?";
        ResultSet resultSet = CrudUtil.execute(query, attendanceId);

        if (resultSet.next()) {
            return resultSet.getString("StudentId");
        }
        return null;
    }
}