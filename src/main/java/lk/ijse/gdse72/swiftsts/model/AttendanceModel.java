package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.AttendanceDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceModel {

//    public static ArrayList<String> getAllAttendanceIds() {
//        try {
//            ResultSet rst = CrudUtil.execute("SELECT AttendanceId FROM Attendance");
//            ArrayList<String> ids = new ArrayList<>();
//            while (rst.next()) {
//                ids.add(rst.getString(1));
//            }
//            return ids;
//        } catch (SQLException sqlException ) {
//            sqlException.printStackTrace();
//
//        }
//        return null;
//    }

    public static int getDayCountByAttendanceId(String attendanceId) {
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

    public static String getAttendanceIdByStudentIdYearMonth(String studentId, String year, String month) throws SQLException {
        String query = "SELECT AttendanceId FROM Attendance WHERE StudentId = ? AND Year = ? AND Month = ?";
        ResultSet rst = CrudUtil.execute(query, studentId, year, month);
        if (rst.next()) {
            return rst.getString("AttendanceId");
        }
        return null;
    }

    public static ArrayList<String> getAttendanceMonthsByStudentId(String studentId) throws SQLException {
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
        return CrudUtil.execute("INSERT INTO Attendance (AttendanceId, StudentId, DriverId, Year, Month, DayCount) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getAttendanceId(),
                dto.getStudentId(),
                dto.getDriverId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount()
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
                    rst.getInt("DayCount")
            );
            attendenceList.add(dto);
        }

        return attendenceList;
    }


    public boolean updateAttendance(AttendanceDto dto) throws SQLException {
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

//    public int getAttendanceDayCount(String studentId, String year, String month) throws SQLException {
//        String query = "SELECT DayCount FROM Attendance WHERE StudentId = ? AND Year = ? AND Month = ?";
//        ResultSet resultSet = CrudUtil.execute(query, studentId, year, month);
//
//        int dayCount = 0;
//        if (resultSet.next()) {
//            dayCount = resultSet.getInt("DayCount");
//        }
//        return dayCount;
//    }
//
//    public String getStudentIdByAttendanceId(String attendanceId) throws SQLException {
//        String query = "SELECT StudentId FROM Attendance WHERE AttendanceId = ?";
//        ResultSet resultSet = CrudUtil.execute(query, attendanceId);
//
//        if (resultSet.next()) {
//            return resultSet.getString("StudentId");
//        }
//        return null;
//    }
}