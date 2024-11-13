package lk.ijse.gdse72.swiftsts.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehicleDto {
    private String vehicleId ;
    private String registrationNo ;
    private String vehicleType ;
    private double EngineCapacity ;
    private String fuelType ;
    private String model ;
    private int seatCount ;
    private int availableSeatCount ;
}
