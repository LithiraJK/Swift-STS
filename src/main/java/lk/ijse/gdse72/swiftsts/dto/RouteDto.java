package lk.ijse.gdse72.swiftsts.dto;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RouteDto {
    private String routeId;
    private String routeName;
    private String startPoint;
    private String destination;
    private double routeFee;
}
