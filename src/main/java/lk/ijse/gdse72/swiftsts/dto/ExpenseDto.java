package lk.ijse.gdse72.swiftsts.dto;

import java.sql.Date;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpenseDto {
    private String expenseId;
    private Date date;
    private double amount;
    private String description;
    private String userId;
}