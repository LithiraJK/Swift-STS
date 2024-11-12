package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    private Connection connection;

    public PaymentModel(Connection connection) {
        this.connection = connection;
    }

    public List<PaymentDto> getPaymentData() {
        List<PaymentDto> paymentData = new ArrayList<>();

        String query = """
            SELECT p.PaymentId, s.StudentId, s.StudentName, p.MonthlyFee, p.Amount, 
                   p.Balance, p.Status, p.Date 
            FROM Payment p 
            INNER JOIN Student s ON p.StudentId = s.StudentId
            """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String paymentId = rs.getString("PaymentId");
                String studentId = rs.getString("StudentId");
                String studentName = rs.getString("StudentName");
                double monthlyFee = rs.getDouble("MonthlyFee");
                double amount = rs.getDouble("Amount");
                double balance = rs.getDouble("Balance");
                String status = rs.getString("Status");
                String date = rs.getString("Date");

                paymentData.add(new PaymentDto(paymentId, studentId, studentName, monthlyFee, amount, balance, status, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentData;
    }

    public double calculateMonthlyFee(String studentId, int dayCount) throws SQLException {
        double feePerDay = 10.0;
        return dayCount * feePerDay;
    }

    public static boolean updatePayment(Connection connection, PaymentDto paymentDto) throws SQLException {
        String query = "UPDATE Payment SET MonthlyFee = ?, Amount = ?, Balance = ?, Status = ?, Date = ? WHERE StudentId = ?";
        return CrudUtil.execute(query,
                paymentDto.getMonthlyFee(),
                paymentDto.getAmount(),
                paymentDto.getBalance(),
                paymentDto.getStatus(),
                paymentDto.getDate(),
                paymentDto.getStudentId()
        );
    }
}