package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.AttendenceDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendenceModel {

    public boolean saveAttendence(AttendenceDto dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Attendence (AttendenceId, StudentId, DriverId, Year, Month, DayCount) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getAttendenceId(),
                dto.getStudentId(),
                dto.getDriverId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount()
        );
    }

    public ArrayList<AttendenceDto> getAllAttendences() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Attendence");
        ArrayList<AttendenceDto> attendenceList = new ArrayList<>();

        while (rst.next()) {
            AttendenceDto dto = new AttendenceDto(
                    rst.getString("AttendenceId"),
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

    public boolean updateAttendence(AttendenceDto dto) throws SQLException {
        return CrudUtil.execute("UPDATE Attendence SET StudentId=?, DriverId=?, Year=?, Month=?, DayCount=? WHERE AttendenceId=?",
                dto.getStudentId(),
                dto.getDriverId(),
                dto.getYear(),
                dto.getMonth(),
                dto.getDayCount(),
                dto.getAttendenceId()
        );
    }

    public boolean deleteAttendence(String attendenceId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Attendence WHERE AttendenceId=?", attendenceId);
    }
}