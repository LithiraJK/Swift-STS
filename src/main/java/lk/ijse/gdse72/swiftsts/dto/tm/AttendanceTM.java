package lk.ijse.gdse72.swiftsts.dto.tm;

import javafx.scene.layout.HBox;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceTM {
    private String attendanceId;
    private String studentName;
    private String driverId;
    private int year;
    private String month;
    private int dayCount;
    private HBox actionBox;
}