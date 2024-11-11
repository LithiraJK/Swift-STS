package lk.ijse.gdse72.swiftsts.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendenceDto {
    private String attendenceId;
    private String studentId;
    private String driverId;
    private int year;
    private String month;
    private int dayCount;
}