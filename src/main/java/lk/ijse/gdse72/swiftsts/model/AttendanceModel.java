package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.AttendanceDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceModel {

    public ArrayList<AttendanceDto> getAllAttendances() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Attendance");
        ArrayList<AttendanceDto> attendenceList = new ArrayList<>();

        while (rst.next()) {
            AttendanceDto dto = new AttendanceDto(
                    rst.getString("AttendanceId"),
                    rst.getString("StudentId"),
                    rst.getString("VehicleId"),
                    rst.getInt("Year"),
                    rst.getString("Month"),
                    rst.getInt("DayCount")
            );
            attendenceList.add(dto);
        }

        return attendenceList;
    }

    public int getDayCountByAttendanceId(String attendanceId) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT DayCount FROM Attendance WHERE AttendanceId = ?", attendanceId);
            if (rst.next()) {
                return rst.getInt(1);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    public String getAttendanceIdByStudentIdYearMonth(String studentId, String year, String month) throws SQLException {
        String query = "SELECT AttendanceId FROM Attendance WHERE StudentId = ? AND Year = ? AND Month = ?";
        ResultSet rst = CrudUtil.execute(query, studentId, year, month);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    public ArrayList<String> getAttendanceMonthsByStudentId(String studentId) throws SQLException {
        String query = "SELECT Year, Month FROM Attendance WHERE StudentId = ?";
        ResultSet rst = CrudUtil.execute(query, studentId);
        ArrayList<String> attendanceMonths = new ArrayList<>();
        while (rst.next()) {
            String year = rst.getString("Year");
            String month = rst.getString("Month");
            attendanceMonths.add(year + "-" + month);
        }
        return attendanceMonths;
    }

    public boolean saveAttendance(AttendanceDto dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Attendance (AttendanceId, StudentId, VehicleId, Year, Month, DayCount) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getAttendanceId(),
                dto.getStudentId(),
                dto.getVehicleId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount()
        );
    }

    public boolean updateAttendance(AttendanceDto dto) throws SQLException {
        return CrudUtil.execute("UPDATE Attendance SET StudentId=?, VehicleId=?, Year=?, Month=?, DayCount=? WHERE AttendanceId=?",
                dto.getStudentId(),
                dto.getVehicleId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount(),
                dto.getAttendanceId()
        );
    }


    public boolean deleteAttendence(String attendenceId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Attendance WHERE AttendanceId=?", attendenceId);
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


}