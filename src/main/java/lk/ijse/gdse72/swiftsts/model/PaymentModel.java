package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    public static boolean insertPayment(PaymentDto paymentDto) throws SQLException {
        String query = "INSERT INTO Payment (PaymentId, StudentId, MonthlyFee, Amount, Balance, Status, Date) VALUES (?,?,?,?,?,?,?)";
        return CrudUtil.execute(query,
                paymentDto.getPaymentId(),
                paymentDto.getStudentId(),
                paymentDto.getMonthlyFee(),
                paymentDto.getAmount(),
                paymentDto.getBalance(),
                paymentDto.getStatus(),
                paymentDto.getDate()
        );
    }

    public List<PaymentDto> getPaymentData() {
        List<PaymentDto> paymentData = new ArrayList<>();
        String query = """
            SELECT p.PaymentId, s.StudentId, p.MonthlyFee, p.Amount,
                   p.Balance, s.CreditBalance, p.Status, p.Date
            FROM Payment p
            INNER JOIN Student s ON p.StudentId = s.StudentId
            """;

        try {
            ResultSet rs = CrudUtil.execute(query);
            while (rs.next()) {
                String paymentId = rs.getString(1);
                String studentId = rs.getString(2);
                double monthlyFee = rs.getDouble(3);
                double amount = rs.getDouble(4);
                double balance = rs.getDouble(5);
                double creditBalance = rs.getDouble(6);
                String status = rs.getString(7);
                String date = rs.getString(8);

                paymentData.add(new PaymentDto(paymentId, studentId, monthlyFee, amount, balance, creditBalance, status, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentData;
    }

    public static double calculateMonthlyFee(String studentId, int dayCount) throws SQLException {
        String query = "SELECT DayPrice FROM StudentRegistration WHERE StudentId = ?";
        ResultSet rs = CrudUtil.execute(query, studentId);

        if (rs.next()) {
            double dayPrice = rs.getDouble("DayPrice");
            if (dayCount>0){
                return dayCount * dayPrice;
            }
            return 0.00;
        } else {
            throw new SQLException("Student ID not found in StudentRegistrationTable");
        }
    }


    public static String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT PaymentId FROM Payment ORDER BY PaymentId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }
}