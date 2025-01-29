package lk.ijse.gdse72.swiftsts.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendanceDto {
    private String attendanceId;
    private String studentId;
    private String vehicleId;
    private int year;
    private String month;
    private int dayCount;
}