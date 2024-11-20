package lk.ijse.gdse72.swiftsts.dto.tm;

import java.sql.Date;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTM {
    private String paymentId;
    private String studentId;
    private double monthlyFee;
    private double creditBalance;
    private double amount;
    private double balance;
    private String status;
    private Date date;
}