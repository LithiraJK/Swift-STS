package lk.ijse.gdse72.swiftsts.dto;

import java.sql.Date;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    private String paymentId;
    private String studentId;
    private double monthlyFee;
    private double creditBalance;
    private double amount;
    private double balance;
    private String status;
    private String date;
}