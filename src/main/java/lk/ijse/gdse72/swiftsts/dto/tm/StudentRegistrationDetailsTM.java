package lk.ijse.gdse72.swiftsts.dto.tm;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRegistrationDetailsTM {
    private String registrationId;
    private String studentId;
    private String studentName;
    private String pickupLocation;
    private String destination;
    private double distance;
    private double dayPrice;
    private String routeId;
    private String vehicleId;
    private Date registrationDate;
}
