package lk.ijse.gdse72.swiftsts.dto;
import lombok.*;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentRegistrationDto {
    private String registrationId;
    private String studentId;
    private double distance;
    private double dayPrice;
    private String routeId;
    private String vehicleId;
    private Date registrationDate;
}
