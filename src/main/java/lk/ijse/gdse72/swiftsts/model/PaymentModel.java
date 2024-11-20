package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.PaymentDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;
import lk.ijse.gdse72.swiftsts.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {

    private Connection connection;

    public PaymentModel() throws SQLException {
        this.connection = DBConnection.getInstance().getConnection();
    }

    public static boolean insertPayment(PaymentDto paymentDto) throws SQLException {
        String query = "INSERT INTO Payment (PaymentId, StudentId, MonthlyFee, CreditBalance, Amount, Balance, Status, Date) VALUES (?,?,?,?,?,?,?,?)";
        return CrudUtil.execute(query,
                paymentDto.getPaymentId(),
                paymentDto.getStudentId(),
                paymentDto.getMonthlyFee(),
                paymentDto.getCreditBalance(),
                paymentDto.getAmount(),
                paymentDto.getBalance(),
                paymentDto.getStatus(),
                paymentDto.getDate()
        );
    }

    public List<PaymentDto> getPaymentData() {
        List<PaymentDto> paymentData = new ArrayList<>();

        String query = """
            SELECT p.PaymentId, s.StudentId, p.MonthlyFee, s.CreditBalance, p.Amount,
                   p.Balance, p.Status, p.Date
            FROM Payment p
            INNER JOIN Student s ON p.StudentId = s.StudentId
            """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String paymentId = rs.getString("PaymentId");
                String studentId = rs.getString("StudentId");
                double monthlyFee = rs.getDouble("MonthlyFee");
                double creditBalance = rs.getDouble("CreditBalance");
                double amount = rs.getDouble("Amount");
                double balance = rs.getDouble("Balance");
                String status = rs.getString("Status");
                String date = rs.getString("Date");

                paymentData.add(new PaymentDto(paymentId, studentId, monthlyFee,creditBalance, amount, balance, status, date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentData;
    }

    public static double calculateMonthlyFee(String studentId, int dayCount) throws SQLException {
        double feePerDay = 10.0;
        return dayCount * feePerDay;
    }

    public static boolean updatePayment(PaymentDto paymentDto) throws SQLException {
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