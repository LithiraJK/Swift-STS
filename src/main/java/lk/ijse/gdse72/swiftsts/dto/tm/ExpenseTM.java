package lk.ijse.gdse72.swiftsts.dto.tm;

import java.sql.Date;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseTM {
    private String expenseId;
    private Date date;
    private double amount;
    private String description;
    private String userId;
}