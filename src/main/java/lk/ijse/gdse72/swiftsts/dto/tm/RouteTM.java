package lk.ijse.gdse72.swiftsts.dto.tm;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RouteTM {
    private String routeId;
    private String routeName;
    private String startPoint;
    private String destination;
    private double dayFee;
}
