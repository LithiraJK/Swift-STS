package lk.ijse.gdse72.swiftsts.dto.tm;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceTM {
    private String attendanceId;
    private String studentId;
    private String driverId;
    private int year;
    private String month;
    private int dayCount;
}