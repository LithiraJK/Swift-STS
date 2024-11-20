package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    public List<PaymentDto> getPaymentData() throws SQLException {
        String query = """
            SELECT p.PaymentId, s.StudentId, s.StudentName, p.MonthlyFee, p.Amount,
                   p.CreditBalance, p.Balance, p.Status, p.Date
            FROM Payment p
            INNER JOIN Student s ON p.StudentId = s.StudentId
            """;
        ResultSet rs = CrudUtil.execute(query);
        List<PaymentDto> paymentData = new ArrayList<>();

        while (rs.next()) {
            String paymentId = rs.getString("PaymentId");
            String studentId = rs.getString("StudentId");
            String studentName = rs.getString("StudentName");
            double monthlyFee = rs.getDouble("MonthlyFee");
            double creditBalance = rs.getDouble("CreditBalance");
            double amount = rs.getDouble("Amount");
            double balance = rs.getDouble("Balance");
            String status = rs.getString("Status");
            Date date = rs.getDate("Date");

            paymentData.add(new PaymentDto(paymentId, studentId, studentName, monthlyFee, amount, creditBalance, balance, status, date));
        }

        return paymentData;
    }

    public double calculateMonthlyFee(String studentId, int dayCount) throws SQLException {
        double feePerDay = 10.0;
        return dayCount * feePerDay;
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException {
        String query = "UPDATE Payment SET MonthlyFee = ?, Amount = ?, CreditBalance = ?, Balance = ?, Status = ?, Date = ? WHERE StudentId = ?";
        return CrudUtil.execute(query,
                paymentDto.getMonthlyFee(),
                paymentDto.getAmount(),
                paymentDto.getCreditBalance(),
                paymentDto.getBalance(),
                paymentDto.getStatus(),
                paymentDto.getDate(),
                paymentDto.getStudentId()
        );
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException {
        String query = "INSERT INTO Payment (PaymentId, StudentId, MonthlyFee, Amount, CreditBalance, Balance, Status, Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(query,
                paymentDto.getPaymentId(),
                paymentDto.getStudentId(),
                paymentDto.getMonthlyFee(),
                paymentDto.getAmount(),
                paymentDto.getCreditBalance(),
                paymentDto.getBalance(),
                paymentDto.getStatus(),
                paymentDto.getDate()
        );
    }

    public int getAttendanceDayCount(String studentId, int year, int monthValue) throws SQLException {
        String query = "SELECT COUNT(*) FROM Attendance WHERE StudentId = ? AND YEAR(Date) = ? AND MONTH(Date) = ?";
        ResultSet resultSet = CrudUtil.execute(query, studentId, year, monthValue);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}