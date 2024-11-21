package lk.ijse.gdse72.swiftsts.model;

import lk.ijse.gdse72.swiftsts.dto.ExpenseDto;
import lk.ijse.gdse72.swiftsts.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExpenseModel {

    public ArrayList<ExpenseDto> getAllExpenses() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Expense");
        ArrayList<ExpenseDto> expenseDtos = new ArrayList<>();

        while (rst.next()) {
            ExpenseDto expenseDto = new ExpenseDto(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            expenseDtos.add(expenseDto);
        }

        return expenseDtos;
    }

    public String getNextExpenseId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT expenseId FROM Expense ORDER BY expenseId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("E%03d", newIdIndex);
        }
        return "E001";
    }

    public boolean saveExpense(ExpenseDto expenseDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO Expense VALUES (?,?,?,?,?)",
                expenseDto.getExpenseId(),
                expenseDto.getDate(),
                expenseDto.getAmount(),
                expenseDto.getDescription(),
                expenseDto.getUserId()
        );
    }

    public boolean updateExpense(ExpenseDto expenseDto) throws SQLException {
        return CrudUtil.execute("UPDATE Expense SET Date=?, Amount=?, Description=?, UserId=? WHERE ExpenseId=?",
                expenseDto.getDate(),
                expenseDto.getAmount(),
                expenseDto.getDescription(),
                expenseDto.getUserId(),
                expenseDto.getExpenseId()
        );
    }

    public boolean deleteExpense(String expenseId) throws SQLException {
        return CrudUtil.execute("DELETE FROM Expense WHERE ExpenseId=?", expenseId);
    }

    public ArrayList<String> getAllUserIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT UserId FROM User");
        ArrayList<String> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getString(1));
        }

        return userIds;
    }
}